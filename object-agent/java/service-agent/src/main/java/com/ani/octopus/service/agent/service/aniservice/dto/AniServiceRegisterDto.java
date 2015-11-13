package com.ani.octopus.service.agent.service.aniservice.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoyu on 15-11-12.
 */
public class AniServiceRegisterDto implements Serializable {
    private static final long serialVersionUID = -1271450263429504186L;

    public String aniServiceId;
    //public Long objectId;
    public String serviceName;
    public String version;

    //public String clientSecret;
    //public Set<String> resourceIds;
    //public Set<String> scope;
    //public Set<String> authorizedGrantTypes;
    //public Collection<String> authorities;

    public String webServerRedirectUri;
    //public Integer accessTokenValidity;
    //public Integer refreshTokenValidity;
    //public String autoApprove;
    //public Date registerDate;
    //public boolean archived;
    //public boolean trusted; //to set weather the client is trusted, the field is just for grant_type = authorization_code, if false, go

    public Long accountId;
    public List<AniServiceEntranceDto> entranceList;
    public AniServiceInfoDto aniServiceInfo;

    public AniServiceRegisterDto() {
    }

    public AniServiceRegisterDto(String serviceName, String version,
                                 String webServerRedirectUri, Long accountId,
                                 List<AniServiceEntranceDto> entranceList,
                                 AniServiceInfoDto aniServiceInfo) {
        this.serviceName = serviceName;
        this.version = version;
        this.webServerRedirectUri = webServerRedirectUri;
        this.accountId = accountId;
        this.entranceList = entranceList;
        this.aniServiceInfo = aniServiceInfo;
    }

    public static AniServiceDto toAniServiceDto(AniServiceRegisterDto registerDto) {
        if (registerDto == null) {
            return null;
        }
        return new AniServiceDto(
                registerDto.aniServiceId,
                registerDto.serviceName,
                registerDto.version,
                registerDto.webServerRedirectUri,
                registerDto.accountId,
                registerDto.entranceList,
                registerDto.aniServiceInfo
        );
    }
}
