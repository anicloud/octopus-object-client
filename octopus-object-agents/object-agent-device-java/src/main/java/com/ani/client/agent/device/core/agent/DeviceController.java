package com.ani.client.agent.device.core.agent;

import com.ani.bus.device.core.domain.device.Device;
import com.ani.bus.device.core.domain.device.Invokable;
import com.ani.bus.device.core.domain.device.InvokeHandler;

/**
 * Created by huangbin on 10/25/15.
 */
public abstract class DeviceController implements Invokable, InvokeHandler {
    public abstract Device getDeviceInfo();
    public abstract void onError();
    public abstract void onReady();
    public abstract void onClose();
}
