package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserReqDTO {

  @JsonProperty("request")
  private Users user;

}
