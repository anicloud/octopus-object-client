/*
 * ============================================================================
 * Name        : device-agent.h
 * Version     : 0.0.1
 * Author      : ben
 * Created on  : 2016/6/21
 * Copyright   : anicloud
 * Description :
 * ============================================================================
 */

#pragma once

#include "device.h"

// TODO 考虑C语言调用的问题,用extern "C"等处理

#include <string>
using namespace std;

/**
 * 调用接口返回的状态
 */
typedef enum Status {
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
} Status;

Status test(bool flag);

Status connect();

Status _register(DeviceMaster dm);

Status auth();

Status close();

Status update();
