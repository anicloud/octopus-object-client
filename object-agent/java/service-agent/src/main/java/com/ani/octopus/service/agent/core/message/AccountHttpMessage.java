package com.ani.octopus.service.agent.core.message;

import com.ani.octopus.account.application.dto.account.AccountDto;

/**
 * Created by zhaoyu on 15-12-9.
 */
public class AccountHttpMessage extends HttpMessage<AccountDto> {
    public AccountHttpMessage() {
    }

    public AccountHttpMessage(AccountDto returnObj) {
        super(returnObj);
    }

    public AccountHttpMessage(ResultCode resultCode, String msg, AccountDto returnObj) {
        super(resultCode, msg, returnObj);
    }
}
