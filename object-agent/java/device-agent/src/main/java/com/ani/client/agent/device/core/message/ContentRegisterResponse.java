package com.ani.client.agent.device.core.message;


import com.ani.client.agent.device.core.device.ResultType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentRegisterResponse extends MessageContent {
    public ResultType result;

    public Long deviceId;

    public byte[] token;

    public ContentRegisterResponse() {

    }

    public ContentRegisterResponse(ResultType result, Long deviceId, byte[] token) {
        this.result = result;
        this.deviceId = deviceId;
        this.token = token;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        dos.writeByte(result.getValue());
        if (result == ResultType.SUCCESS) {
            dos.writeLong(deviceId);
            dos.write(token, 0, 16);
        }
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        result = ResultType.getType((int) dis.readByte());
        if (result == ResultType.SUCCESS) {
            deviceId = dis.readLong();
            token = new byte[16];
            dis.read(token, 0, 16);
        } else {
            deviceId = -1l;
            token = null;
        }
    }
}
