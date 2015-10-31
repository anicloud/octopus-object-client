package com.ani.client.agent.device.core.message;


import com.ani.client.agent.device.core.device.ResultType;

import java.io.DataInputStream;
import java.io.DataOutputStream;

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
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeByte(result.getValue());
        dos.writeLong(deviceId);
        dos.write(token, 0, 128);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        result = ResultType.getType((int) dis.readByte());
        deviceId = dis.readLong();
        token = new byte[128];
        dis.read(token, 0, 128);
    }
}
