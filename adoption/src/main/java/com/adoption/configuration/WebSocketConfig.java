package com.adoption.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  // 註冊一個給Client連至WebSocket Server的節點(websocket endpoint)
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:4200").withSockJS();// setAllowedOrigins解決CORS policy
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 啟用一個訊息代理並設定訊息發送目地的前綴路徑
    registry.enableSimpleBroker("/topic");
    // 設定導向至訊息處理器的前綴路徑
    registry.setApplicationDestinationPrefixes("/app");
  }

}
