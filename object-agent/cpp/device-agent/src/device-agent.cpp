/*
 * device-agent.cpp
 *
 *  Created on: 2016/6/21
 *      Author: ben
 */

#include "../include/device-agent.h"
//#include "socket.h"

#include <cstdio>
#include <cstdlib>
using namespace std;

//Status connect() {
//	/**
//	 * 服务器域名及端口号,一般不会改变
//	 */
//	const char *host = "bj-yatsen.anicel.cn";
//	int port = 1222;
//}

Status test(bool flag) {
	puts("excute the test function!");
	if (flag) {
		return SUCCESSFULLY;
	} else {
		return UNKNOWN_ERROR;
	}
}

//int main(void) {
//	int a = 1;
//	int b = 2;
//	int c = a + b;
//	puts("Hello World!!!");
//	return EXIT_SUCCESS;
//}
