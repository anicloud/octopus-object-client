package com.ani.client.agent.device.demo.persistence.agent.dao;

import java.util.List;

/**
 * Created by huangbin on 1/5/16.
 */
public class DeviceSlaveDao {

    public String physicalId;

    public String physicalAddress;

    public String name;

    public String description;

    public List<FunctionDao> functions;

    public String avatarUrl;

    public List<String> tags;

    public Integer deviceId;

    public Long masterId;


    public DeviceSlaveDao(String physicalId, String physicalAddress, String name, String description, List<FunctionDao> functions, String avatarUrl, List<String> tags, Integer deviceId, Long masterId) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
        this.avatarUrl = avatarUrl;
        this.tags = tags;
        this.deviceId = deviceId;
        this.masterId = masterId;
    }
}
