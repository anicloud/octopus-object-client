package com.ani.client.agent.device.core.message;


import com.ani.client.agent.device.core.device.FunctionInstance;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/27/15.
 */
public class ContentInvokeRequest extends MessageContent {
    public FunctionInstance instance;

    public ContentInvokeRequest() {
    }

    public ContentInvokeRequest(FunctionInstance instance) {
        this.instance = instance;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        instance.serializeByte(dos);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        instance.unserializeByte(dis);
    }
}
