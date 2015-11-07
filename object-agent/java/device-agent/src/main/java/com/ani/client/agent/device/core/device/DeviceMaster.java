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

    /**
     * Master auth token
     * NotNull
     */
    private byte[] token;

    /**
     * Slave devices.
     * Nullable
     */
    private List<DeviceSlave> slaves;

    public DeviceMaster() {
    }

    public DeviceMaster(byte[] token, List<DeviceSlave> slaves) {
        this.token = token;
        this.slaves = slaves;
    }

    public DeviceMaster(String name, String physicalId, String physicalAddress, String description, List<Function> functions, List<DeviceSlave> slaves) {
        super(name, physicalId, physicalAddress, description, functions);
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
            slaves = new ArrayList<DeviceSlave>();
            for (int i=0; i<slaveSize; i++) {
                DeviceSlave slave = new DeviceSlave(this);
                slave.unserializeByte(dis);
                slaves.add(slave);
            }
        }
    }

    public List<DeviceSlave> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<DeviceSlave> slaves) {
        this.slaves = slaves;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }
}
