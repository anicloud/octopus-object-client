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

//
const int MAX_PHYSICAL_ID_LEN = 100;

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
