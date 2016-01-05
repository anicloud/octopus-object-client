package com.ani.client.agent.device.core.device;


import com.ani.bus.device.commons.dto.device.FunctionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/22/15.
 */
public class Function {
    /**
     * Function id, assigned by vendor.
     * @Required
     */
    private Integer functionId;

    /**
     * Function group id, assigned by vendor.
     * @Required
     */
    private Long groupId;

    public Function() {
    }

    public Function(Integer functionId, Long groupId) {
        this.functionId = functionId;
        this.groupId = groupId;
    }

    public Function(FunctionDto functionDto) {
        this.functionId = functionDto.functionId;
        this.groupId = functionDto.groupId;
    }

    public FunctionDto toDto() {
        return new FunctionDto(functionId, groupId);
    }

    public static List<Function> fromDtos(List<FunctionDto> functionDtos) {
        List<Function> functions = null;
        if (functionDtos != null) {
            functions = new ArrayList<>(functionDtos.size());
            for (int i=0; i<functionDtos.size(); i++) {
                functions.add(new Function(functionDtos.get(i)));
            }
        }
        return functions;
    }

    public static List<FunctionDto> toDtos(List<Function> functions) {
        List<FunctionDto> functionDtos = null;
        if (functions != null) {
            functionDtos = new ArrayList<>(functions.size());
            for (int i=0; i<functions.size(); i++) {
                functionDtos.add(functions.get(i).toDto());
            }
        }
        return functionDtos;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
