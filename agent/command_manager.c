#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <jvmti.h>

#include "agent.h"
#include "command_manager.h"

struct command_manager* command_manager_create(struct agent* agent) {
	struct command_manager* manager = (struct command_manager*)malloc(sizeof(struct command_manager));
	memset(manager, 0, sizeof(struct command_manager));
	manager->agent = agent;
	return manager;
}

void command_manager_destroy(struct command_manager* manager) {
	if (NULL != manager) {
		free(manager); manager = NULL;
	}
}
