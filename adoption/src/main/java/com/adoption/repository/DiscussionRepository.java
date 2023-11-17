package com.adoption.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.adoption.entity.DiscussionEntity;

public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Object> {

  List<DiscussionEntity> findByAnimalIdOrderBySerialNoAscReplyNoAsc(Long animalId);

}
