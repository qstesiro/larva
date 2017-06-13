#ifndef _AGENT_H_
#define _AGENT_H_

#define TRUE 1
#define FALSE 0

#include <jvmti.h>

typedef jvmtiEnv jvmti_env;
typedef JNIEnv jni_env;
typedef jvmtiError jvmti_error;
typedef jvmtiEventMode jvmti_event_mode;
typedef jvmtiEvent jvmti_event;
typedef jvmtiEventCallbacks jvmti_event_handlers;

struct network_manager;
struct command_manager;
struct event_manager;

struct agent {
	JavaVM* vm;
    struct network_manager* network;
	struct command_manager* command;
	struct event_manager* event;
};

struct agent* agent_create(JavaVM* vm);
void agent_destroy(struct agent* agent);

struct agent* agent_instance();

struct network_manager* agent_get_network_manager(struct agent* agent);
struct command_manager* agent_get_command_manager(struct agent* agent);
struct event_manager* agent_get_event_manager(struct agent* agent);

jvmti_env* agent_get_jvmti(struct agent* agent);

/* void print_version(JVMTI* jvmti); */

/* void set_handlers(JVMTI* jvmti); */

/* void JNICALL prepare_class(JVMTI* jvmti, jni* jni, jthread thread, jclass klass); */
/* void JNICALL method_entry(JVMTI* jvmti, jni* jni, jthread thread, jmethodID method); */
/* void JNICALL method_exit(JVMTI* jvmti, jni* jni, jthread thread, jmethodID method, jboolean exception, jvalue return_value); */

#endif /* _AGENT_H_ */
