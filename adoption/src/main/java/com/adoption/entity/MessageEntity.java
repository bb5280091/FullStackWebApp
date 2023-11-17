package com.adoption.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ADOPTION_MESSAGE")
public class MessageEntity implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "SENDER_ID")
  private Long senderId;

  @Column(name = "RECEIVER_ID")
  private Long receiverId;

  @Column(name = "CONTENT")
  private String content;

  @Column(name = "SENDING_TIME")
  private LocalDateTime timestamp;

}
