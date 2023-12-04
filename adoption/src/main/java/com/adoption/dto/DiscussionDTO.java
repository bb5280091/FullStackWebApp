package com.adoption.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiscussionDTO {

  @JsonProperty("name")
  private String name;

  @JsonProperty("serialNo")
  private Long serialNo;

  @JsonProperty("replyNo")
  private Long replyNo;

  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("content")
  private String content;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
  @JsonProperty("timestamp")
  private LocalDateTime timestamp;


}
