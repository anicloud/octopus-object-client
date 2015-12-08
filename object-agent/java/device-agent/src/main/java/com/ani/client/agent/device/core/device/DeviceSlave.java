package com.ani.client.agent.device.core.device;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by huangbin on 11/3/15.
 */
public class DeviceSlave extends Device {
    private DeviceMaster master;

    private DeviceSlave() {}

    public DeviceSlave(DeviceMaster master) {
        this.master = master;
    }

    public DeviceSlave(String name, String physicalId, String physicalAddress, String description, List<Function> functions, DeviceMaster master) {
        super(name, physicalId, physicalAddress, description, functions);
        this.master = master;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        super.serializeByte(dos);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        super.unserializeByte(dis);
    }
}
