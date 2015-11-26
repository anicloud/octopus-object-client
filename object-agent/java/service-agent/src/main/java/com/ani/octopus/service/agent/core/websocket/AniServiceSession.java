package com.ani.octopus.service.agent.core.websocket;

import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaoyu on 15-11-24.
 */
public class AniServiceSession implements Serializable {
    private static final long serialVersionUID = -4346434686130985414L;

    private Map<String, AniStub> stubInstanceMap = new ConcurrentHashMap<>();
    private Map<String, AccountObject> accountObjectMap = new ConcurrentHashMap<>();
    private Session session;

    public AniServiceSession() {
    }

    public AniServiceSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public synchronized void put(AniStub aniStub) {
        this.stubInstanceMap.put(aniStub.getKeyId(), aniStub);
    }

    public synchronized AniStub getAniStub(String keyId) {
        return this.stubInstanceMap.get(keyId);
    }

    public synchronized void delete(AniStub aniStub) {
        this.stubInstanceMap.remove(aniStub.getKeyId());
    }

    public synchronized void put(AccountObject accountObject) {
        this.accountObjectMap.put(accountObject.getKeyId(), accountObject);
    }

    public synchronized AccountObject getAccountObject(String keyId) {
        return this.accountObjectMap.get(keyId);
    }

    public synchronized void delete(AccountObject accountObject) {
        this.accountObjectMap.remove(accountObject.getKeyId());
    }

    /**
     * generate unique id
     * @return
     */
    public synchronized String generateAniStubKeyId() {
        String uniId = AniStub.generateKeyId();
        while (stubInstanceMap.containsKey(uniId)) {
            uniId = AniStub.generateKeyId();
        }
        return uniId;
    }
}
