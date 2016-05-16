package com.ani.client.agent.device.demo.persistence.agent.dao;

/**
 * Created by huangbin on 11/10/15.
 */
public class FunctionDao {
    public Integer functionId;
    public Long groupId;

    public FunctionDao(Integer functionId, Long groupId) {
        this.functionId = functionId;
        this.groupId = groupId;
    }
}