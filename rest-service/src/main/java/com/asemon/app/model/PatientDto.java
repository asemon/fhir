package com.asemon.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientDto {
  private String gender;
  private String birthDate;
  private List<NameDto> name;
  private String uri;

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public List<NameDto> getName() {
    if (name == null) {
      name = new ArrayList<PatientDto.NameDto>();
    }
    return name;
  }

  public void setName(List<NameDto> name) {
    this.name = name;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public static class NameDto {
    public NameDto(String family, Set<String> given, Set<String> prefix, Set<String> suffix) {
      this.family = family;
      if (given != null) {
        this.given = given.toArray(new String[given.size()]);
      }
      if (prefix != null) {
        this.prefix = prefix.toArray(new String[prefix.size()]);
      }
      if (suffix != null) {
        this.suffix = suffix.toArray(new String[suffix.size()]);
      }
    }

    private String family;
    private String[] given;
    private String[] prefix;
    private String[] suffix;

    public String getFamily() {
      return family;
    }

    public void setFamily(String family) {
      this.family = family;
    }

    public String[] getGiven() {
      return given;
    }

    public void setGiven(String[] given) {
      this.given = given;
    }

    public String[] getPrefix() {
      return prefix;
    }

    public void setPrefix(String[] prefix) {
      this.prefix = prefix;
    }

    public String[] getSuffix() {
      return suffix;
    }

    public void setSuffix(String[] suffix) {
      this.suffix = suffix;
    }

  }

}
