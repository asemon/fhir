package com.asemon.app.persistence;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NAME")
public class Name implements Serializable {

  private static final long serialVersionUID = 1986047102258172547L;

  @Id
  @GeneratedValue
  @Column(name = "NAME_ID")
  private Long nameId;

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
  @JoinColumn(name = "patientId", nullable = false)
  private Patient patient;

}
