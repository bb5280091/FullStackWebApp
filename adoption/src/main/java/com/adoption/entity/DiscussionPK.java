package com.adoption.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADOPTION_DISCUSSION")
public class DiscussionPK implements Serializable{
  
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
  
  public Long getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(Long serialNo) {
    this.serialNo = serialNo;
  }

  public Long getAnimalId() {
    return animalId;
  }

  public void setAnimalId(Long animalId) {
    this.animalId = animalId;
  }

  public Long getReplyNo() {
    return replyNo;
  }

  public void setReplyNo(Long replyNo) {
    this.replyNo = replyNo;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DiscussionPK other = (DiscussionPK) obj;
    return Objects.equals(animalId, other.animalId) && Objects.equals(replyNo, other.replyNo)
        && Objects.equals(serialNo, other.serialNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(animalId, replyNo, serialNo);
  }

  
}
