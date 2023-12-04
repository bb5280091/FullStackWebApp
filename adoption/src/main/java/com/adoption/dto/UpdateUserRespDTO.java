package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserRespDTO {

  @JsonProperty("returnCode")
  private  String returnCode;
  
  @JsonProperty("returnDesc")
  private  String returnDesc;

}
