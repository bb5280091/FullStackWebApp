package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MinimalistAnimal {
  
  @JsonProperty("id")
  private Long id;
  
  @JsonProperty("name")
  private String name;
}
