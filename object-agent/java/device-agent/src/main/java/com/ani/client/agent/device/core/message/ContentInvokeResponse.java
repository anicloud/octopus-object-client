package com.ani.client.agent.device.core.message;



import com.ani.client.agent.device.core.device.FunctionInstance;
import com.ani.client.agent.device.core.device.ResultType;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/27/15.
 */
public class ContentInvokeResponse extends MessageContent {
    public ResultType result;
    public FunctionInstance instance;

    public ContentInvokeResponse() {
    }

    public ContentInvokeResponse(ResultType result, FunctionInstance instance) {
        this.result = result;
        this.instance = instance;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeByte(result.getValue());
        instance.serializeByte(dos);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        result = ResultType.getType((int) dis.readByte());
        instance = new FunctionInstance();
        instance.unserializeByte(dis);
    }
}
