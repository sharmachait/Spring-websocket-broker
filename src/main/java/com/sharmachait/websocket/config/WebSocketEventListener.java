package com.sharmachait.websocket.config;

import com.sharmachait.websocket.dto.ChatMessage;
import com.sharmachait.websocket.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSender;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String)accessor.getSessionAttributes().get("username");
        if(username!=null && !username.isBlank()){
            log.info("User Disconnected: " + username);
            ChatMessage chatMessage = ChatMessage.builder()
                    .messagetype(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messageSender.convertAndSend("/topic/public", chatMessage);
        }
    }
}
