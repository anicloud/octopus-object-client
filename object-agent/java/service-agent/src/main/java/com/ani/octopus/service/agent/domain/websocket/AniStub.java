package com.ani.octopus.service.agent.domain.websocket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoyu on 15-10-29.
 */
public class AniStub implements Serializable {
    private static final long serialVersionUID = 3790604372796535245L;

    private Long objectId;
    private Long stubId;
    private Long groupId;
    private AniStubConnType stubConnType;

    private ResultType resultType;
    private String result;
    private List<Argument> inputValues;
    private List<Argument> outputValues;

    public AniStub() {
    }

    public AniStub(Long objectId, Long stubId, Long groupId, AniStubConnType stubConnType, List<Argument> inputValues, List<Argument> outputValues) {
        this.objectId = objectId;
        this.stubId = stubId;
        this.groupId = groupId;
        this.stubConnType = stubConnType;
        this.inputValues = inputValues;
        this.outputValues = outputValues;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getStubId() {
        return stubId;
    }

    public void setStubId(Long stubId) {
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

    @Override
    public String toString() {
        return "AniStub{" +
                "objectId=" + objectId +
                ", stubId=" + stubId +
                ", groupId=" + groupId +
                ", stubConnType=" + stubConnType +
                ", resultType=" + resultType +
                ", inputValues=" + inputValues +
                ", outputValues=" + outputValues +
                '}';
    }
}
