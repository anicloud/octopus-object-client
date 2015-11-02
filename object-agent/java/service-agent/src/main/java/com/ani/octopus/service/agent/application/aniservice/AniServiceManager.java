package com.ani.octopus.service.agent.application.aniservice;

import com.ani.octopus.service.agent.domain.aniservice.AniService;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AniServiceManager {
    /**
     * register your service on anicloud platform
     * @param aniService
     * @return
     */
    AniService register(AniService aniService);
}
