package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.message.ByteSerializable;
import com.ani.client.agent.device.core.message.MessageUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class Device implements ByteSerializable {

    /**
     * Device custom name, a human readable name.
     * Nullable
     */
    protected String name;

    /**
     * Device custom id, used to distinguish different devices have the same MAC.
     * NotNull
     */
    protected String physicalId;

    /**
     * Device MAC address.
     * NotNull
     */
    protected String physicalAddress;

    /**
     * Device custom description, describes device details.
     * Nullable
     */
    protected String description;
    
    /**
     * Device functions list provided by device manufactures.
     */
    protected List<Function> functions;

    /**
     * Unique device identified id provided by Anicloud.
     */
    protected Long deviceId;

    /**
     * Owner account provided by Anicloud.
     */
    protected Account owner;

    /**
     * related accounts provided by Anicloud.
     */
    protected List<Account> accounts;

    public Device() {
        this.deviceId = -1l;
        this.owner = new Account();
        this.accounts = new ArrayList<>();
    }

    public Device(String name, String physicalId, String physicalAddress, String description, List<Function> functions) {
        this.name = name;
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.description = description;
        this.functions = functions;

        this.deviceId = -1l;
        this.owner = new Account();
        this.accounts = new ArrayList<>();
    }



    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        MessageUtils.writeString(dos, name);
        MessageUtils.writeString(dos, physicalId);
        MessageUtils.writeString(dos, physicalAddress);
        MessageUtils.writeString(dos, description);
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
        name = MessageUtils.readString(dis);
        physicalId = MessageUtils.readString(dis);
        physicalAddress = MessageUtils.readString(dis);
        description = MessageUtils.readString(dis);
        int size = dis.readInt();
        if (size > 0) {
            functions = new ArrayList<Function>();
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
            accounts = new ArrayList<Account>();
            for (int i=0; i<size; i++) {
                Account account = new Account();
                account.unserializeByte(dis);
                accounts.add(account);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(String physicalId) {
        this.physicalId = physicalId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
