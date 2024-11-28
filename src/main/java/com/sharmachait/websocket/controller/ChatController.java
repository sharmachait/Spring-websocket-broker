package com.sharmachait.websocket.controller;

import com.sharmachait.websocket.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public class ChatController {
    @MessageMapping("/chat/message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage message
    ) {
        return message;
    }

    @MessageMapping("/chat/join")
    @SendTo("/topic/public")
    public ChatMessage addMessage(
            @Payload ChatMessage message,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
