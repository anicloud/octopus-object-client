package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.device.Device;
import com.ani.client.agent.device.core.device.DeviceInfo;
import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.FunctionInstance;

import java.util.List;

/**
 * Created by huangbin on 10/29/15.
 */
public class XinwoDeviceController extends DeviceController {

    @Override
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

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onUpdate(DeviceMaster master) {

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
