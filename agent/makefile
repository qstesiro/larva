agent: agent.o network_manager.o command_manager.o event_manager.o
	gcc -fPIC -shared -o libagent.so agent.o network_manager.o command_manager.o event_manager.o

agent.o: agent.c
	cc -fPIC -c agent.c -I $(JAVA_HOME)/include/ -I $(JAVA_HOME)/include/linux
network_manager.o: network_manager.c
	cc -fPIC -c network_manager.c -I $(JAVA_HOME)/include/ -I $(JAVA_HOME)/include/linux
command_manager.o: command_manager.c
	cc -fPIC -c command_manager.c -I $(JAVA_HOME)/include/ -I $(JAVA_HOME)/include/linux
event_manager.o: event_manager.c
	cc -fPIC -c event_manager.c -I $(JAVA_HOME)/include/ -I $(JAVA_HOME)/include/linux

clean:
	rm -rf *.so *.o
