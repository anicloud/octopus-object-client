package com.ani.octopus.service.agent.service.aniservice.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaoyu on 15-11-4.
 */
public class AniServiceDto implements Serializable {
    private static final long serialVersionUID = 8456133641725431284L;

    public String aniServiceId;
    public Long objectId;
    public String serviceName;
    public String version;

    public String clientSecret;
    public Set<String> resourceIds;
    public Set<String> scope;
    public Set<String> authorizedGrantTypes;
    public Collection<String> authorities;

    public String webServerRedirectUri;
    public Integer accessTokenValidity;
    public Integer refreshTokenValidity;
    public String autoApprove;
    public Date registerDate;
    public boolean archived;
    public boolean trusted; //to set weather the client is trusted, the field is just for grant_type = authorization_code, if false, go

    public Long accountId;
    public List<AniServiceEntranceDto> entranceList;
    public AniServiceInfoDto aniServiceInfo;

    public AniServiceDto() {
    }

    public AniServiceDto(String aniServiceId, String serviceName, String version,
                         String webServerRedirectUri, Long accountId,
                         List<AniServiceEntranceDto> entranceList,
                         AniServiceInfoDto serviceInfo) {
        this.aniServiceId = aniServiceId;
        this.serviceName = serviceName;
        this.version = version;
        this.webServerRedirectUri = webServerRedirectUri;
        this.accountId = accountId;
        this.entranceList = entranceList;
        this.aniServiceInfo = serviceInfo;
    }

    public AniServiceDto(String aniServiceId, Long objectId, String serviceName,
                         String version, String clientSecret, Set<String> resourceIds,
                         Set<String> scope, Set<String> authorizedGrantTypes,
                         Collection<String> authorities, String webServerRedirectUri,
                         Integer accessTokenValidity, Integer refreshTokenValidity,
                         String autoApprove, Date registerDate, boolean archived,
                         boolean trusted, Long accountId,
                         List<AniServiceEntranceDto> entranceList,
                         AniServiceInfoDto aniServiceInfo) {
        this.aniServiceId = aniServiceId;
        this.objectId = objectId;
        this.serviceName = serviceName;
        this.version = version;
        this.clientSecret = clientSecret;
        this.resourceIds = resourceIds;
        this.scope = scope;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.authorities = authorities;
        this.webServerRedirectUri = webServerRedirectUri;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.autoApprove = autoApprove;
        this.registerDate = registerDate;
        this.archived = archived;
        this.trusted = trusted;
        this.accountId = accountId;
        this.entranceList = entranceList;
        this.aniServiceInfo = aniServiceInfo;
    }

    @Override
    public String toString() {
        return "AniServiceDto{" +
                "aniServiceId='" + aniServiceId + '\'' +
                ", objectId=" + objectId +
                ", serviceName='" + serviceName + '\'' +
                ", version='" + version + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", resourceIds=" + resourceIds +
                ", scope=" + scope +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", authorities=" + authorities +
                ", webServerRedirectUri='" + webServerRedirectUri + '\'' +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", autoApprove='" + autoApprove + '\'' +
                ", registerDate=" + registerDate +
                ", archived=" + archived +
                ", trusted=" + trusted +
                ", accountId=" + accountId +
                ", entranceList=" + entranceList +
                ", aniServiceInfo=" + aniServiceInfo +
                '}';
    }
}
