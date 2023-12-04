package com.adoption.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.adoption.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Object> {

  List<UserEntity> findByUserId(Long userId);

  public List<UserEntity> findByGoogleAccount(String googleAccount);

  @Query("select u.subscription from UserEntity u where u.userId = :userId")
  public String findUserSubscription(Long userId);

  @Transactional
  @Modifying
  @Query("update UserEntity u set u.subscription = :subscription where u.userId = :userId")
  public void updateUserSubscription(String subscription, Long userId);

  @Query("select e from UserEntity e order by e.userId asc")
  public List<UserEntity> findAllUsers();

  @Query("select e from UserEntity e where e.userId = :userId order by e.userId asc")
  public List<UserEntity> findUserByUserId(Long userId);

  @Query("select e from UserEntity e where e.googleAccount LIKE %:googleAccount% order by e.userId asc")
  public List<UserEntity> findUserByGoogleAccount(String googleAccount);
  
  @Transactional
  @Modifying
  @Query("update UserEntity e set  e.status = :status where e.userId = :userId")
  public void modifyUserStatus(Long userId, String status);
  
}
