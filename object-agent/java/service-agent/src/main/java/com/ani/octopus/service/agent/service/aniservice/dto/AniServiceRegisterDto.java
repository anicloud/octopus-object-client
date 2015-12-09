package com.ani.octopus.service.agent.service.aniservice.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyu on 15-11-12.
 */
public class AniServiceRegisterDto implements Serializable {
    private static final long serialVersionUID = -1271450263429504186L;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Size(max = 100)
    public String aniServiceId;
    //public Long objectId;
    @NotNull
    @Size(min = 4, max = 100)
    public String serviceName;
    @NotNull
    @Size(min = 1, max = 20)
    public String version;

    //public String clientSecret;
    //public Set<String> resourceIds;
    //public Set<String> scope;
    //public Set<String> authorizedGrantTypes;
    //public Collection<String> authorities;
    @NotNull
    @Size(max = 200)
    public String webServerRedirectUri;
    //public Integer accessTokenValidity;
    //public Integer refreshTokenValidity;
    //public String autoApprove;
    //public Date registerDate;
    //public boolean archived;
    //public boolean trusted; //to set weather the client is trusted, the field is just for grant_type = authorization_code, if false, go

    @NotNull
    public Long accountId;

    @Valid
    public List<AniServiceEntranceDto> entranceList;
    @Valid
    public AniServiceInfoDto aniServiceInfo;

    /**
     * key is stub group id, value is stub id list
     */
    private Map<Long, List<Integer>> stubMap;
    private String stubMapStr;

    public AniServiceRegisterDto() {
        this.stubMap = new HashMap<>();
    }

    public AniServiceRegisterDto(String aniServiceId, String serviceName, String version,
                                 String webServerRedirectUri, Long accountId,
                                 List<AniServiceEntranceDto> entranceList,
                                 AniServiceInfoDto aniServiceInfo,
                                 Map<Long, List<Integer>> stubMap) {
        this.aniServiceId = aniServiceId;
        this.serviceName = serviceName;
        this.version = version;
        this.webServerRedirectUri = webServerRedirectUri;
        this.accountId = accountId;
        this.entranceList = entranceList;
        this.aniServiceInfo = aniServiceInfo;
        this.stubMap = stubMap;

        setStubMapStr();
    }

    public AniServiceRegisterDto(String serviceName, String version,
                                 String webServerRedirectUri, Long accountId,
                                 List<AniServiceEntranceDto> entranceList,
                                 AniServiceInfoDto aniServiceInfo,
                                 Map<Long, List<Integer>> stubMap) {
        this.serviceName = serviceName;
        this.version = version;
        this.webServerRedirectUri = webServerRedirectUri;
        this.accountId = accountId;
        this.entranceList = entranceList;
        this.aniServiceInfo = aniServiceInfo;
        this.stubMap = stubMap;

        setStubMapStr();
    }

    public String getAniServiceId() {
        return aniServiceId;
    }

    public void setAniServiceId(String aniServiceId) {
        this.aniServiceId = aniServiceId;
    }

    private void setStubMapStr() {
        try {
            if (this.stubMap != null) {
                this.stubMapStr = objectMapper.writeValueAsString(this.stubMap);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setStubMap(Map<Long, List<Integer>> stubMap) {
        if (this.stubMap == null) {
            this.stubMap = new HashMap<>();
        }
        stubMap.putAll(stubMap);
        setStubMapStr();
    }

    public void addStub(Long groupId, Integer stubId) {
        if (this.stubMap == null) {
            this.stubMap = new HashMap<>();
        }
        List<Integer> stubIdList = this.stubMap.get(groupId);
        if (stubIdList == null) {
            stubIdList = new ArrayList<>();
        }
        stubIdList.add(stubId);
        this.stubMap.put(groupId, stubIdList);

        setStubMapStr();
    }

    public void setAniServiceEntranceList(List<AniServiceEntranceDto> entranceList) {
        if (this.entranceList == null) {
            this.entranceList = new ArrayList<>();
        }
        this.entranceList.addAll(entranceList);
    }

    public void addAniServiceEntrance(AniServiceEntranceDto entranceDto) {
        if (this.entranceList == null) {
            this.entranceList = new ArrayList<>();
        }
        this.entranceList.add(entranceDto);
    }

    public String getStubMapStr() {
        return stubMapStr;
    }

    @Override
    public String toString() {
        return "AniServiceRegisterDto{" +
                "aniServiceId='" + aniServiceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", version='" + version + '\'' +
                ", webServerRedirectUri='" + webServerRedirectUri + '\'' +
                ", accountId=" + accountId +
                ", entranceList=" + entranceList +
                ", aniServiceInfo=" + aniServiceInfo +
                ", stubMap=" + stubMap +
                '}';
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
