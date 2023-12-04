package com.adoption.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimpleAnimal {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("speciesName")
  private String speciesName;

  @JsonProperty("sex")
  private String sex;

  @JsonProperty("cityName")
  private String cityName;

  @JsonProperty("conditionAffidavit")
  private String conditionAffidavit;

  @JsonProperty("conditionFollowup")
  private String conditionFollowup;

  @JsonProperty("conditionAgeLimit")
  private String conditionAgeLimit;

  @JsonProperty("conditionParentalPermission")
  private String conditionParentalPermission;

  @JsonProperty("introduction")
  private String introduction;

  @JsonProperty("photo")
  private byte[] photo;
}
