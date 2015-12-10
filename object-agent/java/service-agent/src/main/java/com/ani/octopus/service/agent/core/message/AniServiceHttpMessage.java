package com.ani.octopus.service.agent.core.message;

import com.ani.service.bus.core.application.dto.AniServiceDto;

/**
 * Created by zhaoyu on 15-12-9.
 */
public class AniServiceHttpMessage extends HttpMessage<AniServiceDto> {
    public AniServiceHttpMessage() {
    }

    public AniServiceHttpMessage(AniServiceDto returnObj) {
        super(returnObj);
    }

    public AniServiceHttpMessage(ResultCode resultCode, String msg,
                                 AniServiceDto returnObj) {
        super(resultCode, msg, returnObj);
    }
}
