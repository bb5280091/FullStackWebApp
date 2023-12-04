package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateAnimalReqDTO {

  @JsonProperty("request")
  private  Animals animal;

}
