#ifndef _EVENT_MANAGER_
#define _EVENT_MANAGER_

#include <jvmti.h>

#include "agent.h"

struct event_manager {
	struct agent* agent;
	jvmti_event_handlers handlers;
	int events[JVMTI_MAX_EVENT_TYPE_VAL - JVMTI_MIN_EVENT_TYPE_VAL];
};

struct event_manager* event_manager_create(struct agent* agent);
void event_manager_destroy(struct event_manager* manager);

jvmti_error event_manager_enable_monitor(struct event_manager* manager, jvmti_event event, jthread thread);
jvmti_error event_manager_disable_monitor(struct event_manager* manager, jvmti_event event, jthread thread);

#endif /* _EVENT_MANAGER_ */
