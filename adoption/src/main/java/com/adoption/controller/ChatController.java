package com.adoption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.adoption.model.ChatMessage;
import com.adoption.model.Discussion;
import com.adoption.service.MessageService;

@Controller
@CrossOrigin("http://localhost:4200")
public class ChatController {

  @Autowired
  MessageService service;
  
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  //for chatroom
  @MessageMapping("/chat.register") // 連結user跟websocket
  @SendTo("")
  public ChatMessage register(@Payload ChatMessage message,
      SimpMessageHeaderAccessor headerAccessor) {
    headerAccessor.getSessionAttributes().put("userId", message.getSenderId());
    // 註冊、訂閱一對一聊天室，以防他人也收得到訊息
    String topic = "";
    if (message.getSenderId() > message.getReceiverId()) {
      topic = "/topic/chat/" + message.getSenderId() + "/" + message.getReceiverId();
    } else {
      topic = "/topic/chat/" + message.getReceiverId() + "/" + message.getSenderId();
    }
    messagingTemplate.convertAndSend(topic, message);
    return message;
  }

  @MessageMapping("/chat.send") // from endpoint
  @SendTo("") // will send the message to this queue
  public ChatMessage sendMessage(@Payload ChatMessage message) {
    return service.sendMessage(message);
  }

  //for discussion)
  @MessageMapping("/discuss.register") // 連結user跟websocket
  @SendTo("")
  public Discussion register(@Payload Discussion discussion,
      SimpMessageHeaderAccessor headerAccessor) {
    headerAccessor.getSessionAttributes().put("userId", discussion.getUserId());
    String topic = "/topic/discuss/" + discussion.getAnimalId();
    messagingTemplate.convertAndSend(topic, discussion);
    return discussion;
  }

  @MessageMapping("/discuss.send") // from endpoint
  @SendTo("") // will send the message to this queue
  public Discussion sendDiscussionMessage(@Payload Discussion discussion) {
    return service.sendDiscussionMessage(discussion);
  }

}

