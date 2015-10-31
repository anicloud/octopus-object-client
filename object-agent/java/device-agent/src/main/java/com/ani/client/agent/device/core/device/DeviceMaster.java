package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.account.Account;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class DeviceMaster extends Device {
    private List<Device> slaves;

    public DeviceMaster() {
        this.slaves = null;
    }

    public DeviceMaster(DeviceInfo info, List<Function> functions, List<Device> slaves) {
        super(info, functions);
        this.slaves = slaves;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        super.serializeByte(dos);
        if (slaves == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(slaves.size());
            for (Device device : slaves) {
                device.serializeByte(dos);
            }
        }
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        super.unserializeByte(dis);
        int slaveSize = dis.readInt();
        if (slaveSize > 0) {
            slaves = new ArrayList<Device>();
            for (int i=0; i<slaveSize; i++) {
                Device device = new Device();
                device.unserializeByte(dis);
                slaves.add(device);
            }
        }
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public List<Device> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<Device> slaves) {
        this.slaves = slaves;
    }

}
