agent: agent.o network_manager.o command_manager.o event_manager.o
	gcc -fPIC -shared -o libagent.so agent.o network_manager.o command_manager.o event_manager.o -levent_core -levent_extra -levent

agent.o: agent.c
	cc -fPIC -c agent.c
network_manager.o: network_manager.c
	cc -fPIC -c network_manager.c
command_manager.o: command_manager.c
	cc -fPIC -c command_manager.c
event_manager.o: event_manager.c
	cc -fPIC -c event_manager.c

clean:
	rm -rf *.so *.o
