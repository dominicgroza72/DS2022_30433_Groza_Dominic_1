package assignment1.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {
        return message;
    }

    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message) {
        System.out.println(message.getMessage()+" ---- ");
        simpMessagingTemplate.convertAndSendToUser(message.getToUsername(), "/specific", message);
    }
}
