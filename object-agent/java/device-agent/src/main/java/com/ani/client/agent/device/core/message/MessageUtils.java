package com.ani.client.agent.device.core.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/22/15.
 */
public class MessageUtils {
    private static final Charset charset = Charset.forName("UTF-8");

    private MessageUtils() {

    }

    public static void writeString(DataOutputStream dos, String in) throws Exception{
        byte [] bytes = in.getBytes(charset);
        dos.writeInt(bytes.length);
        dos.write(bytes);
    }

    public static String readString(DataInputStream dis) throws Exception {
        int length = dis.readInt();
        byte[] bytes = new byte[length];
        dis.read(bytes, 0, length);
        return new String(bytes);
    }

    public static Message decodeMessage(byte[] in) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        DataInputStream dis = new DataInputStream(bais);
        Message message = new Message();
        message.unserializeByte(dis);
        return message;
    }

    public static byte[] encodeMessage(Message message) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        message.serializeByte(dos);
        return baos.toByteArray();
    }


}
