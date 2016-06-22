/*
 * device.h
 *
 *  Created on: 2016年6月21日
 *      Author: ben
 */

#ifndef ANIDEVICE_H_
#define ANIDEVICE_H_

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
};

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
};

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
};

typedef struct Function {
	int functionId;
	long groupId;
};

#endif /* ANIDEVICE_H_ */
