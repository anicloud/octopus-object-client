package com.ani.octopus.service.agent.service.aniservice.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyu on 15-11-4.
 */
public class AniServiceEntranceDto implements Serializable {
    private static final long serialVersionUID = 5000116488779512089L;

    public Long entranceId;
    public String entranceName;
    public String entranceUrl;
    public String logoPath;
    public Set<String> tagSet;
    public String description;

    public AniServiceEntranceDto() {
    }

    public AniServiceEntranceDto(Long entranceId, String entranceName,
                                 String entranceUrl, String logoPath,
                                 Set<String> tagSet, String description) {
        this.entranceId = entranceId;
        this.entranceName = entranceName;
        this.entranceUrl = entranceUrl;
        this.logoPath = logoPath;
        this.tagSet = tagSet;
        this.description = description;
    }
}
