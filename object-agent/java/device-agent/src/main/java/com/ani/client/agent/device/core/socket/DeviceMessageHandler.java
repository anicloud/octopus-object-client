package com.ani.client.agent.device.core.socket;


import com.ani.bus.device.commons.dto.message.DeviceMessage;

import java.io.IOException;

/**
 * Created by huangbin on 10/22/15.
 */
public interface DeviceMessageHandler {
    void onMessage(DeviceMessage message) throws IOException;
    void onConnect() throws IOException;
    void onClose() throws IOException;
    void onTimeout() throws IOException;
}
