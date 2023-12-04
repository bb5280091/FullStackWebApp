package com.adoption.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetRankCtrRespDTO {

  @JsonProperty("animal")
  public List<SimpleAnimal> animals;
}
