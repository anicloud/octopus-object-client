package com.ani.client.agent.device.demo.core.agent;

import com.ani.bus.device.commons.dto.device.*;
import com.ani.client.agent.device.core.agent.DeviceAgent;
import com.ani.client.agent.device.core.agent.DeviceController;
import com.ani.client.agent.device.core.agent.InvokeCallback;
import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.DeviceSlave;
import com.ani.client.agent.device.core.device.Function;

import com.ani.client.agent.device.demo.persistence.agent.dao.DeviceMasterDao;
import com.ani.client.agent.device.demo.persistence.agent.dao.DeviceSlaveDao;
import com.ani.client.agent.device.demo.persistence.agent.dao.FunctionDao;
import com.ani.client.agent.device.demo.persistence.agent.service.DevicePersistenceService;
import com.ani.client.agent.device.demo.persistence.agent.service.DevicePersistenceServiceImpl;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huangbin on 10/29/15.
 */

/**
 * A demo implement of DeviceController.
 * The device boots sequence:
 * DeviceAgent::Connect() --> DeviceAgent;:Register() --> DeviceAgent::Auth() --> ready to do normal businesses.
 */
public class DemoDeviceController extends DeviceController {
    private static final Logger LOG = Logger.getLogger(DemoDeviceController.class);

    private DevicePersistenceService devicePersistenceService = DevicePersistenceServiceImpl.getInstance();

    private DeviceAgent agent;
    private DeviceMaster deviceMaster;

