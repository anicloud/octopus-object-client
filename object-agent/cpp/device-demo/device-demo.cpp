//============================================================================
// Name        : device-demo.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C, Ansi-style
//============================================================================

#include "../device-agent/include/device-agent.h"

#include <cstdio>
#include <cstdlib>
using namespace std;

int main(void) {
	puts("Hello World!!!");
	Status ret = test(true);
	if (ret == SUCCESSFULLY) {
		puts("SUCCESSFULLY");
	}
	return EXIT_SUCCESS;
}
