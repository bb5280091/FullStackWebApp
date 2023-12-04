package com.adoption.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {

  @JsonProperty("name")
  private String name;

  @JsonProperty("receiverId")
  private Long receiverId;
  
  @JsonProperty("senderId")
  private Long senderId;
  
  @JsonProperty("content")
  private String content;

  @JsonFormat(pattern="yyyy/MM/dd HH:mm")
  @JsonProperty("time")
  private LocalDateTime time;

}
