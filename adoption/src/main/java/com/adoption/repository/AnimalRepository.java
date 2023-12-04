package com.adoption.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.adoption.entity.AnimalEntity;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Object> {

  // 只搜尋時只設條件給有代入的參數
  @Query("select a from AnimalEntity a where (:cityId is null or a.cityId = :cityId) and (:sex is null or a.sex = :sex) and (:speciesId is null or a.speciesId = :speciesId)")
  public Page<AnimalEntity> findByCityIdAndSexAndSpeciesId(@Param("cityId") String cityId,
      @Param("sex") String sex, @Param("speciesId") String speciesId, Pageable pageable);

  List<AnimalEntity> findById(Long animalId);

  List<AnimalEntity> findByUserIdOrderByPublishDateDesc(Long userId);

  // 後來新加的，用來審核發文狀態
  @Transactional
  @Modifying
  @Query("update AnimalEntity a set a.postStatus = :postStatus where a.id = :id")
  public void modifyPostStatus(Long id, String postStatus);

  @Query("SELECT a FROM AnimalEntity a ORDER BY a.publishDate DESC")
  Page<AnimalEntity> findAllOrderByPublishDateDesc(Pageable pageable);

  @Modifying
  @Transactional
  @Query("update AnimalEntity e set e.ctr = e.ctr + 1 where e.id = :id")
  public void addAnimalCtr(Long id);
  
  @Query("select e from AnimalEntity e order by e.ctr desc")
  public List<AnimalEntity> queryAnimalByRankCtr();
  
  @Transactional
  public void deleteById(Long id);

}
