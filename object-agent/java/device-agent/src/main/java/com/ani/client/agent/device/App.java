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
            /**
             * Instantiate a device controller object here.
             */
            XinwoDeviceController controller = new XinwoDeviceController();

            /**
             * Instantiate a device agent with a device controller.
             */
            DeviceAgent agent = new DeviceAgent(controller, controller.getDeviceMaster());

            /**
             * Set device agent to device controller.
             */
            controller.setAgent(agent);

            /**
             * Start the agent connection.
             *
             */
            agent.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
