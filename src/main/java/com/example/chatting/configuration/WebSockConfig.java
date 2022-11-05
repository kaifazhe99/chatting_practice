package com.example.chatting.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//Stomp를 사용하기 위해 @EnableWebSocketMessageBroker을 선언하고
//WebSocketMessageBrokerConfigurer을 상속받아 configureMessageBroker를 구현합니다.
//pub/sub 메시징을 구현하기 위해 메시지를 발행하는 요청의 prefix는 /pub 로 시작하도록 설정하고
//메시지를 구독하는 요청의 prefix는 /sub로 시작하도록 설정합니다. 그리고 stomp websocket의 연결
//endpoint 는 /ws-stomp로 설정합니다.

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
                .withSockJS();
    }





}
