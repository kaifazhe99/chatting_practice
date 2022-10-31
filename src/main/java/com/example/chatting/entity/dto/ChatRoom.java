package com.example.chatting.entity.dto;


import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;


//클라이언트들을 서버에 접속하면 개별의 WebSocketSession을 가지게 됩니다.
//따라서 채팅방에 입장시 클라이언트의 WebSocketSession정보를 채팅방에 맵핑시켜 보관하고 있으면
//서버에 전달된 메시지를 특정 방의 WebSocket 세션으로 보낼 수 있으므로 개별의 채팅방을 구현할 수 있습니다.

//채팅방을 구현하기 위해 DTO를 하나 만듭니다.
//채팅방은 입장한 클라이언트의 정보를 가지고 있어야 하므로 WebSocketSession 정보 리스트를 멤버 필드로 갖습니다.
///나머지 멤버 필드로 채팅방 id, 채팅방 이름을 추가합니다.
//채팅방에서는 입장, 대화하기의 기능이 있으므로 handleAction을 통해 분기 처리합니다.
//입장 시에는 채팅룸의 session리스트에 추가해 놓았다가 채팅룸에 메시지가 도착할 경우 채팅룸의 모든 session에 메시지를 발송하면
//채팅이 완성됩니다.

//➕
//세션(session)이란?
//
//세션(session)이란 웹 사이트의 여러 페이지에 걸쳐 사용되는 사용자 정보를 저장하는 방법을 의미합니다.
//사용자가 브라우저를 닫아 서버와의 연결을 끝내는 시점까지를 세션이라고 합니다

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 spring에서 WebSocket connection이 맺어진 세션을 가리킨다.

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");

        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));

    }

}
