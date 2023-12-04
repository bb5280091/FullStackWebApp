package com.adoption.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADOPTION_CITY")
public class CityEntity implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "CITY_ID")
  private String cityId;

  @Column(name = "CITY_NAME")
  private String cityName;

}
