package com.ani.client.agent.device.core.agent;

import com.ani.client.agent.device.core.device.DeviceInfo;
import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.FunctionInstance;
import org.apache.log4j.Logger;

/**
 * Created by huangbin on 10/29/15.
 */
public class XinwoDeviceController extends DeviceController {
    public static final Logger LOG = Logger.getLogger(XinwoDeviceController.class);

    public DeviceMaster getDeviceMaster() {
        DeviceInfo info = new DeviceInfo("xinwo", "id-1234", "adress-abcd", "xinwo device");
        DeviceMaster master = new DeviceMaster(info, null, null);
        return master;
    }

    @Override
    public void onError() {

    }

    @Override
    public void onReady() {
        LOG.info("agent ready");
    }

    @Override
    public void onClose() {
        LOG.info("agent close");
    }

    @Override
    public void onUpdate(DeviceMaster master) {
        LOG.info("agent update");
    }

    @Override
    public void invokeSync(FunctionInstance instance, Long timeout) throws Exception {

    }

    @Override
    public void invokeAsync(FunctionInstance instance) throws Exception {

    }

    @Override
    public void onInvokeDone(FunctionInstance instance) throws Exception {

    }
}
