package com.asemon.app.persistence;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENT")
public class Patient implements Serializable {

  private static final long serialVersionUID = -1418940091067178911L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pat_seq")
  @SequenceGenerator(name = "pat_seq", sequenceName = "pat_seq")
  @Column(name = "PATIENTID")
  private Long patientid;

  @Column(name = "CREATED_ON")
  private LocalDateTime createdOn;

  @Column(name = "BIRTH_DATE")
  private LocalDate birthDate;

  @Column(name = "GENDER")
  private String gender;

  @Column(name = "URI")
  private String uri;

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Name> name;

  @PrePersist
  public void prepPersist() {
    createdOn = LocalDateTime.now();
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Set<Name> getName() {
    return name;
  }

  public void setName(Set<Name> name) {
    this.name = name;
  }

  public Long getPatientId() {
    return patientid;
  }

  public void setPatientId(Long patientid) {
    this.patientid = patientid;
  }

}
