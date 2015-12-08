package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by huangbin on 10/22/15.
 */
public class Function implements ByteSerializable {
      /**
     * Function id provided by Anicloud.
     */
    private Integer id;

    /**
     * Function group id provided by Anicloud.
     */
    private Long groupId;

    private ConnType type;

    public Function() {
    }

    public Function(Integer id, Long groupId, ConnType type) {
        this.id = id;
        this.groupId = groupId;
        this.type = type;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
        dos.writeLong(groupId);
        dos.writeByte(type.getValue());
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        id = dis.readInt();
        groupId = dis.readLong();
        type = ConnType.getType((int) dis.readByte());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public ConnType getType() {
        return type;
    }

    public void setType(ConnType type) {
        this.type = type;
    }

}
