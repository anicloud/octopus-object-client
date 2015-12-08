package com.ani.client.agent.device.core.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentRegisterRequest extends MessageContent {
    public String name;

    public String physicalId;

    public String physicalAddress;

    public String description;

    public ContentRegisterRequest() {
    }

    public ContentRegisterRequest(String name, String physicalId, String physicalAddress, String description) {
        this.name = name;
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.description = description;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        MessageUtils.writeString(dos, name);
        MessageUtils.writeString(dos, physicalId);
        MessageUtils.writeString(dos, physicalAddress);
        MessageUtils.writeString(dos, description);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        name = MessageUtils.readString(dis);
        physicalId = MessageUtils.readString(dis);
        physicalAddress = MessageUtils.readString(dis);
        description = MessageUtils.readString(dis);
    }
}
