package com.ani.client.agent.device.demo.persistence.agent.domain;


import java.util.List;

/**
 * Created by huangbin on 11/10/15.
 */
public class DeviceMasterDao {

    public String physicalId;

    public String physicalAddress;

    public String name;

    public String description;

    public List<FunctionDao> functions;

    public List<DeviceSlaveDao> slaves;

    public Long deviceId;

    public Long owner;

    public List<Long> accountGroups;

    public String token;

    public DeviceMasterDao(String physicalId, String physicalAddress, String name, String description, List<FunctionDao> functions, List<DeviceSlaveDao> slaves, Long deviceId, Long owner, List<Long> accountGroups, String token) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
        this.slaves = slaves;
        this.deviceId = deviceId;
        this.owner = owner;
        this.accountGroups = accountGroups;
        this.token = token;
    }
}
