package com.dqt.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    //    Bước 1: đăng ký kết nối với websocket
//@Override
//public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("/dqt");
//}

//    Bước 2: cấu hình prefix url cho client gọi đến server & tạo phòng chat
//@Override
//public void configureMessageBroker(MessageBrokerRegistry config) {
//    config.setApplicationDestinationPrefixes("/app");
//    config.enableSimpleBroker("/topic","username");
//}

//    Bước 3: - tạo phương thức  client => message(trước xử lý logic) => server   : @MessageMapping         /app/hello
//            - tạo phòng chat   server => message(sau xử lý logic)   => client   : @SendTo                 /topic/greetings


//client gửi lên server URL: /app/hello và phương thức MessageMapping
//server sau khi xử lý sẽ đẩy về client(tạo phòng): /topic/greetings

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    *Các bước WebSocket & Spring Boot:
1. Config endpoint
2. Send <-> Recive

(- Server -> Client:
1. @SendTo(topic)
2. SimpMessagingTemplate.convertAndSend(topic,data)
3. SimpMessagingTemplate.convertAndSendToUser(username,topic,data)

- Client -> Server:
@MessageMapping)

config.setApplicationDestinationPrefixes("/app");
config.setUserDestinationPrefix("user");

để set prefixes khi Client -> Server

Client:
1. Stompt.over('ws://localhost:8080/websocket')
2. Connect()
3.
	Subcribe
	Send
    * */


    @MessageMapping("/chat-public")
    @SendTo("/public")
    public String chatPublic(@Payload String message) {
        return message;
    }

    @MessageMapping("/chat/{groupId}")
    public void sendToGroup(@DestinationVariable String groupId, String message) {
        simpMessagingTemplate.convertAndSend("/chat/group/" + groupId, message);
    }

    @MessageMapping("/chat-private")
    public String chatPrivate(@Payload String message, Integer userId) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(userId), "/private", message);
        return message;
    }

}
