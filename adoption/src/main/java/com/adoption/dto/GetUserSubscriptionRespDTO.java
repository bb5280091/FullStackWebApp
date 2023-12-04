package com.adoption.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetUserSubscriptionRespDTO {

  @JsonProperty("response")
  private List<MinimalistAnimal> minimalistAnimals ;
  
}
