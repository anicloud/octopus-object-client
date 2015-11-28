package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.dto.message.Message;

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
    Message registerAndLogin(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account login
     * @param accountObject
     */
    Message login(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account logout
     * @param accountObject
     */
    Message logout(AccountObject accountObject) throws IOException, EncodeException;
    Message remove(AccountObject accountObject) throws IOException, EncodeException;
    Message updateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException;
}
