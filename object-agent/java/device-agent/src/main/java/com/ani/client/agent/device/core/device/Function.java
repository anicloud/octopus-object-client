package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/22/15.
 */
public class Function implements ByteSerializable {
    private Integer id;
    private Integer groupId;
    private FunctionType type;

    public Function() {
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeInt(id);
        dos.writeInt(groupId);
        dos.writeInt(type.getValue());
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        id = dis.readInt();
        groupId = dis.readInt();
        type = FunctionType.getType(dis.readInt());
        int argSize = dis.readInt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public FunctionType getType() {
        return type;
    }

    public void setType(FunctionType type) {
        this.type = type;
    }

}
