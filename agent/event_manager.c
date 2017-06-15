#include <stdio.h>
#include <malloc.h>
#include <string.h>

#include <jvmti.h>

#include "agent.h"
#include "event_manager.h"

struct event_manager {
	struct agent* agent;
	jvmti_event_handlers handlers;
	int events[JVMTI_MAX_EVENT_TYPE_VAL - JVMTI_MIN_EVENT_TYPE_VAL];	
};

/* void* event_manager_thread_routine(void* argv); */

jvmti_error event_manager_set_handlers(struct event_manager* manager);

void JNICALL event_manager_vm_init_handler(jvmti_env *jvmti, jni_env* jni, jthread thread);
void JNICALL event_manager_prepare_class_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jclass clazz);
void JNICALL event_manager_method_entry_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jmethodID method);
void JNICALL event_manager_method_exit_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jmethodID method, jboolean exception, jvalue value);
	
struct event_manager* event_manager_create(struct agent* agent) {
	struct event_manager* manager = (struct event_manager*)malloc(sizeof(struct event_manager));
	if (NULL != manager) {
		memset(manager, 0, sizeof(struct event_manager));
		manager->agent = agent;
		event_manager_set_handlers(manager);
		return manager;
	}
	if (NULL != manager) {
		event_manager_destroy(manager);
		manager = NULL;
	}
	return NULL;
}

void event_manager_destroy(struct event_manager* manager) {
	if (NULL != manager) {
		free(manager); manager = NULL;
	}
}

jvmti_error event_manager_enable_monitor(struct event_manager* manager, jvmti_event event, jthread thread) {	
	if (NULL != manager) {
		int i = event - JVMTI_MIN_EVENT_TYPE_VAL;
		if (TRUE != manager->events[i]) {
			jvmti_env* jvmti = agent_get_jvmti(manager->agent);
			if (NULL != jvmti) {
				int rev = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, event, thread);
				if (JVMTI_ERROR_NONE == rev) {
					manager->events[i] = TRUE;
				}
				return rev;
			}			
		}
	}	
	return JVMTI_ERROR_ILLEGAL_ARGUMENT;
}

jvmti_error event_manager_disable_monitor(struct event_manager* manager, jvmti_event event, jthread thread) {
	if (NULL != manager) {
		int i = event - JVMTI_MIN_EVENT_TYPE_VAL;
		if (TRUE != manager->events[i]) {
			jvmti_env* jvmti = agent_get_jvmti(manager->agent);
			if (NULL != jvmti) {
				int rev = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_DISABLE, event, thread);
				if (JVMTI_ERROR_NONE == rev) {
					manager->events[i] = TRUE;
				}
				return rev;
			}			
		}
	}
	return JVMTI_ERROR_ILLEGAL_ARGUMENT;
}

jvmti_error event_manager_set_handlers(struct event_manager* manager) {
	if (NULL != manager) {
		jvmti_env* jvmti = agent_get_jvmti(manager->agent);
		if (NULL != jvmti) {
			manager->handlers.VMInit = event_manager_vm_init_handler;
			manager->handlers.ClassPrepare = event_manager_prepare_class_handler;
			manager->handlers.MethodEntry = event_manager_method_entry_handler;
			manager->handlers.MethodExit = event_manager_method_exit_handler;
			return (*jvmti)->SetEventCallbacks(jvmti, &manager->handlers, sizeof(jvmti_event_handlers));
		}
	}
	return JVMTI_ERROR_ILLEGAL_ARGUMENT;
}

/* void* event_manager_thread_routine(void* argv) { */
/* 	if (NULL != argv) {		 */
/* 		struct agent* agent = (struct agent*)argv; */
/* 		if (NULL != agent_get_vm(agent)) { */
/* 			int rev = JVMTI_ERROR_NONE; */
/* 			jni_env* jni = NULL; */
/* 			rev = (*agent_get_vm(agent))->AttachCurrentThread(agent_get_vm(agent), (void**)&jni, NULL); */
/* 			if (JNI_OK == rev) { */
/* 				event_manager_enable_monitor(agent_get_event_manager(agent), JVMTI_EVENT_CLASS_PREPARE, NULL); */
/* 				event_manager_enable_monitor(agent_get_event_manager(agent), JVMTI_EVENT_METHOD_ENTRY, NULL); */
/* 				event_manager_enable_monitor(agent_get_event_manager(agent), JVMTI_EVENT_METHOD_EXIT, NULL); */
/* 				rev = (*agent_get_vm(agent))->DetachCurrentThread(agent_get_vm(agent)); */
/* 				if (JNI_OK != rev) printf("DetachCurrentThread #%d", rev); */
/* 			} */
/* 		} */
/* 	} */
/* 	return ((void*)0); */
/* } */

void JNICALL event_manager_vm_init_handler(jvmti_env *jvmti, jni_env* jni, jthread thread) {	
	struct event_manager* manager = agent_get_event_manager(agent_object());	
	printf("agent->jvmti: %p\n", agent_get_jvmti(agent_object()));
	printf("argv->jvmti: %p\n", jvmti);		
}

void JNICALL event_manager_prepare_class_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jclass clazz) {
	/* int rev = JVMTI_ERROR_NONE; */
	/* char* sign = NULL; */
	/* rev = (*jvmti)->GetClassSignature(jvmti, clazz, &sign, NULL); */
	/* if (JVMTI_ERROR_NONE == rev) { */
	/* 	printf("sign -> %s\n", sign); */
	/* 	(*jvmti)->Deallocate(jvmti, sign); sign = NULL; */
	/* }	 */
	printf("prepare_class_handler\n");
}

void JNICALL event_manager_method_entry_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jmethodID method) {
	printf("method_entry_handler\n");
}

void JNICALL event_manager_method_exit_handler(jvmti_env* jvmti, jni_env* jni, jthread thread, jmethodID method, jboolean exception, jvalue value) {
	printf("method_exit_handler\n");
}
