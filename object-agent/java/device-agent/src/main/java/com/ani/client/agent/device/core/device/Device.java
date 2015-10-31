package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class Device implements ByteSerializable {

    protected DeviceInfo info;
    protected List<Function> functions;

    protected Long deviceId;
    protected Account owner;
    protected List<Account> accounts;

    public Device() {
        this.info = null;
        this.functions = null;
        this.deviceId = -1l;
        this.owner = null;
        this.accounts = null;
    }

    public Device(DeviceInfo info, List<Function> functions) {
        this.info = info;
        this.functions = functions;
        this.deviceId = -1l;
        this.owner = new Account();
        this.accounts = null;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        info.serializeByte(dos);
        if (functions == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(functions.size());
            for (Function function : functions) {
                function.serializeByte(dos);
            }
        }
        dos.writeLong(deviceId);
        owner.serializeByte(dos);
        if (accounts == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(accounts.size());
            for (Account account : accounts) {
                account.serializeByte(dos);
            }
        }
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        info = new DeviceInfo();
        info.unserializeByte(dis);
        int size = dis.readInt();
        if (size > 0) {
            functions = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Function function = new Function();
                function.unserializeByte(dis);
                functions.add(function);
            }
        }
        deviceId = dis.readLong();
        owner = new Account();
        owner.unserializeByte(dis);
        size = dis.readInt();
        if (size > 0) {
            accounts = new ArrayList<>();
            for (int i=0; i<size; i++) {
                Account account = new Account();
                account.unserializeByte(dis);
                accounts.add(account);
            }
        }
    }

    public DeviceInfo getInfo() {
        return info;
    }

    public void setInfo(DeviceInfo info) {
        this.info = info;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
