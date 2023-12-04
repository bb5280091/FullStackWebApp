package com.adoption.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.adoption.entity.SpeciesEntity;

@Repository
public interface SpeciesRepository extends JpaRepository<SpeciesEntity, Object> {

  List<SpeciesEntity> findBySpeciesId(String speciesId);
  
  @Query("select e from SpeciesEntity e")
  public List<SpeciesEntity> findAllSpecies();
  
  @Query("select e.speciesName from SpeciesEntity e where e.speciesId = :speciesId")
  public List<String>  qureySpeciesNameBySpeciesId(String speciesId); 
}