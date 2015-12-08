package com.ani.client.agent.device.core.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/19/15.
 */
public interface ByteSerializable {
    public void serializeByte(DataOutputStream dos) throws IOException;
    public void unserializeByte(DataInputStream dis) throws IOException;
}
