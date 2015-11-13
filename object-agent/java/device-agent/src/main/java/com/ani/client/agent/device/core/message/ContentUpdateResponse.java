package com.ani.client.agent.device.core.message;

import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.ResultType;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/23/15.
 */
public class ContentUpdateResponse extends MessageContent {
    public ResultType result;

    public DeviceMaster deviceMaster;

    public ContentUpdateResponse() {
    }

    public ContentUpdateResponse(ResultType result, DeviceMaster deviceMaster) {
        this.result = result;
        this.deviceMaster = deviceMaster;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeByte(result.getValue());
        if (result == ResultType.SUCCESS) {
            deviceMaster.serializeByte(dos);
        }
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        result = ResultType.getType((int) dis.readByte());
        if (result == ResultType.SUCCESS) {
            deviceMaster = new DeviceMaster();
            deviceMaster.unserializeByte(dis);
        }
    }
}
