#ifndef _NETWORK_MANAGER_
#define _NETWORK_MANAGER_

#include "agent.h"

struct network_manager {
	struct agent* agent;
};

struct network_manager* network_manager_create(struct agent* agent);
void network_manager_destroy(struct network_manager* manager);

int network_manager_startup(struct network_manager* manager);
int network_manager_stop(struct network_manager* manager);

int network_manager_recv_command(struct network_manager* manager, char* buffer, int size, int timeout);
int network_manager_send_echo(struct network_manager* manager, char* buffer, int size, int timeout);
int network_manager_send_query_data(struct network_manager* manager, char* buffer, int size, int timeout);
int network_maanger_send_event_data(struct network_manager* manager, char* buffer, int size, int timeout);

#endif /* _NETWORK_MANAGER_ */
