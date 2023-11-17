package com.adoption.dto;

import java.sql.Timestamp;
import javax.persistence.Lob;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Animals {

  @JsonInclude(Include.NON_EMPTY)
  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("species")
  private String species;

  @JsonProperty("type")
  private String type;

  @JsonProperty("sex")
  private String sex;

  @JsonProperty("size")
  private String size;

  @JsonProperty("color")
  private String color;

  @JsonProperty("age")
  private String age;

  @JsonProperty("ligation")
  private String ligation;

  @JsonProperty("city")
  private String city;

  @JsonProperty("conditionAffidavit")
  private String conditionAffidavit;

  // 有改conditionFollowUp
  @JsonProperty("conditionFollowUp")
  private String conditionFollowUp;

  @JsonProperty("conditionAgeLimit")
  private String conditionAgeLimit;

  @JsonProperty("conditionParentalPermission")
  private String conditionParentalPermission;

  @JsonProperty("introduction")
  private String introduction;

  // 多了這個
  @JsonInclude(Include.NON_EMPTY)
  @JsonProperty("postStatus")
  private String postStatus;

  // 多了這個
  @JsonInclude(Include.NON_EMPTY)
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("publishDate")
  private Timestamp publishDate;

  @Lob
  @JsonProperty("photo")
  private byte[] photo;

  @JsonProperty("userId")
  private Long userId;

}
