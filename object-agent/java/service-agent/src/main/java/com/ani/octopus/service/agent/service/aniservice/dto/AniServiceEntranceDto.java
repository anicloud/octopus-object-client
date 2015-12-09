package com.ani.octopus.service.agent.service.aniservice.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyu on 15-11-4.
 */
public class AniServiceEntranceDto implements Serializable {
    private static final long serialVersionUID = 5000116488779512089L;


    public Long entranceId;
    @NotNull
    @Size(min = 1, max = 150)
    public String entranceName;
    @NotNull
    @Size(min = 1, max = 200)
    public String entranceUrl;
    @Size(min = 1, max = 200)
    public String logoPath;
    @NotNull
    public Set<String> tagSet;
    @Size(min = 1, max = 255)
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

    public void setTagSet(Set<String> tagSet) {
        if (this.tagSet == null) {
            this.tagSet = new HashSet<>();
        }
        this.tagSet.addAll(tagSet);
    }

    public void addTag(String tag) {
        if (this.tagSet == null) {
            this.tagSet = new HashSet<>();
        }
        this.tagSet.add(tag);
    }

    @Override
    public String toString() {
        return "AniServiceEntranceDto{" +
                "entranceName='" + entranceName + '\'' +
                ", entranceUrl='" + entranceUrl + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", tagSet=" + tagSet +
                ", description='" + description + '\'' +
                '}';
    }
}
