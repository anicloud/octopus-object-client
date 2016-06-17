package com.ani.client.agent.device.demo.persistence.agent.service;

import com.ani.client.agent.device.demo.persistence.agent.dao.DeviceMasterDao;
import com.google.gson.Gson;

import java.io.*;

/**
 * Created by huangbin on 11/10/15.
 */
public class DevicePersistenceServiceImpl implements DevicePersistenceService {
    private static DevicePersistenceServiceImpl instance = new DevicePersistenceServiceImpl();

//    private static final String DATA_FILE = "/home/huangbin/src/octopus-object-client/object-agent/java/device-demo/src/main/resources/device/device.json";
    private static final String DATA_FILE = "./device-demo/src/main/resources/device/device.json";

    public static DevicePersistenceServiceImpl getInstance() {
        return instance;
    }

    private DevicePersistenceServiceImpl() {

    }


    @Override
    public DeviceMasterDao getDeviceMasterDao() throws Exception {
//        InputStream inputStream = this.getClass().getResourceAsStream("/device/device.json");

        InputStream inputStream = new FileInputStream(new File(DATA_FILE));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String json = "";
        for (String tmpString = bufferedReader.readLine(); tmpString != null; tmpString = bufferedReader.readLine()) {
            json += tmpString;
        }

        Gson gson = new Gson();
        DeviceMasterDao deviceMasterDao = gson.fromJson(json, DeviceMasterDao.class);
        return deviceMasterDao;
    }


    @Override
    public void saveDeviceMasterDao(DeviceMasterDao deviceMasterDao) throws Exception {
        // File file = new File(this.getClass().getResource("/device/device.json").getFile());
        OutputStream outputStream = new FileOutputStream(new File(DATA_FILE));

        Gson gson = new Gson();
        String json = gson.toJson(deviceMasterDao);
        outputStream.write(json.getBytes("utf-8"));
        outputStream.flush();
    }
}
