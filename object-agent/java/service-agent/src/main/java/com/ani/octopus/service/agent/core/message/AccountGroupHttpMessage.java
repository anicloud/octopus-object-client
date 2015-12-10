package com.ani.octopus.service.agent.core.message;

import com.ani.octopus.account.application.dto.account.AccountGroupDto;

/**
 * Created by zhaoyu on 15-12-10.
 */
public class AccountGroupHttpMessage extends HttpMessage<AccountGroupDto> {
    public AccountGroupHttpMessage() {
    }

    public AccountGroupHttpMessage(AccountGroupDto returnObj) {
        super(returnObj);
    }

    public AccountGroupHttpMessage(ResultCode resultCode, String msg, AccountGroupDto returnObj) {
        super(resultCode, msg, returnObj);
    }
}
