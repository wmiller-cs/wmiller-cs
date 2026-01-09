#ifndef _SIRCD_H_
#define _SIRCD_H_

#include <sys/types.h>
#include <netinet/in.h>

#define MAX_MSG_TOKENS 10
#define MAX_MSG_LEN 256
#define MAX_CLIENTS 512
#define MAX_CHANNELS 16

#define MAX_USERNAME 32
#define MAX_HOSTNAME 512
#define MAX_SERVERNAME 512
#define MAX_REALNAME 512

#define MAX_CHANNAME 64

#define MAX_COMMAND 16

typedef struct cha channel;
typedef struct cli client;


typedef struct cha{
		char name[MAX_CHANNAME]; // channel name
		client *users[MAX_CLIENTS]; // array of pointers to users currently in channel
		int user_count; // number of users currently in channel
} channel;

//extern int cur_channel_count;
extern channel *channels[MAX_CHANNELS];


typedef struct cli{
    int sock;
    struct sockaddr_in cliaddr;
    unsigned inbuf_size;
    int registered;
    char servername[MAX_SERVERNAME];
    char username[MAX_USERNAME];
    char hostname[MAX_HOSTNAME];
    char realname[MAX_REALNAME];
    char inbuf[MAX_MSG_LEN+1];
    channel *channel;
    char nickname[MAX_USERNAME];
    int has_nicked;
    int has_userd;
    int has_welcomed;
    int ip;
    int motd; // 0 if user hasn't received Message of the Day, 1 if should, and 2 if already has
} client;

extern int cur_user_count;
extern client *clients[MAX_CLIENTS];


extern int send_echo; // 0 don't send, 1 to channel, 2 to user
extern channel recipients;
extern channel recipients_from_channel;

extern int m_type; // message case, depends on what command used

extern char send_message[MAX_MSG_LEN];
extern char send_message2[MAX_MSG_LEN];
extern char send_message3[MAX_MSG_LEN];

extern int to_error;
extern char error_message[MAX_MSG_LEN];

void handle_line(client *c, char *line);
void part_helper(client *c);

#endif /* _SIRCD_H_ */
