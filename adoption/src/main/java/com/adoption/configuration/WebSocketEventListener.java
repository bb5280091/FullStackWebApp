package com.adoption.configuration;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.adoption.model.ChatMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long senderId = (Long) headerAccessor.getSessionAttributes().get("senderId");
        if (senderId != null) {
            log.info("user disconnected: {}", senderId);
            var chatMessage = ChatMessage.builder()
                    .type(ChatMessage.MessageType.LEAVE)
                    .senderId(senderId)
                    .build();
            messagingTemplate.convertAndSend("/topic/chat", chatMessage);
        }
    }

}
