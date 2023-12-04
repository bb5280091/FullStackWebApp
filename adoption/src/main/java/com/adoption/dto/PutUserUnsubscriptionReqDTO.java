package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PutUserUnsubscriptionReqDTO {

  @JsonProperty("userId")
  private Long userId ;
  
  @JsonProperty("id")
  private Long id ;
}
