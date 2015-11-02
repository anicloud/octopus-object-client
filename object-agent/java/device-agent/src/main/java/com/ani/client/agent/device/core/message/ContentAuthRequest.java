package com.ani.client.agent.device.core.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentAuthRequest extends MessageContent {

    public Long deviceId;

    public Long timestamp;

    public String sign;

    public ContentAuthRequest() {

    }

    public ContentAuthRequest(Long deviceId, Long timestamp, String digest) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.sign = digest;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeLong(deviceId);
        dos.writeLong(timestamp);
        MessageUtils.writeString(dos, sign);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        deviceId = dis.readLong();
        timestamp = dis.readLong();
        sign = MessageUtils.readString(dis);
    }
}
