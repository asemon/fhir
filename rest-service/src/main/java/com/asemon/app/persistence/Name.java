package com.asemon.app.persistence;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NAME")
public class Name implements Serializable {

  private static final long serialVersionUID = 1986047102258172547L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nam_seq")
  @SequenceGenerator(name = "nam_seq", sequenceName = "nam_seq")
  private Long nameid;

  @Column(name = "FAMILY")
  private String family;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "GIVEN")
  private Set<String> given;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "PREFIX")
  private Set<String> prefix;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "SUFFIX")
  private Set<String> suffix;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patientid", nullable = false)
  private Patient patient;

  public String getFamily() {
    return family;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public Set<String> getGiven() {
    return given;
  }

  public void setGiven(Set<String> given) {
    this.given = given;
  }

  public Set<String> getPrefix() {
    return prefix;
  }

  public void setPrefix(Set<String> prefix) {
    this.prefix = prefix;
  }

  public Set<String> getSuffix() {
    return suffix;
  }

  public void setSuffix(Set<String> suffix) {
    this.suffix = suffix;
  }

  public Long getNameId() {
    return nameid;
  }

  public void setNameId(Long nameid) {
    this.nameid = nameid;
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

}
