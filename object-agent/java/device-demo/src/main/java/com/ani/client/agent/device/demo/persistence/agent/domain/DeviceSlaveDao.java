package com.ani.client.agent.device.demo.persistence.agent.domain;

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

    public Integer deviceId;

    public Long masterId;

    public DeviceSlaveDao(String physicalId, String physicalAddress, String name, String description, List<FunctionDao> functions, Integer deviceId, Long masterId) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
        this.deviceId = deviceId;
        this.masterId = masterId;
    }
}
