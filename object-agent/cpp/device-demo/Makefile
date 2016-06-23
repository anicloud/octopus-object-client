CXXFLAGS	=	-O2 -g -Wall -fmessage-length=0

OBJS		=	device-demo.o

LIB_DIR		=	../device-agent/lib
LIBS		=	$(LIB_DIR)/libdevice-agent.a

TARGET =	device-demo

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

testso:	$(OBJS)
	$(CXX) $(OBJS) -L $(LIB_DIR) -ldevice-agent -o $(TARGET)
# 这样生成的可执行文件直接运行会报找不到so库的错,一种方法是将库拷到/usr/lib中；另一种是在终端设置环境变量,再执行程序
# export LD_LIBRARY_PATH=$(LIB_DIR)
# ./$(TARGET)


testa:	$(OBJS)
	$(CXX) $(OBJS) $(LIBS) -o $(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)
