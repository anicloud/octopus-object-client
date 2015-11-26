package com.ani.octopus.service.agent.service.websocket.dto.message;

import com.ani.octopus.service.agent.service.websocket.account.AccountObjectCallType;
import com.ani.octopus.service.agent.service.websocket.account.AniObjectState;
import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.dto.AniStubConnType;
import com.ani.octopus.service.agent.service.websocket.dto.ResultType;

/**
 * Created by zhaoyu on 15-11-16.
 */
public class AniAccountCallMessage extends Message {
    private static final long serialVersionUID = 5686024362447262789L;

    private AccountObject accountObject;
    private AccountObjectCallType accountObjectCallType;

    public AniAccountCallMessage(AccountObject accountObject,
                                 AccountObjectCallType accountObjectCallType) {
        super(MessageType.CALL_ANI_ACCOUNT);
        this.accountObject = accountObject;
        this.accountObjectCallType = accountObjectCallType;
    }

    public AniAccountCallMessage(Long accountId, AniObjectState objectState,
                                 AccountObjectCallType accountObjectCallType) {
        super(MessageType.CALL_ANI_ACCOUNT);
        this.accountObject = new AccountObject(accountId, objectState);
        this.accountObjectCallType = accountObjectCallType;
    }

    public AccountObject getAccountObject() {
        return accountObject;
    }

    public void setAccountObject(AccountObject accountObject) {
        this.accountObject = accountObject;
    }

    public AccountObjectCallType getAccountObjectCallType() {
        return accountObjectCallType;
    }

    public void setAccountObjectCallType(AccountObjectCallType accountObjectCallType) {
        this.accountObjectCallType = accountObjectCallType;
    }

    @Override
    public String toString() {
        return "AniAccountCallMessage{" +
                "accountObject=" + accountObject +
                ", accountObjectCallType=" + accountObjectCallType +
                "} " + super.toString();
    }
}
