package com.adoption.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetAnimalsRespDTO {

  @NotNull
  @JsonProperty("totalPage")
  private Number totalPage;

  @NotNull
  @JsonProperty("totalCount")
  private Number totalCount;

  @NotNull
  @JsonProperty("pageNumber")
  private Number pageNumber;

  @NotNull
  @JsonProperty("pageSize")
  private Number pageSize;
  
  @JsonProperty("response")
  private List<Animals> animals;

}
