package com.adoption.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.adoption.dto.DiscussionDTO;
import com.adoption.dto.GetDiscussionRespDTO;
import com.adoption.dto.GetMessageRespDTO;
import com.adoption.dto.Message;
import com.adoption.entity.DiscussionEntity;
import com.adoption.entity.MessageEntity;
import com.adoption.model.ChatMessage;
import com.adoption.model.Discussion;
import com.adoption.repository.DiscussionRepository;
import com.adoption.repository.MessageRepository;
import com.adoption.repository.UserRepository;
import com.adoption.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private DiscussionRepository discussionRepository;
  @Autowired
  private UserRepository userRepository;

  @Override
  public ChatMessage sendMessage(@Payload ChatMessage message) {
    MessageEntity messageEntity = new MessageEntity();
    messageEntity.setReceiverId(message.getReceiverId());
    messageEntity.setSenderId(message.getSenderId());
    messageEntity.setContent(message.getContent());
    messageEntity.setTimestamp(LocalDateTime.now());
    // Save the message to the database
    messageRepository.save(messageEntity);
    // Send the message to the recipient
    String topic = "";
    if (message.getSenderId() > message.getReceiverId()) {
      topic = "/topic/chat/" + message.getSenderId() + "/" + message.getReceiverId();
    } else {
      topic = "/topic/chat/" + message.getReceiverId() + "/" + message.getSenderId();
    }
    messagingTemplate.convertAndSend(topic, message);
    return message;
  }

  @Override
  public GetMessageRespDTO queryAllMessage(Long userId) {
    GetMessageRespDTO getMessageRespDTO = new GetMessageRespDTO();
    List<Message> messagesList = new ArrayList<>();
    List<MessageEntity> entities = messageRepository.findLatestMessagesForUser(userId);
    for (MessageEntity entity : entities) {
      Message message = new Message();
      // 要對方的名字
      if (userId == entity.getSenderId()) {
        message.setName(userRepository.findByUserId(entity.getReceiverId()).get(0).getName());
      } else {
        message.setName(userRepository.findByUserId(entity.getSenderId()).get(0).getName());
      }
      message.setSenderId(entity.getSenderId());
      message.setReceiverId(entity.getReceiverId());
      message.setContent(entity.getContent());
      message.setTime(entity.getTimestamp());
      messagesList.add(message);
    }
    getMessageRespDTO.setMessages(messagesList);
    return getMessageRespDTO;
  }

  @Override
  public GetMessageRespDTO queryChatroomMessage(Long userId, Long otherId) {
    GetMessageRespDTO getMessageRespDTO = new GetMessageRespDTO();
    List<Message> messagesList = new ArrayList<>();
    List<MessageEntity> entities =
        messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestamp(userId,
            otherId, otherId, userId);
    for (MessageEntity entity : entities) {
      Message message = new Message();
      if (userId == entity.getSenderId()) {
        message.setName(userRepository.findByUserId(entity.getReceiverId()).get(0).getName());
      } else {
        message.setName(userRepository.findByUserId(entity.getSenderId()).get(0).getName());
      }
      message.setSenderId(entity.getSenderId());
      message.setReceiverId(entity.getReceiverId());
      message.setContent(entity.getContent());
      message.setTime(entity.getTimestamp());
      messagesList.add(message);
    }
    getMessageRespDTO.setMessages(messagesList);
    return getMessageRespDTO;
  }

  @Override
  public Discussion sendDiscussionMessage(Discussion discussion) {
    DiscussionEntity discussionEntity = new DiscussionEntity();
    discussionEntity.setUserId(discussion.getUserId());
    discussionEntity.setAnimalId(discussion.getAnimalId());
    discussionEntity.setSerialNo(discussion.getSerialNo());
    discussionEntity.setReplyNo(discussion.getReplyNo());
    discussionEntity.setContent(discussion.getContent());
    discussionEntity.setTimestamp(LocalDateTime.now());
    // Save the message to the database
    discussionRepository.save(discussionEntity);
    // Send the message to the recipient
    String topic = "/topic/discuss/" + discussion.getAnimalId();
    messagingTemplate.convertAndSend(topic, discussion);
    return discussion;
  }

  @Override
  public GetDiscussionRespDTO queryDiscussionByAnimalId(Long animalId) {
    GetDiscussionRespDTO GetDiscussionRespDTO = new GetDiscussionRespDTO();
    List<DiscussionDTO> discussionList = new ArrayList<>();
    List<DiscussionEntity> entities =
        discussionRepository.findByAnimalIdOrderBySerialNoAscReplyNoAsc(animalId);
    for (DiscussionEntity entity : entities) {
      DiscussionDTO discussion = new DiscussionDTO();
      discussion.setName(userRepository.findByUserId(entity.getUserId()).get(0).getName());
      discussion.setContent(entity.getContent());
      discussion.setTimestamp(entity.getTimestamp());
      discussion.setSerialNo(entity.getSerialNo());
      discussion.setReplyNo(entity.getReplyNo());
      discussion.setUserId(entity.getUserId());;
      discussionList.add(discussion);
    }
    GetDiscussionRespDTO.setDiscussion(discussionList);
    return GetDiscussionRespDTO;
  }

}
