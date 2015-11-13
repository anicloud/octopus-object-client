package com.ani.client.agent.device.core.agent;

import com.ani.client.agent.device.core.device.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * Created by huangbin on 10/29/15.
 */

/**
 * A demo implement of DeviceController.
 */
public class XinwoDeviceController extends DeviceController {
    private static final Logger LOG = Logger.getLogger(XinwoDeviceController.class);

    private DeviceAgent agent;
    private DeviceMaster deviceMaster;
    private Queue<FunctionInstance> instanceQueue;

    public XinwoDeviceController() {
        this.deviceMaster = generateDeviceMaster();
    }

    @Override
    public void onError(AgentError agentError) {
        LOG.info("agent error event");
        switch (agentError) {
            case AGENT_ERROR_CONNECT:
                try {
                    Thread.sleep(3000);
                    agent.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case AGENT_ERROR_UPDATE:
            default:
                break;
        }
    }

    @Override
    public void onReady() {
        LOG.info("agent ready event");
        /**
         * Following function instance invoke a asynchronous function with no input arguments
         * and the destination is all the related accounts.
         */
        FunctionInstance instance = agent.createFunctionInstance(
                deviceMaster.getDeviceId(),
                deviceMaster.getFunctions().get(0),
                deviceMaster.getAccounts(),
                null,
                null
        );
        /**
         * Set the invocation callback handler.
         */
        instance.setInvokeCallback(this);
        try {
            agent.invokeAsync(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose() {
        LOG.info("agent close event");
        try {
            Thread.sleep(3000);
            agent.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(DeviceMaster master) {
        LOG.info("agent update event");
        this.deviceMaster = master;
    }

    @Override
    public void invokeSync(FunctionInstance instance, Long timeout) throws Exception {
        LOG.info("Accept synchronous invocation: ");
        LOG.info("\tid: " + instance.getInstanceId());
        LOG.info("\tinput args: ");
        List<Argument> input = instance.getInputValues();
        if (input != null) {
            LOG.info("\tsize: " + input.size());
            for (int i=0; i<input.size(); i++) {
                Argument argument = input.get(i);
                LOG.info("\targ[" + i + "]: type: " + argument.getType().getTypeClass() + " size: " + argument.getSize());
            }
        }

        LOG.info("Invocation begin...");

        /**
         * ...
         * Do invocation business
         * ...
         */

        LOG.info("Invocation done");

        /**
         * Prepare invocation result
         */

        instance.setResult(ResultType.SUCCESS);
        List<Argument> output = new ArrayList<>();

        List<String> value1 = new ArrayList<>(1);
        value1.add("string value 1");
        Argument argument1 = new Argument(ArgumentType.STRING, value1.get(0).length());
        argument1.setValue(value1);
        output.add(argument1);

        List<Integer> value2 = new ArrayList<>(3);
        value2.add(1);
        value2.add(2);
        value2.add(3);
        Argument argument2 = new Argument(ArgumentType.INTEGER, value1.size());
        argument2.setValue(value2);
        output.add(argument2);

        instance.setOutputValues(output);

    }

    @Override
    public void invokeAsync(FunctionInstance instance) throws Exception {
        instanceQueue.add(instance);
    }

    @Override
    public void onInvokeDone(FunctionInstance instance) throws Exception {
        LOG.info("Asynchronous invocation callback: ");
        LOG.info("\tid: " + instance.getInstanceId());
        LOG.info("\tresult: " + instance.getResult().getName());
        LOG.info("\toutput args: ");
        List<Argument> output = instance.getOutputValues();
        if (output != null) {
            LOG.info("\tsize: " + output.size());
            for (int i=0; i<output.size(); i++) {
                Argument argument = output.get(i);
                LOG.info("\targ[" + i + "]: type: " + argument.getType().getTypeClass() + " size: " + argument.getSize());
            }
        }
    }

    private DeviceMaster generateDeviceMaster() {

        /**
         * Generate device functions.
         */
        List<Function> functions = new ArrayList<Function>();
        Function function1 = new Function(1001, 1l, ConnType.ASYNC);
        Function function2 = new Function(1002, 1l, ConnType.SYNC);
        functions.add(function1);
        functions.add(function2);

        /**
         * Generate device master.
         */
        deviceMaster = new DeviceMaster(
                "checker",
                "manufacture-id",
                "00-01-6C-06-A6-29",
                "xinwo device description",
                functions,
                null);

        return deviceMaster;
    }

    public DeviceAgent getAgent() {
        return agent;
    }

    public void setAgent(DeviceAgent agent) {
        this.agent = agent;
    }

    public DeviceMaster getDeviceMaster() {
        return deviceMaster;
    }

    public void setDeviceMaster(DeviceMaster deviceMaster) {
        this.deviceMaster = deviceMaster;
    }
}
