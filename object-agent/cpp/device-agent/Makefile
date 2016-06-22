CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		device-agent.o

LIBS =

TARGET =	device-agent

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)
