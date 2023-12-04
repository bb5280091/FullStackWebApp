package com.adoption.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.adoption.entity.UserEntity;


public interface AdminUserRepository extends JpaRepository<UserEntity, String> {

  @Query("select e from UserEntity e")
  public List<UserEntity> findAllUsers();

  @Query("select e from UserEntity e WHERE e.userId = :userId")
  public List<UserEntity> findUserByUserId(Long userId);

  @Transactional
  @Modifying
  @Query("update UserEntity e set  e.status = :status where e.userId = :userId")
  public void modifyUserStatus(Long userId, String status);

}
