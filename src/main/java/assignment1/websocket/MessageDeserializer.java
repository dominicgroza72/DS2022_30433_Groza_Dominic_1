package assignment1.websocket;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MessageDeserializer extends JsonDeserializer<Message> {


    @Override
    public Message deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        // extract values from array and create a Message object
        String sender = node.get(0).asText();
        String content = node.get(1).asText();
        Message message = new Message(sender, content);
        return message;
    }
}
