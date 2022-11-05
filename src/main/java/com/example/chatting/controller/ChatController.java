package com.example.chatting.controller;


import com.example.chatting.entity.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

//@MessageMapping을 통해 Websocket으로 들어오는 메시지 발행을 처리합니다.
//클라이언트에서 prefix를 붙여서 /pub/chat/message로 발행요청을 하면
//Controller가 해당 메시지를 받아 처리합니다.
//메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 Send 하는 것을 볼 수 있는데
//클라이언트에서는 해당주소를 (/sub/chat/room/{roomId}) 구독(subscribe)하고 있다가 메시지가 전달되면
//화면에 출력하면 됩니다. 여기서 /sub/chat/room/{roomId}는 채팅룸을 구분하는 값이므로
// pub/sub에서 Topic의 역할이라고 보면 됩니다.
//기존의 WebSockChatHandler가 했던 역할을 대체하므로 WebSockChatHandler는 삭제합니다.





@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

   private final SimpMessageSendingOperations messagingTemplate;

   @MessageMapping("/message")
    public void message (ChatMessage message){
       if(ChatMessage.MessageType.JOIN.equals(message.getType())){
           message.setMessage(message.getSender()+ "님이 입장하셨습니다.");
           messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId());

       }
   }
}

