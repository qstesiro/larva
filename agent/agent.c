#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include <jvmti.h>

#include "agent.h"
#include "network_manager.h"
#include "command_manager.h"
#include "event_manager.h"

struct agent* agent = NULL;

jvmti_error agent_enable_all_caps(struct agent* agent);

JNIEXPORT
jint JNICALL Agent_OnLoad(JavaVM *vm, char *options, void *reserved) {
	printf("[INFO] OnLoad\n");
	agent = agent_create(vm);	
	if (NULL != agent) {
		agent_enable_all_caps(agent);
		event_manager_enable_monitor(agent_get_event_manager(agent), JVMTI_EVENT_VM_INIT, NULL);		
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

struct agent {
	JavaVM* vm;
	jvmti_env* jvmti;
    struct network_manager* network;
	struct command_manager* command;
	struct event_manager* event;	
};

struct agent* agent_object() {
	return agent;
}

struct agent* agent_create(JavaVM* vm) {
	if (NULL != vm) {
		struct agent* agent = (struct agent*)malloc(sizeof(struct agent));
		if (NULL != agent) {		
			memset(agent, 0, sizeof(struct agent));
			agent->vm = vm;
			if (JNI_OK == (*agent->vm)->GetEnv(agent->vm, (void**)&agent->jvmti, JVMTI_VERSION_1_2)) {
				agent->network = network_manager_create(agent);
				agent->command = command_manager_create(agent);
				agent->event = event_manager_create(agent);
				if (NULL != agent->network &&
					NULL != agent->command &&
					NULL != agent->event) {
					return agent;					
				}
			}
		}
		if (NULL != agent) {
			agent_destroy(agent); agent = NULL;
		}		
	}
	return NULL;
}

void agent_destroy(struct agent* agent) {
	if (NULL != agent) {
		network_manager_destroy(agent->network);
		command_manager_destroy(agent->command);
		event_manager_destroy(agent->event);
		free(agent); agent = NULL;
	}
}

JavaVM* agent_get_vm(struct agent* agent) {
	if (NULL != agent) {
		return agent->vm;
	}
	return NULL;
}

jvmti_env* agent_get_jvmti(struct agent* agent) {
	if (NULL != agent) {
		return agent->jvmti;
	}
	return NULL;
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

jvmti_error agent_enable_all_caps(struct agent* agent) {
	if (NULL != agent) {
		jvmti_error rev = JVMTI_ERROR_NONE;
	    jvmti_capabilities caps = {0};	   
		rev = (*agent->jvmti)->GetPotentialCapabilities(agent->jvmti, &caps);
		if (JVMTI_ERROR_NONE == rev) {
			return (*agent->jvmti)->AddCapabilities(agent->jvmti, &caps);
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
