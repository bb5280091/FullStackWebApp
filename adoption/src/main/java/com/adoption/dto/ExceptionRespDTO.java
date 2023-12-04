package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExceptionRespDTO {
  
  @JsonProperty("statusCode")
  private String statusCode;
  
  @JsonProperty("status")
  private String status;

}
