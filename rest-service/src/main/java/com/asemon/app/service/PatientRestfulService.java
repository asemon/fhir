package com.asemon.app.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.asemon.app.model.PatientDto;
import com.asemon.app.service.dao.PatientDaService;
import com.google.gson.Gson;

@Path("/patient")
public class PatientRestfulService {

  @Inject
  private PatientDaService patientService;

  private static final Logger LOG = LoggerFactory.getLogger(PatientRestfulService.class);

  @GET
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  public Response testOk() {
    return Response.status(Status.OK).build();
  }

  @POST
  @Path("/transferFhirPatient")
  @Produces(MediaType.APPLICATION_JSON)
  public Response transferFhirPatient(@QueryParam("fhirUrl") String fhirUrl) {
    try {
      String uri = URLDecoder.decode(fhirUrl, StandardCharsets.UTF_8.name());
      LOG.info("Calling " + uri);
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet getRequest = new HttpGet(uri);
      HttpResponse response = client.execute(getRequest);
      String jsonString = EntityUtils.toString(response.getEntity());
      LOG.info("Received payload:\n {}", jsonString);
      Gson gson = new Gson();
      PatientDto patient = gson.fromJson(jsonString, PatientDto.class);
      patient.setUri(uri);
      patientService.savePatient(patient);
      return Response.status(Status.OK).entity(gson.toJson(patient)).build();
    } catch (UnsupportedEncodingException e) {
      LOG.error("URL could not be encoded", e);
      return Response.status(Status.BAD_REQUEST).build();
    } catch (ClientProtocolException ex) {
      LOG.error("Could not establish connection - bad or corrupt URL", ex);
      return Response.status(Status.BAD_REQUEST).build();
    } catch (IOException exs) {
      LOG.error("Something went wrong", exs);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

  @GET
  @Path("/transferedPatient")
  @Produces(MediaType.APPLICATION_JSON)
  public Response transferedPatient(@QueryParam("fhirUrl") String fhirUrl) {
    try {
      String uri = URLDecoder.decode(fhirUrl, StandardCharsets.UTF_8.name());
      LOG.info("Received URI: {}", uri);
      PatientDto patient = patientService.getPatient(uri);
      if (patient == null) {
        LOG.error("Nothing found");
        return Response.status(Status.NOT_FOUND).build();
      }
      Gson gson = new Gson();
      String jsonObject = gson.toJson(patient);
      LOG.info("Found following entry:\n {}", jsonObject);
      return Response.status(Status.OK).entity(jsonObject).build();
    } catch (UnsupportedEncodingException e) {
      LOG.error("URL could not be encoded", e);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }

}
