package com.ani.octopus.service.agent.service.websocket.observer;

import com.ani.octopus.service.agent.service.websocket.dto.message.MessageType;

import java.util.Vector;

/**
 * The class is the Subject of Observer pattern.
 * Created by zhaoyu on 15-10-30.
 */
public class MessageObservable {
    private Vector<MessageObserver> obs;

    public MessageObservable() {
        this.obs = new Vector<>();
    }

    public synchronized void addObserver(MessageObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!obs.contains(observer)) {
            obs.addElement(observer);
        }
    }

    public void setObs(Vector<MessageObserver> obs) {
        this.obs = obs;
    }

    public synchronized void deleteObserver(MessageObserver observer) {
        obs.removeElement(observer);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * notify the observers with the specified messageType.
     * @param object the cite of Subject.
     * @param messageType the enum message type.
     */
    public void notifyObservers(Object object, MessageType messageType) {
        Object[] arrLocal;
        synchronized (this) {
            arrLocal = obs.toArray();
        }
        for (int i = arrLocal.length-1; i>=0; i--) {
            MessageObserver observer = (MessageObserver)arrLocal[i];
            if (messageType == MessageType.CALL_ANI_OBJECT
                    && (observer instanceof AniObjectCallMessageObserver)) {
                observer.update(this, object);
            }
            if (messageType == MessageType.CALL_SYSTEM
                    && (observer instanceof AniFunctionCallMessageObserver)) {
                observer.update(this, object);
            }
        }

    }

    public void notifyObservers(Object object) {
        Object[] arrLocal;
        synchronized (this) {
            arrLocal = obs.toArray();
        }
        for (int i = arrLocal.length-1; i>=0; i--)
            ((MessageObserver)arrLocal[i]).update(this, object);
    }

    public void deleteObservers() {
        obs.removeAllElements();
    }

    public synchronized int countObservers() {
        return obs.size();
    }
}
