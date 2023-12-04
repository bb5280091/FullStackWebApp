package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CityMapping {
  
  @JsonProperty("cityId")
  private String  cityId;
  
  @JsonProperty("cityName")
  private String cityName;

}
