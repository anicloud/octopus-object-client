package com.ani.octopus.service.agent.service.websocket;

import com.ani.bus.service.commons.dto.accountobject.AccountObject;
import com.ani.bus.service.commons.message.SocketMessage;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * The basic interface for the third party to report the <b>Account's State</b> to the Anicloud Platform.
 * Then, the Anicloud Platform can notify the appointed account and call the stub on the account object.
 * <br><br>
 *
 * Created by zhaoyu on 15-11-14.
 */
public interface AccountInvoker {
    /**
     * account object register and login
     * @param accountObject account object which is used to keep state of Account on Anicloud Platform.
     * @return SocketMessage
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    SocketMessage registerAndLogin(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * account object login
     * @param accountObject  account object which is used to keep state of Account on Anicloud Platform.
     * @return SocketMessage
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    SocketMessage login(AccountObject accountObject) throws IOException, EncodeException;

    /**
     *  account object logout
     * @param accountObject  account object which is used to keep state of Account on Anicloud Platform.
     * @return SocketMessage
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    SocketMessage logout(AccountObject accountObject) throws IOException, EncodeException;
    /**
     *  account object remove
     * @param accountObject  account object which is used to keep state of Account on Anicloud Platform.
     * @return SocketMessage
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    SocketMessage remove(AccountObject accountObject) throws IOException, EncodeException;

    /**
     * update account object related stub list
     * @param accountObject account object which is used to keep state of Account on Anicloud Platform.
     * @return result message
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    SocketMessage updateAccountObjectStubList(AccountObject accountObject) throws IOException, EncodeException;
}
