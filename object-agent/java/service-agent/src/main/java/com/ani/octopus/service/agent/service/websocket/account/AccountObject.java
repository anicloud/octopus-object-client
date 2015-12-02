package com.ani.octopus.service.agent.service.websocket.account;

import com.ani.octopus.service.agent.service.websocket.dto.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.*;

/**
 * Created by zhaoyu on 15-11-14.
 */
public class AccountObject implements Serializable {
    private static final long serialVersionUID = 7187234248824052204L;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String keyId = generateKeyId();

    private Long accountId;
    private Map<Long, List<Integer>> stubMap;
    private String stubMapStr;
    private AniObjectState objectState;

    private Message result;

    public AccountObject() {
        this.keyId = generateKeyId();
    }

    public AccountObject(Long accountId, Map<Long, List<Integer>> stubMap, AniObjectState objectState) {
        this.accountId = accountId;
        this.stubMap = stubMap;
        this.objectState = objectState;

        setStubMapStr();
    }

    public AccountObject(Long accountId) {
        this.accountId = accountId;
    }

    public AccountObject(Long accountId, AniObjectState objectState) {
        this.accountId = accountId;
        this.objectState = objectState;
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

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setStubMap(Map<Long, List<Integer>> stubMap) {
        if (this.stubMap == null) {
            this.stubMap = new HashMap<>();
        }
        if (stubMap != null) {
            this.stubMap.putAll(stubMap);
            setStubMapStr();
        }
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

    public AniObjectState getObjectState() {
        return objectState;
    }

    public void setObjectState(AniObjectState objectState) {
        this.objectState = objectState;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Map<Long, List<Integer>> getStubMap() {
        return stubMap;
    }

    public String getStubMapStr() {
        return stubMapStr;
    }

    public String getKeyId() {
        return keyId;
    }

    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AccountObject{" +
                "accountId=" + accountId +
                ", stubMap=" + stubMap +
                ", stubMapStr='" + stubMapStr + '\'' +
                ", objectState=" + objectState +
                '}';
    }

    public static String generateKeyId() {
        return UUID.randomUUID().toString();
    }
}
