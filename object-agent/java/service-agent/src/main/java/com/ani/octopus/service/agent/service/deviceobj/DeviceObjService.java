package com.ani.octopus.service.agent.service.deviceobj;

import com.ani.bus.service.commons.dto.anidevice.DeviceMasterObjInfoDto;
import com.ani.octopus.service.agent.core.http.AccessTokenService;

import java.util.List;

/**
 * @autor zhaoyu
 * @date 16-3-2
 * @since JDK 1.7
 */
public interface DeviceObjService extends AccessTokenService {
    /**
     * get device obj info, includes device basic info and device obj related Stub list
     * @param accountId account unique id
     * @param withSlave if true, return slave devices; else do not
     * @return list of device obj
     */
    List<DeviceMasterObjInfoDto> getDeviceObjInfo(Long accountId, boolean withSlave) throws Exception;

    /**
     * get device obj info
     * @param accountId account unique id
     * @param mainObjId main device obj id
     * @return device obj
     */
    DeviceMasterObjInfoDto getDeviceObjInfo(Long accountId, Long mainObjId, boolean withSlave) throws Exception;
}
