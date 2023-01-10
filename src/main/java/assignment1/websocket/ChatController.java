package assignment1.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
    public void sendToSpecificUser(@Payload byte[] message) {
        String s = new String(message, StandardCharsets.UTF_8);
        System.out.println(s);
//        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
//             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
//
//            // Read the object from the byte array
//            Message message1 = (Message) objectInputStream.readObject();
//            System.out.println(message1.getMessage()+" ---- ");
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        simpMessagingTemplate.convertAndSendToUser(message.getToUsername(), "/specific", message);
    }
}
