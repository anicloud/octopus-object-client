package com.ani.octopus.service.agent.application.aniservice;

import com.ani.octopus.service.agent.domain.aniservice.AniService;

/**
 * Created by zhaoyu on 15-10-31.
 */
public interface AniServiceManager {
    AniService register(AniService aniService);
}
