package com.example.chatting.pubsub;

import com.example.chatting.entity.dto.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

//Redis에 메시지 발행이 될 때까지 대기하였다가 메시지가 발행되면 해당 메시지를 읽어 처리하는 리스너 입니다.
//MessageListener를 상속받아 onMessge 메서드를 재작성합니다.
//아래에서 Redis에 메시지가 발행되면 해당 메시지를 ChatMessage로 변환하고 messaging Template을 이용하여
//채팅방의 모든 websocket 클라이언트들에게 메시지를 전달하도록 구현되어 있습니다.


@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    //➕❓
    // ObjectMapper란?
    //
    //JSON 컨텐츠를 Java 객체로 deserialization 하거나 Java 객체를 JSON으로 serialization 할 때 사용하는 Jackson 라이브러리의 클래스이다.

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            //redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer()
                    .deserialize(message.getBody());
            //ChatMessage 객체로 맵핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            //Websocket 구독자에게 채팅 메시지 send
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
