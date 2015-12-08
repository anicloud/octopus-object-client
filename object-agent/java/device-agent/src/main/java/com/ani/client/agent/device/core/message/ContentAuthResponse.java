package com.ani.client.agent.device.core.message;


import com.ani.client.agent.device.core.device.ResultType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentAuthResponse extends MessageContent {

    public ResultType result;

    public ContentAuthResponse() {

    }

    public ContentAuthResponse(ResultType result) {
        this.result = result;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        dos.writeByte(result.getValue());
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        result = ResultType.getType((int) dis.readByte());
    }
}
