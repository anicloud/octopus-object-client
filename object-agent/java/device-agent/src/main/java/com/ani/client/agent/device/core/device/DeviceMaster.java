package com.ani.client.agent.device.core.device;

import com.ani.bus.device.commons.dto.device.DeviceMasterDto;

import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class DeviceMaster {
    /**
     * Device custom id, assigned by vendor.
     *
     * @Required
     */
    private String physicalId;

    /**
     * Device MAC address, assigned by vendor.
     *
     * @Required
     */
    private String physicalAddress;

    /**
     * Device name, assigned by vendor.
     */
    private String name;

    /**
     * Device description, assigned by vendor.
     */
    private String description;

    /**
     * Device functions list, assigned by vendor.
     */
    private List<Function> functions;

    private String avatarUrl;

    private List<String> tags;

    /**
     * The device id allocated and managed by anicloud.
     *
     * @Required
     */
    private Long deviceId;

    /**
     * The slave devices, managed by vendor.
     */
    private List<DeviceSlave> slaves;

    /**
     * The account id bound with the device, managed by anicloud.
     */
    private Long owner;

    /**
     * The account groups sharing the device, managed by anicloud.
     */
    private List<Long> accountGroups;

    private Long versionId;

    private Long lastModifiedTime;

    /**
     * The authentification token allocated after device registry and managed by anicloud.
     */
    private byte[] token;

    public DeviceMaster() {
    }

    public DeviceMaster(String physicalId, String physicalAddress, String name, String description, List<Function> functions, String avatarUrl, List<String> tags, Long deviceId, List<DeviceSlave> slaves, Long owner, List<Long> accountGroups, Long versionId, Long lastModifiedTime, byte[] token) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
        this.avatarUrl = avatarUrl;
        this.tags = tags;
        this.deviceId = deviceId;
        this.slaves = slaves;
        this.owner = owner;
        this.accountGroups = accountGroups;
        this.versionId = versionId;
        this.lastModifiedTime = lastModifiedTime;
        this.token = token;
    }

    public DeviceMaster(DeviceMasterDto dto) {
        this.physicalId = dto.physicalId;
        this.physicalAddress = dto.physicalAddress;
        this.name = dto.name;
        this.description = dto.description;
        this.functions = Function.fromDtos(dto.functions);
        this.avatarUrl = dto.avatarUrl;
        this.tags = dto.tags;
        this.deviceId = dto.deviceId;
        this.slaves = DeviceSlave.fromDtos(dto.slaves);
        this.owner = dto.owner;
        this.accountGroups = dto.accountGroups;
        this.versionId = dto.versionId;
        this.lastModifiedTime = dto.lastModifiedTime;
    }

    public DeviceMasterDto toDto() {
        return new DeviceMasterDto(physicalId, physicalAddress, name, description, Function.toDtos(functions), avatarUrl, tags,
                deviceId, DeviceSlave.toDtos(slaves), owner, accountGroups, versionId, lastModifiedTime);
    }

    public String getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(String physicalId) {
        this.physicalId = physicalId;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public List<DeviceSlave> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<DeviceSlave> slaves) {
        this.slaves = slaves;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public List<Long> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<Long> accountGroups) {
        this.accountGroups = accountGroups;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }
}
