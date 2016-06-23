/*
 * device-agent.h
 *
 *  Created on: 2016年6月21日
 *      Author: ben
 */

#ifndef DEVICE_AGENT_H_
#define DEVICE_AGENT_H_

#include <string>
using namespace std;


#include "include/device.h"

/**
 * 调用接口返回的状态
 */
typedef enum State {
	//调用接口函数成功
	SUCCESSFULLY,

	//调用接口所给参数错误
	PARAMETER_ERROR,

	//与平台连接错误
	CONNECT_ERROR,

	//注册过程出错
	REGISTER_ERROR,

	//认证过程出错
	AUTH_ERROR,

	//更新过程出错
	UPDATE_ERROR,

	//关闭过程出错
	CLOSE_ERROR,

	//调用过程出错
	INVOKE_ERROR,

	//未知错误
	UNKNOWN_ERROR
} State;

State connect();

State _register(DeviceMaster dm);

State auth();

State close();

State update();

#endif /* DEVICE_AGENT_H_ */
