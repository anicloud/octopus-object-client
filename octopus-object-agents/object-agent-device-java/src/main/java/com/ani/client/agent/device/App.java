package com.ani.client.agent.device;

import com.ani.client.agent.device.core.agent.DeviceAgent;
import com.ani.client.agent.device.core.agent.XinwoDeviceController;
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
            XinwoDeviceController controller = new XinwoDeviceController();
            DeviceAgent agent = new DeviceAgent(controller);
            agent.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
