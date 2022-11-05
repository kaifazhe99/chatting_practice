package com.example.chatting.handler;

import com.example.chatting.entity.dto.ChatMessage;
import com.example.chatting.entity.dto.ChatRoom;
import com.example.chatting.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//위에서 만든 채팅 로직을 handler에 추가합니다.
// 🎱웹소캣 클라이언트로부터 채팅 메시지를 전달받아 채팅 메시지 객체로 변환
// 🎱전달받은 메시제에 담긴 채팅방 Id로 발송 대상 채팅방 정보를 조회함
// 🎱해당 채팅방에 입장해있는 모든 클라이언트(Websocket session)에게 타입에 따른 메시지 발송


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
//        TextMessage textMessage = new TextMessage("하이욤");
//        session.sendMessage(textMessage);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleAction(session, chatMessage, chatService);
    }

}

