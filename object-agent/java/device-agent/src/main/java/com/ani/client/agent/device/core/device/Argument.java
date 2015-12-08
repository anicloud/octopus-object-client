package com.ani.client.agent.device.core.device;

/**
 * Created by huangbin on 10/26/15.
 */

import com.ani.client.agent.device.core.message.ByteSerializable;
import com.ani.client.agent.device.core.message.MessageUtils;
import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Integer[] arg = new Integer(2) results in:
 * Argument arg = new(ArgumentType.INTEGER, 2);
 */
public class Argument implements ByteSerializable {
    private Class declaredClass;
    private Object instance;

    private static final Map<Character, Class> PRIMITIVE_NAMES = new HashMap<>();

    static {
        PRIMITIVE_NAMES.put('N', Null.class);
        PRIMITIVE_NAMES.put('V', Void.class);
        PRIMITIVE_NAMES.put('T', Boolean.TYPE);
        PRIMITIVE_NAMES.put('B', Byte.TYPE);
        PRIMITIVE_NAMES.put('C', Character.TYPE);
        PRIMITIVE_NAMES.put('S', Short.TYPE);
        PRIMITIVE_NAMES.put('I', Integer.TYPE);
        PRIMITIVE_NAMES.put('L', Long.TYPE);
        PRIMITIVE_NAMES.put('F', Float.TYPE);
        PRIMITIVE_NAMES.put('D', Double.TYPE);
    }

    private static class Null {
        private Class declaredClass;

        public Null() {
        }

        public Null(Class declaredClass) {
            this.declaredClass = declaredClass;
        }

    }

    public Argument() {
    }

    public Argument(Class declaredClass, Object instance) {
        this.declaredClass = declaredClass;
        this.instance = instance;
    }

    public Object get() {
        return instance;
    }


    public Class getDeclaredClass() {
        return declaredClass;
    }

    public void serializeByte(DataOutputStream dos) throws IOException {
        writeObject(dos, instance, declaredClass);
    }

    public void unserializeByte(DataInputStream dis) throws IOException {
        instance = readObject(dis);
        if (instance != null) {
            declaredClass = instance.getClass();
        }
    }

    private Object readObject(DataInputStream dis) throws IOException {
        Object value;
        switch (dis.readChar()) {
            case '[': // array
                int len = dis.readInt();
                Object[] components;
                if (len > 0) {
                    components = new Object[len];
                    for (int i = 0; i < len; i++) {
                        components[i] = readObject(dis);
                        if (components[i] == null) { // An array's all elements are supposed to be primitive.
                            components = null;
                            break;
                        }
                    }
                    if (components != null) {
                        value = Array.newInstance(components[0].getClass(), len);
                        for (int i = 0; i < len; i++) {
                            Array.set(value, i, components[i]);
                        }
                    } else {
                        value = null;
                    }
                } else {
                    value = null;
                }
                break;
            case 'T': // boolean
                value = Boolean.valueOf(dis.readBoolean());
                break;
            case 'B': // byte
                value = Byte.valueOf(dis.readByte());
                break;
            case 'C': // char
                value = Character.valueOf(dis.readChar());
                break;
            case 'S': // short
                value = Short.valueOf(dis.readShort());
                break;
            case 'I': // int
                value = Integer.valueOf(dis.readInt());
                break;
            case 'L': // long
                value = Long.valueOf(dis.readLong());
                break;
            case 'F': // float
                value = Float.valueOf(dis.readFloat());
                break;
            case 'D': // double
                value = Double.valueOf(dis.readDouble());
                break;
            case 'N': // null
            default: // unknown
                value = null;
        }
        return value;
    }

    private void writeObject(DataOutputStream dos, Object instance, Class declaredClass) throws IOException {
        if (instance == null) {
            declaredClass = Null.class;
        }

        if (declaredClass.isArray()) { // array
            dos.writeChar('[');
            int len = Array.getLength(instance);
            dos.writeInt(len);
            for (int i = 0; i < len; i++) {
                writeObject(dos, Array.get(instance, i), declaredClass.getComponentType());
            }
        } else if (declaredClass == Boolean.class || declaredClass == boolean.class) {
            dos.writeChar('T');
            dos.writeBoolean(((Boolean) instance).booleanValue());
        } else if (declaredClass == Character.class || declaredClass == char.class) {
            dos.writeChar('C');
            dos.writeChar(((Character) instance).charValue());
        } else if (declaredClass == Byte.class || declaredClass == byte.class) {
            dos.writeChar('B');
            dos.writeByte(((Byte) instance).byteValue());
        } else if (declaredClass == Short.class || declaredClass == short.class) {
            dos.writeChar('S');
            dos.writeShort(((Short) instance).shortValue());
        } else if (declaredClass == Integer.class || declaredClass == int.class) {
            dos.writeChar('I');
            dos.writeInt(((Integer) instance).intValue());
        } else if (declaredClass == Long.class || declaredClass == long.class) {
            dos.writeChar('L');
            dos.writeLong(((Long) instance).longValue());
        } else if (declaredClass == Float.class || declaredClass == float.class) {
            dos.writeChar('F');
            dos.writeFloat(((Float) instance).floatValue());
        } else if (declaredClass == Double.class || declaredClass == double.class) {
            dos.writeChar('D');
            dos.writeDouble(((Double) instance).doubleValue());
        } else if (declaredClass == Null.class) { // null
            dos.writeChar('N');
        } else {
            throw new IOException("cannot write the object type: " + declaredClass.getCanonicalName());
        }

    }

    private void writeObjectType(StringBuffer ss, Class declaredClass) throws IOException {
        if (declaredClass.isArray()) {
            ss.append('[');
            writeObjectType(ss, declaredClass.getComponentType());
        } else if (declaredClass == Boolean.class) {
            ss.append('T');
        } else if (declaredClass == Character.class) {
            ss.append('C');
        } else if (declaredClass == Byte.class) {
            ss.append('B');
        } else if (declaredClass == Short.class) {
            ss.append('S');
        } else if (declaredClass == Integer.class) {
            ss.append('I');
        } else if (declaredClass == Long.class) {
            ss.append('L');
        } else if (declaredClass == Float.class) {
            ss.append('F');
        } else if (declaredClass == Double.class) {
            ss.append('D');
        } else if (declaredClass == Null.class) {
            ss.append('N');
        } else {
            throw new IOException("cannot write object type: " + declaredClass.getName());
        }
    }

}
