#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include "agent.h"
#include "network_manager.h"

struct network_manager* network_manager_create(struct agent* agent) {
	struct network_manager* manager = (struct network_manager*)malloc(sizeof(struct network_manager));
	memset(manager, 0, sizeof(struct network_manager));
	manager->agent = agent;
	return manager;
}

void network_manager_destroy(struct network_manager* manager) {
	if (NULL != manager) {
		free(manager); manager = NULL;
	}
}

int network_manager_startup(struct network_manager* manager) {
	if (NULL != manager) {
		
	}
}

int network_manager_stop(struct network_manager* manager) {
	if (NULL != manager) {
		
	}
}

int network_manager_recv_command(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

int network_manager_send_echo(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

int network_manager_recv(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

int send_event_data(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

int network_manager_send_query_data(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}
