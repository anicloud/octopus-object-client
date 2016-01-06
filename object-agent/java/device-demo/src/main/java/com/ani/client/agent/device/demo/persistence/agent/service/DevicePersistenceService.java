package com.ani.client.agent.device.demo.persistence.agent.service;


import com.ani.client.agent.device.demo.persistence.agent.domain.DeviceMasterDao;

/**
 * Created by huangbin on 11/10/15.
 */
public interface DevicePersistenceService {
    DeviceMasterDao getDeviceMasterDao() throws Exception;
    void saveDeviceMasterDao(DeviceMasterDao deviceMasterDao) throws Exception;
}
