package com.asemon.app.service.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.asemon.app.model.PatientDto;
import com.asemon.app.model.PatientDto.NameDto;
import com.asemon.app.persistence.Name;
import com.asemon.app.persistence.Patient;

@Stateless
public class PatientDaService {

  @PersistenceContext(unitName = "patient-pu")
  private EntityManager em;

  private static final String URI_PARAM = "uri_param";
  private static final String FIND_BY_URI =
      "SELECT p FROM Patient p WHERE p.uri = :" + URI_PARAM + " ORDER BY p.createdOn DESC";

  @Transactional
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void savePatient(PatientDto dto) {
    if (dto == null) {
      return;
    }
    // sure, we could use something like dozer mapper for that, but for the sake of simplicity the
    // mapping is done programmatically
    Patient patient = new Patient();
    patient.setUri(dto.getUri());
    patient.setBirthDate(
        LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    patient.setGender(dto.getGender());
    if (dto.getName() != null && !dto.getName().isEmpty()) {
      patient.setName(new HashSet<>());
      for (PatientDto.NameDto nameDto : dto.getName()) {
        mapNameFromDto(patient, nameDto);
      }
    }

    em.persist(patient);
    em.flush();
  }

  /**
   * Gets data from DB and maps them to DTO
   * 
   * @param uri search parameter to look for data in DB
   * @return mapped DTO
   */
  @Transactional
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public PatientDto getPatient(String uri) {
    // the assumption here is that each call of savePatient() would create a separate DB entry
    // hence we can expect, that entries with the same URI could exist in DB
    List<Patient> patients =
        em.createQuery(FIND_BY_URI, Patient.class).setParameter(URI_PARAM, uri).getResultList();
    if (patients.isEmpty()) {
      return null;
    } else {
      // since we can get many results with the same URI from DB, we will use the one with the most
      // recent
      // time stamp (ordered DESC)
      Patient pat = patients.get(0);
      // mapping is done programmatically
      PatientDto patientDto = new PatientDto();
      patientDto.setUri(pat.getUri());
      patientDto.setGender(pat.getGender());
      patientDto.setBirthDate(pat.getBirthDate().toString());
      pat.getName().stream().forEach(t -> patientDto.getName()
          .add(new NameDto(t.getFamily(), t.getGiven(), t.getPrefix(), t.getSuffix())));
      return patientDto;
    }
  }

  // maps the NameDto inner class
  private void mapNameFromDto(Patient patient, PatientDto.NameDto nameDto) {
    Name name = new Name();
    name.setFamily(nameDto.getFamily());
    String[] given = nameDto.getGiven();
    String[] prefix = nameDto.getPrefix();
    String[] suffix = nameDto.getSuffix();
    if (given != null && given.length != 0) {
      name.setGiven(new HashSet<>(Arrays.asList(given)));
    }
    if (prefix != null && prefix.length != 0) {
      name.setPrefix(new HashSet<>(Arrays.asList(prefix)));
    }
    if (suffix != null && suffix.length != 0) {
      name.setSuffix(new HashSet<>(Arrays.asList(suffix)));
    }
    name.setPatient(patient);
    patient.getName().add(name);
  }

}
