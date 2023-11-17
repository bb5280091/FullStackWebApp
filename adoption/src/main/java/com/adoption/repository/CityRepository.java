package com.adoption.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.adoption.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Object> {

  List<CityEntity> findByCityId(String cityId);
  
  @Query("select e.cityName from CityEntity e where e.cityId = :cityId")
  public List<String>  qureyCityNameByCityId(String cityId); 
  
  
  @Query("select e from CityEntity e")
  public List<CityEntity> findAllCity();
}