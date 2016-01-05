package com.ani.client.agent.device.core.device;

import com.ani.bus.device.commons.dto.device.DeviceSlaveDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 11/3/15.
 */
public class DeviceSlave extends Device {
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

    public DeviceSlave(String physicalId, String physicalAddress, String name, String description, List<Function> functions, Long masterId, Integer deviceId) {
        super(physicalId, physicalAddress, name, description, functions);
        this.masterId = masterId;
        this.deviceId = deviceId;
    }

    public DeviceSlave(DeviceSlaveDto dto) {
        super(dto.physicalId, dto.physicalAddress, dto.name, dto.description, Function.fromDtos(dto.functions));
        this.deviceId = dto.deviceId;
        this.masterId = dto.masterId;
    }

    public DeviceSlaveDto toDto() {
        return new DeviceSlaveDto(physicalId, physicalAddress, name, description, Function.toDtos(functions), deviceId, masterId);
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
