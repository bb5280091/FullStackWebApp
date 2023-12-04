package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PutPostStatusReqDTO {
  
  @JsonProperty("id")
  private Long id ;
  
  @JsonProperty("status")
  private String status ;

}
