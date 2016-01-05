package com.ani.client.agent.device.core.device;

import com.ani.bus.device.commons.dto.device.DeviceMasterDto;

import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class DeviceMaster extends Device {
    /**
     * The device id allocated and managed by anicloud.
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

    /**
     * The authentification token allocated after device registry and managed by anicloud.
     */
    private byte[]token;

    public DeviceMaster() {
    }

    public DeviceMaster(String physicalId, String physicalAddress, String name, String description, List<Function> functions, Long deviceId, List<DeviceSlave> slaves, Long owner, List<Long> accountGroups, byte[] token) {
        super(physicalId, physicalAddress, name, description, functions);
        this.deviceId = deviceId;
        this.slaves = slaves;
        this.owner = owner;
        this.accountGroups = accountGroups;
        this.token = token;
    }

    public DeviceMaster(DeviceMasterDto dto) {
        super(dto.physicalId, dto.physicalAddress, dto.name, dto.description, Function.fromDtos(dto.functions));
        this.deviceId = dto.deviceId;
        this.slaves = DeviceSlave.fromDtos(dto.slaves);
        this.owner = dto.owner;
        this.accountGroups = dto.accountGroups;
    }

    public DeviceMasterDto toDto() {
        return new DeviceMasterDto(
                physicalId, physicalAddress, name, description, Function.toDtos(functions),
                deviceId, DeviceSlave.toDtos(slaves), owner, accountGroups);
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

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
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
}
