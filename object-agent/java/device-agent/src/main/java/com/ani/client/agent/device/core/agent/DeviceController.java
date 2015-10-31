package com.ani.client.agent.device.core.agent;


import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.device.Device;
import com.ani.client.agent.device.core.device.DeviceMaster;
import com.ani.client.agent.device.core.device.Invokable;
import com.ani.client.agent.device.core.device.InvokeHandler;

import java.util.List;

/**
 * Created by huangbin on 10/25/15.
 */
public abstract class DeviceController implements Invokable, InvokeHandler {
    public abstract DeviceMaster getDeviceMaster();
    public abstract void onError();
    public abstract void onReady();
    public abstract void onClose();
    public abstract void onUpdate(DeviceMaster master);
}
