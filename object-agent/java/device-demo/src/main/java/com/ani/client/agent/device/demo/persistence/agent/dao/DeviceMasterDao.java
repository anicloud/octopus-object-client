package com.ani.client.agent.device.demo.persistence.agent.dao;


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

    public String avatarUrl;

    public List<String> tags;

    public List<DeviceSlaveDao> slaves;

    public Long deviceId;

    public Long owner;

    public List<Long> accountGroups;

    public Long versionId;

    public Long lastModifiedTime;

    public String token;

    public DeviceMasterDao(String physicalId, String physicalAddress, String name, String description, List<FunctionDao> functions, String avatarUrl, List<String> tags, List<DeviceSlaveDao> slaves, Long deviceId, Long owner, List<Long> accountGroups, Long versionId, Long lastModifiedTime, String token) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
        this.avatarUrl = avatarUrl;
        this.tags = tags;
        this.slaves = slaves;
        this.deviceId = deviceId;
        this.owner = owner;
        this.accountGroups = accountGroups;
        this.versionId = versionId;
        this.lastModifiedTime = lastModifiedTime;
        this.token = token;
    }
}
