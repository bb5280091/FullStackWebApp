package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUsersByUserIdRespDTO {

  @JsonProperty("user")
  private User users;

}
