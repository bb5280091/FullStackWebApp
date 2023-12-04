package com.adoption.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ADOPTION_USER")
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "MOBILE")
  private String mobile;

  @Column(name = "GOOGLE_ACCOUNT")
  private String googleAccount;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "ROLE")
  private String role;

  @Column(name = "SUBSCRIPTION")
  private String subscription;

}

