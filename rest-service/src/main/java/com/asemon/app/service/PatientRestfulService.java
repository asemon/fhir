package com.asemon.app.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.asemon.app.model.PatientDto;
import com.google.gson.Gson;

@Path("/patient")
public class PatientRestfulService {

  @GET
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  public String getTestString() {
    return "Success 200";
  }

  @POST
  @Path("/transferFhirPatient")
  @Produces(MediaType.APPLICATION_JSON)
  public Response transferFhirPatient() throws IOException {

    HttpClient client = HttpClientBuilder.create().build();
    HttpGet getRequest = new HttpGet("https://lforms-fhir.nlm.nih.gov/baseR4/Patient/5770420");
    HttpResponse response = client.execute(getRequest);
    String jsonString = EntityUtils.toString(response.getEntity());
    Gson gson = new Gson();
    PatientDto patient = gson.fromJson(jsonString, PatientDto.class);
    System.out.println(patient.toString());
    return Response.status(Status.OK).entity(response.getEntity()).type(MediaType.APPLICATION_JSON)
        .build();
  }
}
