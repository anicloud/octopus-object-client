package com.ani.octopus.service.agent.service.websocket.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class AniStub implements Serializable {
    private static final long serialVersionUID = 3790604372796535245L;

    private String keyId = generateKeyId(); // for every times call, unique key

    private Long objectId;
    private Integer stubId;
    private Long groupId;
    private AniStubConnType stubConnType;

    private ResultType resultType;
    private String result;
    private List<Argument> inputValues;
    private List<Argument> outputValues;

    public AniStub() {
    }

    public AniStub(Long objectId, Integer stubId, Long groupId, AniStubConnType stubConnType, List<Argument> inputValues) {
        this.objectId = objectId;
        this.stubId = stubId;
        this.groupId = groupId;
        this.stubConnType = stubConnType;
        this.inputValues = inputValues;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
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

    public AniStubConnType getStubConnType() {
        return stubConnType;
    }

    public void setStubConnType(AniStubConnType stubConnType) {
        this.stubConnType = stubConnType;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getKeyId() {
        return keyId;
    }

    @Override
    public String toString() {
        return "AniStub{" +
                "keyId='" + keyId + '\'' +
                ", objectId=" + objectId +
                ", stubId=" + stubId +
                ", groupId=" + groupId +
                ", stubConnType=" + stubConnType +
                ", resultType=" + resultType +
                ", result='" + result + '\'' +
                ", inputValues=" + inputValues +
                ", outputValues=" + outputValues +
                '}';
    }

    public static String generateKeyId() {
        return UUID.randomUUID().toString();
    }
}
