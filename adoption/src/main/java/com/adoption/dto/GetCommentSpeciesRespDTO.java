package com.adoption.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCommentSpeciesRespDTO {

  @JsonProperty("data")
  private List<SpeciesMapping> species;

}
