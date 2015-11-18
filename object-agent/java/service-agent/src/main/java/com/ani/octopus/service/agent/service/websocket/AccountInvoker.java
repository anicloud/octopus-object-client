package com.ani.octopus.service.agent.service.websocket;

import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.account.AniObjectState;

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
    void registerAndLogin(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account login
     * @param accountObject
     */
    void login(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account logout
     * @param accountObject
     */
    void logout(AccountObject accountObject) throws IOException, EncodeException;
    void remove(AccountObject accountObject) throws IOException, EncodeException;
    void UpdateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException;
}
