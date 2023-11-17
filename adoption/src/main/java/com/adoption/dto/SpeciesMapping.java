package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpeciesMapping {

  @JsonProperty("speciesId")
  private String  speciesId ;
  
  @JsonProperty("speciesName")
  private String  speciesName ;
}
