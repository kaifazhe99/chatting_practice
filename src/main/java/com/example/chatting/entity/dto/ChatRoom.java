package com.example.chatting.entity.dto;


import com.example.chatting.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//pub/sub 방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션관리가 필요 없어집니다.
//또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하는 구현이 필요 없어집니다.
//따라서 채팅방 DTO는 다음과 같이 간소화 됩니다.

//➕
//세션(session)이란?
//
//세션(session)이란 웹 사이트의 여러 페이지에 걸쳐 사용되는 사용자 정보를 저장하는 방법을 의미합니다.
//사용자가 브라우저를 닫아 서버와의 연결을 끝내는 시점까지를 세션이라고 합니다

@Getter
public class ChatRoom {
    private String roomId;
    private String name;


    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }



}
