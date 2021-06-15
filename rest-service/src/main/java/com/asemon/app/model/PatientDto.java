package com.asemon.app.model;

import java.util.ArrayList;
import java.util.List;

public class PatientDto {
  private String gender;
  private String birthDate;
  private List<Name> name;

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

  public List<Name> getName() {
    if (name == null) {
      name = new ArrayList<PatientDto.Name>();
    }
    return name;
  }

  public void setName(List<Name> name) {
    this.name = name;
  }

  private class Name {
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("gender: ").append(gender).append("\n").append("birth date: ").append(birthDate)
        .append("\n");
    for (Name n : name) {
      sb.append("family: ").append(n.family).append("\n").append("given: ");
      for (String s : n.given) {
        sb.append(s).append(" ");
      }
    }
    return sb.toString();
  }
}
