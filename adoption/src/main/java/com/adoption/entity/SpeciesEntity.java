package com.adoption.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADOPTION_SPECIES")
public class SpeciesEntity implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "SPECIES_ID")
  private String speciesId;

  @Column(name = "SPECIES_NAME")
  private String speciesName;
}
