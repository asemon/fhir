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
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing NAME
 * 
 * @author Alex Semonov
 *
 */
@Getter
@Setter
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

}
