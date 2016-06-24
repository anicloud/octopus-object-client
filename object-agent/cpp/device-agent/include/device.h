/*
 * ============================================================================
 * Name        : device.h
 * Version     : 0.0.1
 * Author      : ben
 * Created on  : 2016/6/21
 * Copyright   : Anicloud Limited ©2016
 * Description :
 * ============================================================================
 */
/*
 * device.h
 *
 *  Created on: 2016/6/21
 *      Author: ben
 */

#pragma once

#include <cstring>
#include <cstdio>
#include <cstdlib>

#include <string>
using namespace std;

//TODO	字段长度是否提前固定好,确定数值

const int MAX_PHYSICAL_ID_SIZE = 100;
const int MAX_PHYSICAL_ADDRESS_SIZE = 100;
const int MAX_NAME_SIZE = 100;
const int MAX_DESCRIPTION_SIZE = 100;
const int MAX_AVATAR_URL_SIZE = 256;
const int MAX_TAGS_NUM = 100;


//const int

typedef struct Device {
	string physicalId;
	string physicalAddress;
	string name;
	string description;
	string avatarUrl;
	string tags[];
} Device;

typedef struct DeviceMaster {
	string physicalId;
	string physicalAddress;
	string name;
	string description;
	string avatarUrl;
	string tags[];
	long deviceId;
	long owner;
	long versionId;
	long lastModifiedTime;
	char token[];
	long slaveId[];
} DeviceMaster;

typedef struct DeviceSlave {
	string physicalId;
	string physicalAddress;
	string name;
	string description;
	string avatarUrl;
	string tags[];
	long deviceId;
	long owner;
	long versionId;
	long lastModifiedTime;
	char token[];
	long masterId;
} DeviceSlave;

typedef struct Function {
	int functionId;
	long groupId;
} Funcion;
