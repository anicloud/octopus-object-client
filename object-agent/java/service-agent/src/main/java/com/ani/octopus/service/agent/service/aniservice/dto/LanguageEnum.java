package com.ani.octopus.service.agent.service.aniservice.dto;

/**
 * Created by zhaoyu on 15-11-3.
 */
public enum LanguageEnum {
    ZH_CN("ZH_CN", 0),
    ZH_HK("ZH_HK", 1),
    ZH_TW("ZH_TW", 2),

    EN_US("EN_US", 3),
    EN_GB("EN_GB", 4),
    EN_AU("EN_AU", 5),
    EN_CA("EN_CA", 6),
    EN_HK("EN_HK", 7),

    JA_JP("JA_JP", 8);

    private String name;
    private int index;

    private LanguageEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }
}
