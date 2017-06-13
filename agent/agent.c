#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <jvmti.h>

#include "agent.h"
#include "network_manager.h"
#include "command_manager.h"
#include "event_manager.h"

struct agent* agent = NULL;
jvmti_env* jvmti_ptr = NULL;

jvmti_error agent_enable_all_caps(struct agent* agent);

JNIEXPORT
jint JNICALL Agent_OnLoad(JavaVM *vm, char *options, void *reserved) {
	printf("[INFO] OnLoad\n");
	agent = agent_create(vm);	
	if (NULL != agent) {
		agent_enable_all_caps(agent);
		event_manager_enable_monitor(agent->event, JVMTI_EVENT_CLASS_PREPARE, NULL);
		event_manager_enable_monitor(agent->event, JVMTI_EVENT_METHOD_ENTRY, NULL);
		event_manager_enable_monitor(agent->event, JVMTI_EVENT_METHOD_EXIT, NULL);
	}
	return 0;
}

JNIEXPORT
jint JNICALL Agent_OnAttach(JavaVM* vm, char *options, void *reserved) {
	printf("OnAttach\n"); return 0;
}

JNIEXPORT
void JNICALL Agent_OnUnload(JavaVM *vm) {
	if (NULL != agent) {
		agent_destroy(agent); agent = NULL;
	}
	printf("OnUnload\n");
}

struct agent* agent_create(JavaVM* vm) {
	struct agent* agent = NULL;
	do {
		agent = (struct agent*)malloc(sizeof(struct agent));
		if (NULL == agent) {
			break;
		}
		memset(agent, 0, sizeof(struct agent));
		agent->vm = vm;
		jvmti_ptr = agent_get_jvmti(agent);	
		agent->network = network_manager_create(agent);
		if (NULL == agent->network) {
			break;
		}
		agent->command = command_manager_create(agent);
		if (NULL == agent->command) {
			break;
		}
		agent->event = event_manager_create(agent);
		if (NULL == agent->event) {
			break;
		}
	} while (FALSE);	
	return agent;
}

void agent_destroy(struct agent* agent) {
	if (NULL != agent) {
		network_manager_destroy(agent->network);
		command_manager_destroy(agent->command);
		event_manager_destroy(agent->event);
		free(agent); agent = NULL;
	}
}

struct agent* agent_instance() {
	return agent;
}

struct network_manager* agent_get_network_manager(struct agent* agent) {
	if (NULL != agent) {
		return agent->network;
	}
	return NULL;
}

struct command_manager* agent_get_command_manager(struct agent* agent) {
	if (NULL != agent) {
		return agent->command;
	}
	return NULL;
}

struct event_manager* agent_get_event_manager(struct agent* agent) {
	if (NULL != agent) {
		return agent->event;
	}
	return NULL;
}

jvmti_env* agent_get_jvmti(struct agent* agent) {
	if (NULL != agent && NULL != agent->vm) {
		jvmti_env* jvmti = NULL;
		int rev = (*agent->vm)->GetEnv(agent->vm, (void**)&jvmti, JVMTI_VERSION_1_2);
		if (JNI_OK == rev) {
			return jvmti;
		}
	}
	return NULL;
}

jvmti_error agent_enable_all_caps(struct agent* agent) {
	if (NULL != agent) {
		jvmtiError rev = JVMTI_ERROR_NONE;
		jvmtiCapabilities caps = {0};
		jvmti_env* jvmti = agent_get_jvmti(agent);
		// rev = (*jvmti)->GetPotentialCapabilities(jvmti, &caps);
		rev = (*jvmti_ptr)->GetPotentialCapabilities(jvmti_ptr, &caps);
		if (JVMTI_ERROR_NONE == rev) {
			printf("bbbbbbbbbbbbbbbbbbb");
			return (*jvmti_ptr)->AddCapabilities(jvmti_ptr, &caps);
		}
		return rev;
	}
	return JVMTI_ERROR_ILLEGAL_ARGUMENT;
}

/* void JNICALL print_version(JVMTI* jvmti) { */
/* 	if (null != jvmti) { */
/* 		jint version = 0; */
/* 		if (JVMTI_ERROR_NONE == (*jvmti)->GetVersionNumber(jvmti, &version)) { */
/* 			printf("0x%08x\n", version); */
/* 		} */
/* 	} */
/* } */

/* void JNICALL enable_all_caps(JVMTI* jvmti) { */
/* 	if (null != jvmti) { */
/* 		do { */
/* 			jvmtiError rev = JVMTI_ERROR_NONE; */
/* 			jvmtiCapabilities caps = {0}; */
/* 			rev = (*jvmti)->GetPotentialCapabilities(jvmti, &caps); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				break; */
/* 			} */
/* 			rev = (*jvmti)->AddCapabilities(jvmti, &caps); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				break; */
/* 			} */
/* 		} while (0); */
/* 	} */
/* } */

/* void JNICALL set_handlers(JVMTI* jvmti) { */
/* 	if (null != jvmti) { */
/* 		do { */
/* 			jint rev = JVMTI_ERROR_NONE; */
/* 			handlers.ClassPrepare = prepare_class; */
/* 			handlers.MethodEntry = method_entry; */
/* 			handlers.MethodExit = method_exit; */
/* 			rev = (*jvmti)->SetEventCallbacks(jvmti, &handlers, sizeof(jvmtiEventCallbacks)); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				printf("SetEventCallbacks #%d\n", rev); break; */
/* 			} */
/* 			rev = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_CLASS_PREPARE, null); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				printf("SetEventNotificationMode JVMTI_EVENT_CLASS_PREPARE #%d\n", rev); break; */
/* 			} */
/* 			rev = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_METHOD_ENTRY, null); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				printf("SetEventNotificationMode JVMTI_EVENT_METHOD_ENTRY #%d\n", rev); break; */
/* 			} */
/* 			rev = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, JVMTI_EVENT_METHOD_EXIT, null); */
/* 			if (JVMTI_ERROR_NONE != rev) { */
/* 				printf("SetEventNotificationMode JVMTI_EVENT_METHOD_EXIT #%d\n", rev); break; */
/* 			} */
/* 		} while (0); */
/* 	} */
/* } */

/* void JNICALL prepare_class(JVMTI* jvmti, jni* jni, jthread thread, jclass clazz) { */
/* 	if (null != jvmti && null != clazz) { */
/* 		char* sign = null;		 */
/* 		if (JVMTI_ERROR_NONE == (*jvmti)->GetClassSignature(jvmti, clazz, &sign, null)) { */
/* 			printf("sign -> %s\n", sign);			 */
/* 		} */
/* 		if (null != sign) { */
/* 			(*jvmti)->Deallocate(jvmti, sign); sign = null; */
/* 		} */
/* 	} */
/* } */

/* void JNICALL method_entry(JVMTI* jvmti, jni* jni, jthread thread, jmethodID method) { */
/* 	printf("method_entry\n"); */
/* } */

/* void JNICALL method_exit(JVMTI* jvmti, jni* jni, jthread thread, jmethodID method, jboolean exception, jvalue return_value) { */
/* 	printf("method_exit\n"); */
/* } */
