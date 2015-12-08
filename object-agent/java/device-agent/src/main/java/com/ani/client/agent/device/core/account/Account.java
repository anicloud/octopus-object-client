package com.ani.client.agent.device.core.account;


import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
    public void serializeByte(DataOutputStream dos) throws IOException {
        dos.writeLong(accountId);
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws IOException {
        accountId = dis.readLong();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
