package com.ani.client.agent.device.core.message;

import java.io.*;

/**
 * Created by huangbin on 10/18/15.
 */
public class Message implements ByteSerializable {
    public MessageType type;
    public MessageContent content;

    public Message() {
    }

    public Message(MessageType type, MessageContent content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeShort(type.getValue());
        content.serializeByte(dos);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        type = MessageType.getType((int) dis.readShort());
        switch (type) {
            case REGISTER_REQUEST:
                content = new ContentRegisterRequest();
                break;
            case REGISTER_RESPONSE:
                content = new ContentRegisterResponse();
                break;
            case AUTH_REQUEST:
                content = new ContentAuthRequest();
                break;
            case AUTH_RESPONSE:
                content = new ContentAuthResponse();
                break;
            case UPDATE_REQUEST:
                content = new ContentUpdateRequest();
                break;
            case UPDATE_RESPONSE:
                content = new ContentUpdateResponse();
                break;
            case INVOKE_REQUEST:
                content = new ContentInvokeRequest();
                break;
            case INVOKE_RESPONSE:
                content = new ContentInvokeResponse();
                break;
            default:
                throw new Exception();
        }
        content.unserializeByte(dis);
    }

}
