#ifndef _COMMAND_MANAGER_
#define _COMMAND_MANAGER_

#include "agent.h"

struct command_manager;

struct command_manager* command_manager_create(struct agent* agent);
void command_manager_destroy(struct command_manager* manager);

#endif /* _COMMAND_MANAGER_ */
