package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/26/15.
 */

import com.ani.client.agent.device.core.message.ByteSerializable;
import com.ani.client.agent.device.core.message.MessageUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Integer[] arg = new Integer(2) results in:
 * Argument arg = new(ArgumentType.INTEGER, 2);
 */
public class Argument implements ByteSerializable {
    private ArgumentType type;
    private Integer size;
    private List value;

    public Argument() {
    }

    public Argument(ArgumentType type, Integer size) {
        this.type = type;
        this.size = size;
    }

    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeByte(type.getValue());
        if (value == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(size);
            switch (type) {
                case BOOLEAN:
                    for (int i = 0; i < size; i++) {
                        dos.writeBoolean((Boolean) value.get(i));
                    }
                    break;
                case SHORT:
                    for (int i = 0; i < size; i++) {
                        dos.writeShort((Short) value.get(i));
                    }
                    break;
                case INTEGER:
                    for (int i = 0; i < size; i++) {
                        dos.writeInt((Integer) value.get(i));
                    }
                    break;
                case LONG:
                    for (int i = 0; i < size; i++) {
                        dos.writeLong((Long) value.get(i));
                    }
                    break;
                case FLOAT:
                    for (int i = 0; i < size; i++) {
                        dos.writeFloat((Float) value.get(i));
                    }
                    break;
                case DOUBLE:
                    for (int i = 0; i < size; i++) {
                        dos.writeDouble((Double) value.get(i));
                    }
                    break;
                case BYTE:
                    for (int i = 0; i < size; i++) {
                        dos.writeByte((Byte) value.get(i));
                    }
                    break;
                case STRING:
                    MessageUtils.writeString(dos, (String) value.get(0));
                    break;
                default:
                    break;
            }
        }
    }

    public void unserializeByte(DataInputStream dis) throws Exception {
        type = ArgumentType.getType((int) dis.readByte());
        size = dis.readInt();
        value = new ArrayList<>(size);
        switch (type) {
            case BOOLEAN:
                for (int i=0; i<size; i++) {
                    value.add(dis.readBoolean());
                }
                break;
            case SHORT:
                for (int i=0; i<size; i++) {
                    value.add(dis.readShort());
                }
                break;
            case INTEGER:
                for (int i=0; i<size; i++) {
                    value.add(dis.readInt());
                }
                break;
            case LONG:
                for (int i=0; i<size; i++) {
                    value.add(dis.readLong());
                }
                break;
            case FLOAT:
                for (int i=0; i<size; i++) {
                    value.add(dis.readFloat());
                }
                break;
            case DOUBLE:
                for (int i=0; i<size; i++) {
                    value.add(dis.readDouble());
                }
                break;
            case BYTE:
                for (int i=0; i<size; i++) {
                    value.add(dis.readByte());
                }
                break;
            case STRING:
                byte[] bytes = new byte[size];
                dis.read(bytes, 0, size);
                value.add(new String(bytes));
                break;
            default:
                break;
        }
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
