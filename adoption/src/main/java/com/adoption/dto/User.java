package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {

  @JsonProperty("userId")
  private Long userId ;
  
  @JsonProperty("name")
  private String name ;
  
  @JsonProperty("mobile")
  private String mobile ;
  
  @JsonProperty("googleAccount")
  private String googleAccount ;
  
  @JsonProperty("STATUS")
  private String staus ;
  
}
