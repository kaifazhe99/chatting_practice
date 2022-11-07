package com.example.chatting.entity.dto;


import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;


//pub/sub 방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션관리가 필요 없어집니다.
//또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하는 구현이 필요 없어집니다.
//따라서 채팅방 DTO는 다음과 같이 간소화 됩니다.


//Redis에 저장되는 객체들은 Serialize기능해야 하므로 Serializable을 참조하도록 선언하고 serialVersionUID를 세팅해 줍니다.
//➕
//세션(session)이란?
//
//세션(session)이란 웹 사이트의 여러 페이지에 걸쳐 사용되는 사용자 정보를 저장하는 방법을 의미합니다.
//사용자가 브라우저를 닫아 서버와의 연결을 끝내는 시점까지를 세션이라고 합니다

@Getter
public class ChatRoom implements Serializable {
    //➕❓
    //그래서 직렬화가 무엇인가?
    //
    //자바 직렬화란 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부의 자바 시스템에서도 사용할 수 있도록
    // 바이트(byte) 형태로 데이터 변환하는 기술과 바이트로 변환된 데이터를 다시 객체로 변환하는 기술(역직렬화)을 아울러서 이야기합니다.
    //시스템적으로 이야기하자면 JVM(Java Virtual Machine 이하 JVM)의 메모리에 상주(힙 또는 스택)되어 있는 객체 데이터를
    // 바이트 형태로 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로 변환해서 JVM으로 상주시키는 형태를 같이 이야기합니다.

    private  static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String name;


    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }


}
