package com.adoption.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADOPTION_ANIMAL")
public class AnimalEntity implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "Name")
  private String name;
  
  @Column(name = "SPECIES_ID")
  private String speciesId;
  
  @Column(name = "TYPE")
  private String type;
  
  @Column(name = "SEX")
  private String sex;
  
  @Column(name = "ANIMAL_SIZE")
  private String animalSize;
  
  @Column(name = "COLOR")
  private String color;
  
  @Column(name = "AGE")
  private String age;
  
  @Column(name = "LIGATION")
  private String ligation;
  
  @Column(name = "CITY_ID")
  private String cityId;
  
  @Column(name = "CONDITION_AFFIDAVIT")
  private String conditionAffidavit;
  
  @Column(name = "CONDITION_FOLLOW_UP")
  private String conditionFollowUp;
  
  @Column(name = "CONDITION_AGE_LIMIT")
  private String conditionAgeLimit;
  
  @Column(name = "CONDITION_PARENTAL_PERMISSION")
  private String conditionParentalPermission;
  
  @Column(name = "INTRODUCTION")
  private String introduction;
  
  @Column(name = "CTR")
  private String ctr;
  
  @Column(name = "POST_STATUS")
  private String postStatus;
  
  @Column(name = "PUBLISH_DATE")
  private Timestamp publishDate;
  
  @Column(name = "PHOTO")
  @Lob
  private byte[] photo;
  
  @Column(name = "USER_ID")
  private Long userId;
}
