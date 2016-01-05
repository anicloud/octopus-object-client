package com.ani.client.agent.device.core.device;

import java.util.List;

/**
 * Created by huangbin on 10/26/15.
 */
public class Device {
    /**
     * Device custom id, assigned by vendor.
     * @Required
     */
    protected String physicalId;

    /**
     * Device MAC address, assigned by vendor.
     * @Required
     */
    protected String physicalAddress;

    /**
     * Device name, assigned by vendor.
     */
    protected String name;

    /**
     * Device description, assigned by vendor.
     */
    protected String description;

    /**
     * Device functions list, assigned by vendor.
     */
    protected List<Function> functions;

    public Device() {
    }

    public Device(String physicalId, String physicalAddress, String name, String description, List<Function> functions) {
        this.physicalId = physicalId;
        this.physicalAddress = physicalAddress;
        this.name = name;
        this.description = description;
        this.functions = functions;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

}
