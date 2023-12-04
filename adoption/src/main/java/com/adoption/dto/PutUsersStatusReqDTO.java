package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PutUsersStatusReqDTO {
  
  @JsonProperty("status")
  private String status ;

}
