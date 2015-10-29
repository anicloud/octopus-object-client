package com.ani.client.agent.device;

import com.ani.client.agent.device.core.socket.IoHandler;
import com.ani.client.agent.device.core.socket.TcpClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            TcpClient client = new TcpClient();
            IoHandler ioHandler = new IoHandler(client);
            client.connect("127.0.0.1", 1222);
            client.startLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
