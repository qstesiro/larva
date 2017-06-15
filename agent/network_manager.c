#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <errno.h>

#include <pthread.h>
#include <sys/types.h>
#include <arpa/inet.h>

#include <event2/listener.h>
#include <event2/bufferevent.h>
#include <event2/buffer.h>

#include "agent.h"
#include "network_manager.h"

struct network_manager {
	struct agent* agent;
	pthread_t thread;
	struct event_base* base;
	struct evconnlistener* listener;
	struct bufferevent* event;
};

static void* network_manager_thread_routine(void* context);
static void network_manager_accept_handler(struct evconnlistener* listener, evutil_socket_t socket, struct sockaddr* address, int size, void* context);
static void network_manager_accept_error_handler(struct evconnlistener* listener, void* context);
static void network_manager_read_handler(struct bufferevent* buffer, void* context);
static void network_manager_read_error_handler(struct bufferevent* buffer, short error, void* context);

struct network_manager* network_manager_create(struct agent* agent) {
	struct network_manager* manager = (struct network_manager*)malloc(sizeof(struct network_manager));
	if (NULL != manager) {
		memset(manager, 0, sizeof(struct network_manager));
		manager->agent = agent;
		return manager;
	}
	if (NULL != manager) {
		network_manager_destroy(manager);
		manager = NULL;
	}
	return NULL;
}

void network_manager_destroy(struct network_manager* manager) {
	if (NULL != manager) {		
		free(manager);
		manager = NULL;
	}
}

int network_manager_startup(struct network_manager* manager) {
	printf("1\n");
	if (NULL != manager && NULL == manager->base && 0 == manager->thread) {
		printf("1\n");
		manager->base = event_base_new();		
		if (NULL != manager->base) {
			printf("1\n");
			if(0 != pthread_create(&manager->thread, NULL, network_manager_thread_routine, manager)) {					
				printf("create thread failed.\n");
			}
		}
	}	
}

int network_manager_stop(struct network_manager* manager) {
	if (NULL != manager) {
		printf("a\n");
		// event_base_loopexit(manager->base, NULL);
		// event_active(manager->listener, EV_TIMEOUT, 0);				
		if (NULL != manager->listener) {
			evconnlistener_free(manager->listener);
			manager->listener = NULL;
		}
		printf("b\n");
		if (NULL != manager->event) {
			bufferevent_free(manager->event);
			manager->event = NULL;
		}		
		printf("c\n");		
		if (NULL != manager->base) {
			event_base_free(manager->base);
			manager->base = NULL;
		}				
		printf("d\n");		
		if (0 != manager->thread) {
			pthread_cancel(manager->thread); // I don`t want to cancel but I can`t find quit method :(
			pthread_join(manager->thread, NULL);
			manager->thread = 0;
		}
		printf("e\n");
	}
}

static void* network_manager_thread_routine(void* context) {	
	if (NULL != context) {
		printf("1\n");
		struct network_manager* manager = (struct network_manager*)context;
		if (NULL != manager->base && NULL == manager->listener) {
			printf("2\n");
			struct sockaddr_in address;
			memset(&address, 0, sizeof(struct sockaddr_in));
			address.sin_family = AF_INET;
			address.sin_addr.s_addr = inet_addr("127.0.0.1");
			address.sin_port = htons(1025);
			manager->listener = evconnlistener_new_bind(manager->base,
														network_manager_accept_handler, manager,
														LEV_OPT_CLOSE_ON_FREE | LEV_OPT_REUSEABLE | LEV_OPT_THREADSAFE,
														-1, (struct sockaddr*)&address, sizeof(struct sockaddr_in));
			if (NULL != manager->listener) {
				printf("3\n");
				evconnlistener_set_error_cb(manager->listener, network_manager_accept_error_handler);
				event_base_dispatch(manager->base);
				printf("4\n");
			}
		}
	}
	return ((void*)0);
}

static void network_manager_accept_handler(struct evconnlistener* listener, evutil_socket_t socket, struct sockaddr* address, int size, void* context) {
	printf("network_manager_accept_handler\n");	
	struct event_base* base = evconnlistener_get_base(listener);
	evutil_make_socket_nonblocking(socket);
	// I don`t why read_handler will not be invoke when BEV_OPT_THREADSAFE is set :(
	struct bufferevent* event = bufferevent_socket_new(base, socket, BEV_OPT_CLOSE_ON_FREE /* | BEV_OPT_THREADSAFE */); 
	if (NULL != event) {
		bufferevent_setcb(event, network_manager_read_handler, NULL, network_manager_read_error_handler, NULL);
		bufferevent_enable(event, EV_READ | EV_WRITE);
	}
}

static void network_manager_accept_error_handler(struct evconnlistener* listener, void* context) {
	printf("network_manager_accept_error_handler\n");
	struct event_base* base = evconnlistener_get_base(listener);
	printf("%s", evutil_socket_error_to_string(EVUTIL_SOCKET_ERROR()));
	event_base_lookpexit(base, NULL);
}

static void network_manager_read_handler(struct bufferevent* event, void* context) {
	printf("network_manager_read_handler\n");
	char* data = NULL;
	struct evbuffer* input = bufferevent_get_input(event);
	struct evbuffer* output = bufferevent_get_output(event);
	size_t size = evbuffer_get_length(input);
	data = (char*)malloc(size * 2);
	if (NULL != data) {
		memset(data, 0, size * 2);
		evbuffer_remove(input, data, size);
		printf("%s\n", data);
		evbuffer_add_printf(output, "%s%s", data, "hello");
		bufferevent_write_buffer(event, output);
	}
}

static void network_manager_read_error_handler(struct bufferevent* event, short error, void* context) {
	printf("network_manager_read_error_handler\n");
	if (error & BEV_EVENT_EOF) {
		/* connection has been closed, do any clean up here */
		printf("connection closed\n");
	} else if (error & BEV_EVENT_ERROR) {
		/* check errno to see what error occurred */
		printf("some other error\n");
	} else if (error & BEV_EVENT_TIMEOUT) {
		/* must be a timeout event handle, handle it */
		printf("Timed out\n");
	}
	bufferevent_free(event);
}


int send_event_data(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

int network_manager_send_query_data(struct network_manager* manager, char* buffer, int size, int timeout) {
	if (NULL != manager && NULL != buffer && 0 < size) {
		
	}
}

