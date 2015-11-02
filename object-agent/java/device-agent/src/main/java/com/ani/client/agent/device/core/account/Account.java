package com.ani.client.agent.device.core.account;


import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by huangbin on 10/29/15.
 */
public class Account implements ByteSerializable {
    private Long accountId;

    public Account() {
        this.accountId = -1L;
    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeLong(accountId);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        accountId = dis.readLong();
    }
}
