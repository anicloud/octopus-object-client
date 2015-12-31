package com.ani.octopus.service.agent.service.websocket;


import com.anicel.commons.service.bus.dto.anistub.AniStub;
import com.anicel.commons.service.bus.dto.anistub.Argument;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.List;

/**
 * The interface provides the basic call methods for the thirty party Application to call the Anicloud Platform.
 * <br><br>
 * We provides <b>Async</b> and <b>Sync</b> methods. If you want to use <b>Async</b> methods, you need to register
 * your own <b>Observer</b> which implements the MessageObserver interface.
 * <br><br>
 * Created by zhaoyu on 15-11-26.
 */
public interface AniInvokable extends AccountInvoker {
    /**
     * Asynchronous call, but you should register your observer to get the return messsage
     * @param stub stub instance
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    void invokeAniObjectAsyn(AniStub stub) throws IOException, EncodeException;

    /**
     * the sync method for aniObject call
     * @param stub stub instance
     * @return result of call
     * @throws IOException IOException
     * @throws EncodeException EncodeException
     */
    List<Argument> invokeAniObjectSync(AniStub stub) throws IOException, EncodeException;
}
