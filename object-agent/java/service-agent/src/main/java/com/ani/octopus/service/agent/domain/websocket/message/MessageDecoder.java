package com.ani.octopus.service.agent.domain.websocket.message;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class MessageDecoder implements Decoder.Text<Message> {

    private ObjectMapper objectMapper;

    public MessageDecoder() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Message decode(String s) throws DecodeException {
        // determine from s to the Message kind
        Message message = null;
        try {
            if (s != null && s.contains(MessageType.CALL_ANI_OBJECT.toString())) {
                message = objectMapper.readValue(s, AniObjectCallMessage.class);
            }
            if (s != null && s.contains(MessageType.CALL_SYSTEM.toString())) {
                message = objectMapper.readValue(s, AniFunctionCallMessage.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        // determine if the message can be converted into a message
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
