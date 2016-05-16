package com.ani.client.agent.device.core.device;

import com.ani.bus.device.commons.dto.device.DeviceSlaveDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 11/3/15.
 */
public class DeviceSlave {
    /**
     * Device custom id, assigned by vendor.
     * @Required
     */
    private String physicalId;

    /**
     * Device MAC address, assigned by vendor.
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
     * The device id, allocated and managed by anicloud.
     * @Required
     */
    private Integer deviceId;

    /**
     * The master device id, assigned by vendor.
     * @Required
     */
    private Long masterId;

    private DeviceSlave() {}

    public DeviceSlave(String physicalId, String physicalAddress, String name, String description, List<Function> functions, String avatarUrl, List<String> tags, Integer deviceId, Long masterId) {
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

    public DeviceSlave(DeviceSlaveDto dto) {
        this.physicalId = dto.physicalId;
        this.physicalAddress = dto.physicalAddress;
        this.name = dto.name;
        this.description = dto.description;
        this.functions = Function.fromDtos(dto.functions);
        this.avatarUrl = dto.avatarUrl;
        this.tags = dto.tags;
        this.deviceId = dto.deviceId;
        this.masterId = dto.masterId;
    }

    public DeviceSlaveDto toDto() {
        return new DeviceSlaveDto(physicalId, physicalAddress, name, description, Function.toDtos(functions), avatarUrl, tags, deviceId, masterId);
    }

    public static List<DeviceSlave> fromDtos(List<DeviceSlaveDto> dtos) {
        if (dtos == null) {
            return null;
        }
        List<DeviceSlave> deviceSlaves = new ArrayList<>(dtos.size());
        for (int i=0; i<dtos.size(); i++) {
            deviceSlaves.add(i, new DeviceSlave(dtos.get(i)));
        }
        return deviceSlaves;
    }

    public static List<DeviceSlaveDto> toDtos(List<DeviceSlave> slaves) {
        if (slaves == null) {
            return null;
        }
        List<DeviceSlaveDto> slaveDtos = new ArrayList<>(slaves.size());
        for (int i=0; i<slaves.size(); i++) {
            slaveDtos.add(i, slaves.get(i).toDto());
        }
        return slaveDtos;
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

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }
}