    public DemoDeviceController() {
        try {
            this.deviceMaster = unserializeDeviceMaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Implement of DeviceController
     */

    @Override
    public void onError(DeviceAgent.Error error) {
        try {
            switch (error) {
                case ERROR_CONNECT:
                    LOG.info("connect error");
                    Thread.sleep(3000);
                    agent.connect();
                    break;
                case ERROR_REGISTER:
                    LOG.info("register error");
                    break;
                case ERROR_AUTH:
                    LOG.info("auth error");
                    // Serialize the deviceId and the token
                    serializeDeviceMaster(deviceMaster);
                    break;
                case ERROR_UPDATE:
                    LOG.info("update error");
                    serializeDeviceMaster(deviceMaster);
                    break;
                case ERROR_INVOKE:
                    LOG.info("invoke error");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOG.info(e);
        }
    }

    @Override
    public void onConnect() {
        LOG.info("Device connected");
        agent.register();
    }

    @Override
    public void onRegister() {
        LOG.info("Device registered");
        try {
            serializeDeviceMaster(deviceMaster);
        } catch (Exception e) {
            LOG.info(e);
        }
        agent.auth();
    }

    @Override
    public void onAuth() {
        LOG.info("Device authed");
        try {
            serializeDeviceMaster(deviceMaster);
        } catch (Exception e) {
            LOG.info(e);
        }
        agent.update();
    }

    @Override
    public void onUpdate() {
        LOG.info("Device updated");
        try {
            serializeDeviceMaster(deviceMaster);
        } catch (Exception e) {
            LOG.info(e);
        }
    }

    @Override
    public void onClose() {
        LOG.info("Device closed");
        try {
            Thread.sleep(3000);
            agent.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement of Invokable
     */
    @Override
    public void invokeSync(FunctionInstance instance, Long timeout) throws Exception {
        LOG.info("Accept synchronous invocation: ");
        LOG.info("\tid: " + instance.instanceId);
        LOG.info("\tinput args: ");
        List<ArgumentDto> input = instance.inputValues;
        if (input != null) {
            LOG.info("\tsize: " + input.size());
            for (int i = 0; i < input.size(); i++) {
                ArgumentDto argumentDto = input.get(i);
                LOG.info("\targ[" + i + "]: type: " + argumentDto.argumentType.type.name());
            }
        }

        LOG.info("Invocation begin...");

        /**
         * ...
         * Do invoke
         * ...
         */

        LOG.info("Invocation end");

        /**
         * Prepare invocation result
         */

        instance.result = true;
        List<ArgumentDto> output = new ArrayList<>();

        String value1 = "String value 1";
        ArgumentDto ArgumentDto1 = new ArgumentDto(new ArgumentType(ArgumentType.Type.STRING), value1);
        output.add(ArgumentDto1);

        int[] value2 = {1, 2, 3};

        ArgumentDto ArgumentDto2 = new ArgumentDto(
                new ArgumentType(ArgumentType.Type.ARRAY, new ArgumentType(ArgumentType.Type.INTEGER)),
                value2);
        output.add(ArgumentDto2);

        instance.outputValues = output;

    }


    @Override
    public void invokeAsync(FunctionInstance instance, InvokeCallback callback) throws Exception {
    }

    /**
     * Implements of InvokeCallback
     */
    @Override
    public void onInvokeDone(FunctionInstance instance) throws Exception {
        LOG.info("after async invoke: ");
        showInstance(instance);
    }

    private void serializeDeviceMaster(DeviceMaster deviceMaster) throws Exception {
        DeviceMasterDao deviceMasterDao = new DeviceMasterDao(
                deviceMaster.getPhysicalId(),
                deviceMaster.getPhysicalAddress(),
                deviceMaster.getName(),
                deviceMaster.getDescription(),
                null,
                deviceMaster.getAvatarUrl(),
                deviceMaster.getTags(),
                null,
                deviceMaster.getDeviceId(),
                deviceMaster.getOwner(),
                deviceMaster.getAccountGroups(),
                deviceMaster.getVersionId(),
                deviceMaster.getLastModifiedTime(),
                Base64.encode(deviceMaster.getToken())
        );

        if (deviceMaster.getFunctions() != null) {
            deviceMasterDao.functions = new ArrayList<>();
            for (int i = 0; i < deviceMaster.getFunctions().size(); i++) {
                Function function = deviceMaster.getFunctions().get(i);
                FunctionDao functionDao = new FunctionDao(function.getFunctionId(), function.getGroupId());
                deviceMasterDao.functions.add(functionDao);
            }
        }

        if (deviceMaster.getSlaves() != null) {
            deviceMasterDao.slaves = new ArrayList<>();
            for (int i = 0; i < deviceMaster.getSlaves().size(); i++) {
                DeviceSlave slave = deviceMaster.getSlaves().get(i);
                DeviceSlaveDao slaveDao = new DeviceSlaveDao(
                        slave.getPhysicalId(),
                        slave.getPhysicalAddress(),
                        slave.getName(),
                        slave.getDescription(),
                        null,
                        slave.getAvatarUrl(),
                        slave.getTags(),
                        slave.getDeviceId(),
                        slave.getMasterId()
                );
                if (slave.getFunctions() != null) {
                    slaveDao.functions = new ArrayList<>();
                    for (int j = 0; j < slave.getFunctions().size(); j++) {
                        Function function = slave.getFunctions().get(j);
                        FunctionDao functionDao = new FunctionDao(function.getFunctionId(), function.getGroupId());
                        slaveDao.functions.add(functionDao);
                    }
                }
                deviceMasterDao.slaves.add(slaveDao);
            }
        }
        devicePersistenceService.saveDeviceMasterDao(deviceMasterDao);
    }

    private DeviceMaster unserializeDeviceMaster() throws Exception {
        DeviceMasterDao deviceMasterDao = devicePersistenceService.getDeviceMasterDao();

        /**
         * Generate device functions.
         */
        List<Function> functions = null;
        if (deviceMasterDao.functions != null) {
            functions = new ArrayList<>();
            for (FunctionDao functionDao : deviceMasterDao.functions) {
                Function function = new Function(functionDao.functionId, functionDao.groupId);
                functions.add(function);
            }
        }

        /**
         * Generate device slaves
         */
        List<DeviceSlave> slaves = new ArrayList<>();
        if (deviceMasterDao.slaves != null) {
            for (DeviceSlaveDao slaveDao : deviceMasterDao.slaves) {
                List<Function> slaveFunctions = null;
                if (slaveDao.functions != null) {
                    slaveFunctions = new ArrayList<>();
                    for (FunctionDao functionDao : deviceMasterDao.functions) {
                        Function function = new Function(functionDao.functionId, functionDao.groupId);
                        slaveFunctions.add(function);
                    }
                }
                DeviceSlave slave = new DeviceSlave(
                        slaveDao.physicalId,
                        slaveDao.physicalAddress,
                        slaveDao.name,
                        slaveDao.description,
                        slaveFunctions,
                        slaveDao.avatarUrl,
                        slaveDao.tags,
                        slaveDao.deviceId,
                        slaveDao.masterId);
                slaves.add(slave);
            }
        }

        /**
         * Generate device master.
         */
        DeviceMaster deviceMaster = new DeviceMaster(
                deviceMasterDao.physicalId,
                deviceMasterDao.physicalAddress,
                deviceMasterDao.name,
                deviceMasterDao.description,
                functions,
                deviceMasterDao.avatarUrl,
                deviceMasterDao.tags,
                deviceMasterDao.deviceId,
                slaves,
                deviceMasterDao.owner,
                deviceMasterDao.accountGroups,
                deviceMasterDao.versionId,
                deviceMasterDao.lastModifiedTime,
                Base64.decode(deviceMasterDao.token));
        return deviceMaster;
    }


    private void showInstance(FunctionInstance instance) {
        LOG.info("\tid: " + instance.instanceId);
        LOG.info("\tresult: " + instance.result);
        LOG.info("\tinput args: ");
        List<ArgumentDto> input = instance.inputValues;
        if (input != null) {
            LOG.info("\tsize: " + input.size());
            for (int i = 0; i < input.size(); i++) {
                ArgumentDto ArgumentDto = input.get(i);
                LOG.info("\targ[" + i + "]: type: " + ArgumentDto.argumentType.type.name());
            }
        }
        LOG.info("\toutput args: ");
        List<ArgumentDto> output = instance.outputValues;
        if (output != null) {
            LOG.info("\tsize: " + output.size());
            for (int i = 0; i < output.size(); i++) {
                ArgumentDto ArgumentDto = output.get(i);
                LOG.info("\targ[" + i + "]: type: " + ArgumentDto.argumentType.type.name());
            }
        }
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
