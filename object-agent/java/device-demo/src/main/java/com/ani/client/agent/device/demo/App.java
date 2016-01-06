package com.ani.client.agent.device.demo;

import com.ani.client.agent.device.core.agent.DeviceAgent;
import com.ani.client.agent.device.core.agent.DeviceController;
import com.ani.client.agent.device.demo.core.agent.DemoDeviceController;


/**
 * Client demo
 */
public class App {


    public static void main(String[] args) {
        /**
         * Instantiate a device controller object here.
         */
        DemoDeviceController controller = new DemoDeviceController();
        DeviceAgent agent = new DeviceAgent(controller, controller.getDeviceMaster());
        controller.setAgent(agent);

        /**
         * Instantiate a agent thread.
         */
        AgentThread agentThread = new AgentThread(agent);

        /**
         * Instantiate a controller thread.
         */
        ControllerThread controllerThread = new ControllerThread(controller);

        /**
         * Start threads.
         *
         */
        controllerThread.start();
        agentThread.start();

    }

    private static class AgentThread extends Thread {
        private DeviceAgent agent;

        public AgentThread(DeviceAgent agent) {
            this.agent = agent;
        }

        @Override
        public void run() {
            agent.connect();
        }

    }

    private static class ControllerThread extends Thread {
        private DeviceController controller;

        public ControllerThread(DeviceController controller) {
            this.controller = controller;
        }

        @Override
        public void run() {

        }
    }
}
