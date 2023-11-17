package com.adoption.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.adoption.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

  List<MessageEntity> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestamp(
      Long senderId1, Long receiverId1, Long senderId2, Long receiverId2);

  @Query("SELECT m " + "FROM MessageEntity m "
      + "WHERE (m.senderId = :userId OR m.receiverId = :userId) "
      + "AND m.timestamp = (SELECT MAX(m2.timestamp) " + "                 FROM MessageEntity m2 "
      + "                 WHERE (m2.senderId = m.senderId AND m2.receiverId = m.receiverId) "
      + "                    OR (m2.senderId = m.receiverId AND m2.receiverId = m.senderId))"
      + "ORDER BY m.timestamp DESC")
  List<MessageEntity> findLatestMessagesForUser(Long userId);
}
