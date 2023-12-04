package com.adoption.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCommentCityRespDTO {
  
  @JsonProperty("data")
  private List<CityMapping> cities ;  

}
