package com.ani.client.agent.device.core.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentAuthRequest extends MessageContent {

    public Long deviceId;

    public Long timestamp;

    public byte[] sign;

    public ContentAuthRequest() {

    }

    public ContentAuthRequest(Long deviceId, Long timestamp, byte[] sign) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        dos.writeLong(deviceId);
        dos.writeLong(timestamp);
        dos.writeInt(sign.length);
        dos.write(sign);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        deviceId = dis.readLong();
        timestamp = dis.readLong();
        int len = dis.readInt();
        sign = new byte[len];
        dis.read(sign, 0, len);
    }
}