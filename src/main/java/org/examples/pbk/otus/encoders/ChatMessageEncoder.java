package org.examples.pbk.otus.encoders;

import org.examples.pbk.otus.messages.ChatMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)){
            jsonGenerator.writeStartObject()
                    .write("type", "chat")
                    .write("from", chatMessage.getFrom())
                    .write("to", chatMessage.getTo())
                    .write("text", chatMessage.getText())
                .writeEnd();
        }
        return stringWriter.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
