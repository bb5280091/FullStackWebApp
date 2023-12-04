package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewUserReqDTO {

  @JsonProperty("request")
  private Users user;

}
