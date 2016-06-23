INC_DIR		=	./include
BUILD_DIR	=	./build
DEBUG_DIR	=	./debug
SRC_DIR		=	./src
BIN_DIR		=	./bin
TARGET_DIR	=	./lib

CXX			=	g++

# 编译参数
DEBUGFLAGS	=	-g -Wall -fmessage-length=0
BUILDFLAGS	=	-O2	-shared -fPIC

# 为了发布项目生成的目标文件
OBJS		=	$(BUILD_DIR)/device-agent.o
$(BUILD_DIR)/%.o:	$(SRC_DIR)/%.cpp
	$(CXX) $(BUILDFLAGS) -c $< -o $@

# 为了调试程序生成的目标文件,项目发布时可删去
OBJS_DEBUG	=	$(DEBUG_DIR)/device-agent.o
$(DEBUG_DIR)/%.o:	$(SRC_DIR)/%.cpp
	$(CXX) $(DEBUGFLAGS) -c $< -o $@

LIBS =

debug:	$(OBJS_DEBUG)
	$(CXX) $^ $(LIBS) -o  $(BIN_DIR)/device-agent
	

TARGET_A =	$(TARGET_DIR)/libdevice-agent.a
$(TARGET_A):	$(OBJS)
	ar -r $@ $^ $(LIBS) 

TARGET_SO=	$(TARGET_DIR)/libdevice-agent.so
$(TARGET_SO):	$(OBJS)
	$(CXX) $(BUILDFLAGS) $^ $(LIBS) -o $@


build:	$(TARGET_A) $(TARGET_SO)

clean:
	rm -f  $(OBJS) $(OBJS_DEBUG) $(TARGET_A) $(TARGET_SO) $(BIN_DIR)/*
