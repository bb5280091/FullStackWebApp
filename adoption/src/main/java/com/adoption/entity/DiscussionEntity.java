package com.adoption.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(value = DiscussionPK.class)
@Table(name = "ADOPTION_DISCUSSION")
public class DiscussionEntity implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "SERIAL_NO")
  private Long serialNo;

  @Id
  @Column(name = "ANIMAL_ID")
  private Long animalId;

  @Id
  @Column(name = "REPLY_NO")
  private Long replyNo;

  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "CONTENT")
  private String content;

  @Column(name = "SENDING_TIME")
  private LocalDateTime timestamp;

}
