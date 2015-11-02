package com.ani.client.agent.device.core.message;

import com.ani.client.agent.device.core.device.DeviceInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentRegisterRequest extends MessageContent {
    public DeviceInfo info;

    public ContentRegisterRequest() {
    }

    public ContentRegisterRequest(DeviceInfo info) {
        this.info = info;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        info.serializeByte(dos);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        info = new DeviceInfo();
        info.unserializeByte(dis);
    }
}
