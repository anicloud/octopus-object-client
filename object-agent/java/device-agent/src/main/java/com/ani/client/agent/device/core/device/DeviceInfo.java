package com.ani.client.agent.device.core.device;


import com.ani.client.agent.device.core.message.ByteSerializable;
import com.ani.client.agent.device.core.message.MessageUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/27/15.
 */
public class DeviceInfo implements ByteSerializable {

    private String name;

    private String physicalId;

    private String physicalAddress;

    private String description;

    public DeviceInfo() {
    }

    public DeviceInfo(String name, String physicalId, String physicalAddress, String description) {
        this.name = name;
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.description = description;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        MessageUtils.writeString(dos, name);
        MessageUtils.writeString(dos, physicalId);
        MessageUtils.writeString(dos, physicalAddress);
        MessageUtils.writeString(dos, description);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        name = MessageUtils.readString(dis);
        physicalId = MessageUtils.readString(dis);
        physicalAddress = MessageUtils.readString(dis);
        description = MessageUtils.readString(dis);
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "name='" + name + '\'' +
                ", physicalId='" + physicalId + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(String physicalId) {
        this.physicalId = physicalId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
