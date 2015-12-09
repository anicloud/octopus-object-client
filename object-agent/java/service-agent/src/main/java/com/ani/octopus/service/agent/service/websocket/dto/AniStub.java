package com.ani.octopus.service.agent.service.websocket.dto;

import com.ani.octopus.service.agent.core.message.SocketMessage;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class AniStub implements Serializable {
    private static final long serialVersionUID = 3790604372796535245L;

    @NotNull
    private String keyId = generateKeyId(); // for every times call, unique key

    @NotNull
    private Long targetObjectId;
    @NotNull
    private Long accountId;
    @NotNull
    private Long groupId;
    @NotNull
    private Integer stubId;

    private List<Argument> inputValues;
    private List<Argument> outputValues;

    private SocketMessage resultMsg;

    public AniStub() {
    }

    public AniStub(Long targetObjectId, Long accountId, Long groupId,
                   Integer stubId, List<Argument> inputValues) {
        this.targetObjectId = targetObjectId;
        this.accountId = accountId;
        this.groupId = groupId;
        this.stubId = stubId;
        this.inputValues = inputValues;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getStubId() {
        return stubId;
    }

    public void setStubId(Integer stubId) {
        this.stubId = stubId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Argument> getInputValues() {
        return inputValues;
    }

    public void setInputValues(List<Argument> inputValues) {
        this.inputValues = inputValues;
    }

    public List<Argument> getOutputValues() {
        return outputValues;
    }

    public void setOutputValues(List<Argument> outputValues) {
        this.outputValues = outputValues;
    }

    public String getKeyId() {
        return keyId;
    }

    public Long getTargetObjectId() {
        return targetObjectId;
    }

    public void setTargetObjectId(Long targetObjectId) {
        this.targetObjectId = targetObjectId;
    }

    public SocketMessage getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(SocketMessage resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "AniStub{" +
                "keyId='" + keyId + '\'' +
                ", accountId=" + accountId +
                ", stubId=" + stubId +
                ", groupId=" + groupId +
                ", inputValues=" + inputValues +
                ", outputValues=" + outputValues +
                '}';
    }

    public static String generateKeyId() {
        return UUID.randomUUID().toString();
    }
}
