package assignment1.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public void notification(Message message,Long userId) throws Exception{
        Thread.sleep(1000);
        messagingTemplate.convertAndSend("/topic/greetings/"+userId,message);
    }

}
