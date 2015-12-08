package com.ani.octopus.service.agent.service.aniservice.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(max = 150)
    public String entranceName;
    @NotNull
    @Size(max = 200)
    public String entranceUrl;
    @Size(max = 200)
    public String logoPath;
    public Set<String> tagSet;
    @Size(max = 255)
    public String description;

    public AniServiceEntranceDto() {
    }

    public AniServiceEntranceDto(String entranceName, String entranceUrl, String logoPath,
                                 Set<String> tagSet, String description) {
        this.entranceName = entranceName;
        this.entranceUrl = entranceUrl;
        this.logoPath = logoPath;
        this.tagSet = tagSet;
        this.description = description;
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
