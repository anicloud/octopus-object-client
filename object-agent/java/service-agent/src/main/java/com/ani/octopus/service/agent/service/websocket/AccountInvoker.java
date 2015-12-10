package com.ani.octopus.service.agent.service.websocket;

import com.ani.service.bus.core.application.agent.dto.accountobject.AccountObject;
import com.ani.service.bus.core.application.agent.message.SocketMessage;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * Created by zhaoyu on 15-11-14.
 */
public interface AccountInvoker {
    /**
     * account register and login
     * @param accountObject
     */
    SocketMessage registerAndLogin(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account login
     * @param accountObject
     */
    SocketMessage login(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account logout
     * @param accountObject
     */
    SocketMessage logout(AccountObject accountObject) throws IOException, EncodeException;
    SocketMessage remove(AccountObject accountObject) throws IOException, EncodeException;
    SocketMessage updateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException;
}
