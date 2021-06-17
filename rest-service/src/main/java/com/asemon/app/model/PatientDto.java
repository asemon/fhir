package com.asemon.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Simple POJO class which serves as a model for GSON and also represents a DTO for data transfer
 * between the layers
 * 
 * @author Alex Semonov
 *
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PatientDto {

  private String gender;
  private String birthDate;
  private List<NameDto> name = new ArrayList<>();
  private String uri;

  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
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

  }

}
