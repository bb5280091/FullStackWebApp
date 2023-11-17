package com.adoption.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetMessageRespDTO {

  @JsonProperty("response")
  private List<Message> messages;

}
