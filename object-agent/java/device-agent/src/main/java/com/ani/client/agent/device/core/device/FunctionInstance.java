package com.ani.client.agent.device.core.device;

import com.ani.client.agent.device.core.account.Account;
import com.ani.client.agent.device.core.message.ByteSerializable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 10/22/15.
 */
public class FunctionInstance implements ByteSerializable {
    /*
    *  Fields to be serialized.
    * */
    private Long instanceId;
    private Long createTime;
    private Long startTime;

    private Long deviceId;
    private Function function;
    private List<Account> accounts;
    private List<Argument> inputValues;
    private List<Argument> outputValues;

    /*
    *  Fields not to be serialized.
    * */
    private ResultType result;
    private InvokeHandler invokeHandler;

    public FunctionInstance() {
    }

    public FunctionInstance(Long instanceId, Long createTime, Long deviceId, Function function, List<Account> accounts, List<Argument> inputValues, List<Argument> outputValues) {
        this.instanceId = instanceId;
        this.deviceId = deviceId;
        this.createTime = createTime;
        this.function = function;
        this.accounts = accounts;
        this.inputValues = inputValues;
        this.outputValues = outputValues;
        this.result = ResultType.SUCCESS;
    }

    @Override
    public void serializeByte(DataOutputStream dos) throws Exception {
        dos.writeLong(instanceId);
        dos.writeLong(createTime);
        dos.writeLong(startTime);
        dos.writeLong(deviceId);
        function.serializeByte(dos);
        if (accounts == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(accounts.size());
            for (Account account : accounts) {
                account.serializeByte(dos);
            }
        }
        if (inputValues == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(inputValues.size());
            for (Argument arg : inputValues) {
                arg.serializeByte(dos);
            }
        }
        if (outputValues == null) {
            dos.writeInt(0);
        } else {
            dos.writeInt(outputValues.size());
            for (Argument arg : outputValues) {
                arg.serializeByte(dos);
            }
        }
    }

    @Override
    public void unserializeByte(DataInputStream dis) throws Exception {
        instanceId = dis.readLong();
        createTime = dis.readLong();
        startTime = dis.readLong();
        deviceId = dis.readLong();
        function = new Function();
        function.unserializeByte(dis);
        int size = dis.readInt();
        if (size > 0) {
            accounts = new ArrayList<>();
            for (int i=0; i<size; i++) {
                Account account = new Account();
                account.unserializeByte(dis);
                accounts.add(account);
            }
        }
        size = dis.readInt();
        if (size > 0) {
            inputValues = new ArrayList<>();
            for (int i=0; i<size; i++) {
                Argument argument = new Argument();
                argument.unserializeByte(dis);
                inputValues.add(argument);
            }
        }
        size = dis.readInt();
        if (size > 0) {
            outputValues = new ArrayList<>();
            for (int i=0; i<size; i++) {
                Argument argument = new Argument();
                argument.unserializeByte(dis);
                outputValues.add(argument);
            }
        }
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    public List<Argument> getInputValues() {
        return inputValues;
    }

    public void setInputValues(List<Argument> inputValues) {
        this.inputValues = inputValues;
    }

    public List<Argument> getOutputValues() {
        return outputValues;
    }

    public void setOutputValues(List<Argument> outputValues) {
        this.outputValues = outputValues;
    }

    public InvokeHandler getInvokeHandler() {
        return invokeHandler;
    }

    public void setInvokeHandler(InvokeHandler invokeHandler) {
        this.invokeHandler = invokeHandler;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
