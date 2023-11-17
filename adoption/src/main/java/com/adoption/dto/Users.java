package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Users {

  @JsonInclude(Include.NON_EMPTY)
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("mobile")
  private String mobile;

  @JsonProperty("googleAccount")
  private String googleAccount;

  @JsonProperty("status")
  private String staus ;
  
}
