package com.adoption.service;

import org.springframework.messaging.handler.annotation.Payload;
import com.adoption.dto.GetDiscussionRespDTO;
import com.adoption.dto.GetMessageRespDTO;
import com.adoption.model.ChatMessage;
import com.adoption.model.Discussion;

public interface MessageService {

  ChatMessage sendMessage(@Payload ChatMessage message);

  GetMessageRespDTO queryAllMessage(Long userId);

  GetMessageRespDTO queryChatroomMessage(Long userId, Long otherId);

  Discussion sendDiscussionMessage(Discussion discussion);

  GetDiscussionRespDTO queryDiscussionByAnimalId(Long animalId);

}
