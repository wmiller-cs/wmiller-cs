#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <assert.h>

#include <sys/socket.h> // for socket(), bind(), and connect()
#include <sys/select.h> // for select()
#include <arpa/inet.h>  // for sockaddr_in and inet_ntoa()
#include <stdio.h>      // for printf() and fprintf()
#include <stdlib.h>     // for atoi() and exit()
#include <string.h>     // for memset()
#include <unistd.h>     // for close()
#include <fcntl.h>      // for fcntl()
#include <ctype.h>      // for toupper()
#include <errno.h>      // errno

#include <sys/types.h> // for getnameinfo()
#include <netdb.h> // for getnameinfo()
#include "sircs.h"
#include "debug.h"
#include "irc_proto.h"
#define MAXPENDING 10

// initalizes empty recipants channel, used for sending messages
channel recipients = (channel){0}; 

// initalizes empty channel for sending messages to an entire channel execpt sender
channel recipients_from_channel = (channel){0}; 

// shows which message type to send
int m_type = -1; 

// message to send to other clients
char send_message[MAX_MSG_LEN]; 

 // used for join, since it sends up to 3 different messages
char send_message2[MAX_MSG_LEN];
char send_message3[MAX_MSG_LEN];

// set to ensure error messages send
int to_error = 0; 

// error message
char error_message[MAX_MSG_LEN]; 

// initalizing channels, clients, and current users on server
channel *channels[MAX_CHANNELS]; 
client *clients[MAX_CLIENTS]; 
int cur_user_count = 0; 

/**
 * This function searches for right client by sock number, returns index
 * Potential improvement: adding an index field to client struct
 */
int findClientBySock(int sock) {
	for (int i = 0; i < MAX_CLIENTS; i++) {
		if (clients[i]->sock == sock) { 
				return i;
		}
	}
	return -1;
}

/**
 * Prints usage instructions and exits.
 */
void usage() {
	fprintf(stderr, "sircs [-d debug_lvl] <port>\n");
	exit(-1);
}

/* This is just like the write() system call, accept that it will
 make sure that all data is transmitted. */
int sockWrite(int sockfd, char *buf, size_t count){
	size_t bytesSent = 0, thisWrite;
	
	while (bytesSent < count) {
		do
			thisWrite = write(sockfd, buf, count - bytesSent);
		while ( (thisWrite < 0) && (errno == EINTR) );
		if (thisWrite <= 0)
			return thisWrite;
		bytesSent += thisWrite;
		buf += thisWrite;
	}
	return count;
}

/* This function writes a character string out to a socket.  It will 
 return -1 if the connection is closed while it is trying to write. */
int sockPuts(int sockfd, char *str){
	return sockWrite(sockfd, str, strlen(str));
}

/**
 * This function sends MOD message if registered and not yet welcomed and
 * could be optomized by better division between sircs and irc_proto role
 */
void motdCheck(client *c) {
	// check if ready to be welcomed, if so send MOD
	if (((c->has_nicked + c->has_userd) == 2) && (c->has_welcomed == 0)) { 

	char motd_welcome[MAX_MSG_LEN];
	snprintf(motd_welcome, sizeof(motd_welcome), ":Lserver 375 %s :- Lserver Message of the day - \n", c->nickname);
	sockPuts(c->sock, motd_welcome);

  char motd_message[MAX_MSG_LEN];
	snprintf(motd_message, sizeof(motd_message), ":Lserver 372 %s :- Hello There. - \n", c->nickname);
	sockPuts(c->sock, motd_message);
							

	char motd_end[MAX_MSG_LEN];
	snprintf(motd_end, sizeof(motd_end), ":Lserver 376 %s :End of /MOTD command.\n", c->nickname);
	sockPuts(c->sock, motd_end);
	c->has_welcomed = 1; // set flag to welcomed
	}
}

/* This function reads from a socket, until it recieves a linefeed
 character.  It fills the buffer "str" up to the maximum size "count".
 
 This function will return -1 if the socket is closed during the read
 operation.
 
 Have not handled if the buffer is too large. */
int sockGets(int sockfd, char *str, size_t count){
	int bytes_read;
	int total_count = 0;
	char *current_position;
	char last_read = 0;

	current_position = str;
	while (last_read != 10) {
		bytes_read = read(sockfd, &last_read, 1); // fatal error if inputting more than the buffer can hold
		if (bytes_read <= 0) {
			/* The other side may have closed unexpectedly */
			return -1; /* Is this effective on other platforms than linux? */
		}
		if ( (total_count < count) && (last_read != 10) && (last_read !=13) ) {
			current_position[0] = last_read;
			current_position++;
			total_count++;
		}
	}

	if (count > 0)
		current_position[0] = 0;
	return total_count;
}

/* Sets socket to be non-blocking so we can perform correct operations.*/
void setNonBlocking(int sock){
	
	int opts = fcntl(sock, F_GETFL);
	if (opts < 0) {
		perror("fcntl(F_GETFL) failed");
		exit(1);
	}
	
	opts = (opts | O_NONBLOCK);
	if (fcntl(sock,F_SETFL,opts) < 0) {
		perror("fcntl(F_SETFL) failed");
		exit(1);
	}
	return;
}


/* Sets socket to be non-blocking so we can perform correct operations.*/
void buildSelectSet(fd_set *fdset, int listenfd, int *highSock, int *connfdArray) {
	/* First put together fd_set for select(), which will
	 consist of the sock veriable in case a new connection
	 is coming in, plus all the sockets we have already
	 accepted. */
	
	/* FD_ZERO() clears out the fd_set called socks, so that
	 it doesn't contain any file descriptors. */
	
	FD_ZERO(fdset);
	
	/* FD_SET() adds the file descriptor "sock" to the fd_set,
	 so that select() will return if a connection comes in
	 on that socket (which means you have to do accept(), etc. */
	
	FD_SET(listenfd, fdset);
	
	/* Loops through all the possible connections and adds
	 those sockets to the fd_set */
	*highSock = listenfd;
	for (int i = 0; i < MAXPENDING; i++) {
		if (connfdArray[i] != 0) {
			FD_SET(connfdArray[i], fdset);
			if (connfdArray[i] > *highSock)
				*highSock = connfdArray[i];
		}
	}
}

/* Accepts or rejects incoming connections.*/
void handleNewConn(int listenfd, int *connfdArray) {

	int connfd;      /* Socket file descriptor for incoming connections */
  struct sockaddr_in cliAddr;
  socklen_t cliAddrLen = sizeof(cliAddr);

	/* We have a new connection coming in!  We'll
	 try to find a spot for it in connfdArray. */
	if((connfd = accept(listenfd, (struct sockaddr *) &cliAddr, &cliAddrLen)) < 0){
		perror("accept() failed");
		exit(1);
	}
	
  char hostname[512] = "";
  int error;
 
  error = getnameinfo((struct sockaddr *) &cliAddr, sizeof(cliAddr), hostname, 512, NULL, 0, 0);
  if (error != 0){
    fprintf(stderr, "error in getnameinfo: %s\n", gai_strerror(error));
  }


	setNonBlocking(connfd);

	for (int i = 0; (i < MAXPENDING) && (connfd != -1); i++){
		if (connfdArray[i] == 0) {
			printf("\nConnection accepted:   FD=%d; Slot=%d, host=%s\n", connfd, i, hostname);

			// reference to client we'e initalizing
			client *accepted_client = clients[cur_user_count];

			// setting dummy nickname and hostname
			strcpy(accepted_client->nickname, "*");
			strcpy(accepted_client->hostname, hostname);

			// setting connfd, cliAddr, and zeroed channel
			accepted_client->sock = connfd;
			accepted_client->cliaddr = cliAddr;
			accepted_client->channel = &(channel){0};


			// increment user count for array access
			cur_user_count++; 

			printf("Responded to unregistered client at slot %d", i);

			// adding to connfd array
			connfdArray[i] = connfd;
			connfd = -1;
		}
	}
	
	if (connfd != -1) {
		/* No room left in the queue! */
		printf("\nNo room left for new client.\n");
		sockPuts(connfd, "Sorry, this server is too busy. Try again later!\r\n");
		close(connfd);
	}
}

/* Handles data passed into the server, performs all upkeep and related irc_proto functionality */
void dealWithData(
					int i,			/* Current item in connfdArray for for loops */
					int *connfdArray) {
	
	char buffer[MAX_MSG_LEN];     /* Buffer for socket reads */
	
	// find client we're dealing with
	int cli_i = findClientBySock(connfdArray[i]);
	client *cur_client = clients[cli_i];

	// to check if user has left, also an incorrect implementation of a buffer check, believe it must go inside sockGets
	int message_length = sockGets(connfdArray[i], buffer, MAX_MSG_LEN);
	
	if (message_length < 0){ // if connection closed, close this end and free up entry in connfdArray
		printf("\nConnection lost: FD=%d;  Slot=%d\n", connfdArray[i],i);

		if (strlen(cur_client->channel->name)){ // check if client has a channel
			part_helper(cur_client); // remove client from channel
			recipients = *cur_client->channel; // we must notify channel gracefully

			// send graceful message to parted channel
			for (int i=0; i<recipients.user_count; i++){
				if (strlen(recipients.users[i]->nickname)){ // if user present, send graceful message
					sockPuts(recipients.users[i]->sock, ":");
					sockPuts(recipients.users[i]->sock, cur_client->nickname);
					sockPuts(recipients.users[i]->sock, "!");
					sockPuts(recipients.users[i]->sock, cur_client->username);
					sockPuts(recipients.users[i]->sock, "@");
					sockPuts(recipients.users[i]->sock, cur_client->hostname);
					sockPuts(recipients.users[i]->sock, " QUIT :");
					sockPuts(recipients.users[i]->sock, cur_client->nickname);
					sockPuts(recipients.users[i]->sock, " The channel lost a valued member");
					sockPuts(recipients.users[i]->sock, "\n");
				}
			}
		}

		// empty client and remove them from connfdArray
		*cur_client = (client){0};
		close(connfdArray[i]);
		connfdArray[i] = 0;
	} 
	else if (message_length > MAX_MSG_LEN){	// should tell user message is too long, unfinished
		sockPuts(cur_client->sock, "\nYour message is too long. Messages must be under ");
		char str_len[20];
		sprintf(str_len, "%d", MAX_MSG_LEN);
		sockPuts(cur_client->sock, str_len);
		sockPuts(cur_client->sock, " characters long.\n");
		printf("%s's message is too long. Messages must be under %i characters long.\n", cur_client->nickname, MAX_MSG_LEN);
		return;
	}
	else { // handle message
		printf("\nReceived from %s: %s\n", cur_client->nickname, buffer);

		// handles commands or lack thereof, updating sendmessage and channels/clients accordingly
		handle_line(cur_client, buffer);
		
		printf("\nHandled line from %s: %s\n", cur_client->nickname, buffer);

		// if error needs to be sent, send it to current user
		if(to_error){
			// send error to client
			sockPuts(cur_client->sock, error_message);
			sockPuts(cur_client->sock, "\n");

			printf("%s was sent the following error message: %s\n", cur_client->nickname, error_message);

			// reset error flag
			to_error = 0;

			return;
		}
      else{
				switch (m_type){ // sends to recipents or channel, handles some send logic that could be better organized/improved 

					case -1: // uninitalized
						break;
					case 0: // NICK, send to channel
						if (strlen(cur_client->channel->name)){ // check if client has a channel
							
							for (int i=0; i<cur_client->channel->user_count; i++){
								client *cli_i = cur_client->channel->users[i]; // potential client being sent message
								int i_sock = cli_i->sock; // socket of client

								if (cli_i != NULL){ // if active, send message
									sockPuts(i_sock, send_message);
									sockPuts(i_sock, "\n");
								}
							}
						}
						
						motdCheck(cur_client); // check if need to send MOTD

						break;
					case 1: // USER, don't normally send to anyone

						motdCheck(cur_client); // check if need to send MOTD
						break;
					case 2: // QUIT, send to quitted channel 
						if (strlen(cur_client->channel->name)){ // if channel used
							for (int i=0; i<cur_client->channel->user_count; i++){

								client *cli_i = cur_client->channel->users[i]; // potential client being sent message
								int i_sock = cli_i->sock; // socket of client

								if (cli_i != NULL){  // if active, send message

									// probably could be improved
									sockPuts(i_sock, ":");
									sockPuts(i_sock, cur_client->nickname);
									sockPuts(i_sock, "!");
									sockPuts(i_sock, cur_client->username);
									sockPuts(i_sock, "@");
									sockPuts(i_sock, cur_client->hostname);
									sockPuts(i_sock, " QUIT :Connection closed");
									sockPuts(i_sock, send_message);
									sockPuts(i_sock, "\n");
								}
							}
						}

						part_helper(cur_client); // part from current channel
						*cur_client = (client){0}; // erase client

						// close conenction
						close(connfdArray[i]);
						connfdArray[i] = 0;

						break;
					case 3: // JOIN, send to parted channel if applicable, client joining, and joined channel
						
					  // send part message to parted channel
						for (int i=0; i<recipients.user_count; i++){

							client *cli_i = recipients.users[i]; // potential client being sent message
							int i_sock = cli_i->sock; // socket of client

							if (cli_i != NULL){ // if active, send message
								sockPuts(i_sock, ":");
								sockPuts(i_sock, cur_client->nickname);
								sockPuts(i_sock, "!");
								sockPuts(i_sock, cur_client->username);
								sockPuts(i_sock, "@");
								sockPuts(i_sock, cur_client->hostname);
								sockPuts(i_sock, " QUIT :");
								sockPuts(i_sock, cur_client->nickname);
								sockPuts(i_sock, " The channel lost a valued member");
								sockPuts(i_sock, "\n");
							}
						}

						// send join message to joined channel
						for (int i=0; i<cur_client->channel->user_count; i++){

							client *cli_i = cur_client->channel->users[i];  // potential client being sent message
							int i_sock = cli_i->sock; // socket of client

							if (cur_client->sock == i_sock) { // don't send to cur_client
								continue;
							}

							if (cli_i != NULL){ // if user present
								char joinecho[MAX_MSG_LEN]; 
								snprintf(joinecho, sizeof(joinecho), ":%s JOIN %s\n", cur_client->nickname, cur_client->channel->name);
								sockPuts(i_sock, joinecho);
							}
						}
						
						// send channel info to client joining
						char join_echo[MAX_MSG_LEN];
						snprintf(join_echo, sizeof(join_echo), ":%s JOIN %s \n", cur_client->nickname, cur_client->channel->name);
						sockPuts(cur_client->sock, join_echo);

						char join_who[MAX_MSG_LEN];
						snprintf(join_who, sizeof(join_who), ":Lserver 353 %s = ", cur_client->nickname);
						sockPuts(cur_client->sock, join_who);
						sockPuts(cur_client->sock, send_message3);
						sockPuts(cur_client->sock, "\n");

						char join_end[MAX_MSG_LEN];
						snprintf(join_end, sizeof(join_end), ":Lserver 366 %s %s :End of /NAMES list\n", cur_client->nickname, cur_client->channel->name);
						sockPuts(cur_client->sock, join_end);

						break;
					case 4: // PART, send to parted channel
						for (int i=0; i<recipients.user_count; i++){

							client *cli_i = recipients.users[i]; // potential client being sent message
							int i_sock = cli_i->sock; // socket of client

							
							if (cli_i != NULL){ // if active, send message
								sockPuts(i_sock, ":");
								sockPuts(i_sock, cur_client->nickname);
								sockPuts(i_sock, "!");
								sockPuts(i_sock, cur_client->username);
								sockPuts(i_sock, "@");
								sockPuts(i_sock, cur_client->hostname);
								sockPuts(i_sock, " QUIT :");
								sockPuts(i_sock, cur_client->nickname);
								sockPuts(i_sock, " The channel lost a valued member");
								sockPuts(i_sock, "\n");
							}
						}

						break;
					case 5: // LIST, send to requesting client

						char list_header[MAX_MSG_LEN];
						snprintf(list_header, sizeof(list_header), ":Lserver 321 %s Channel :Users Name\n", cur_client->nickname);
						sockPuts(cur_client->sock, list_header);

						char list_body[MAX_MSG_LEN];
						snprintf(list_body, sizeof(list_body), ":Lserver 322 %s ", cur_client->nickname);
						sockPuts(cur_client->sock, list_body);
						sockPuts(cur_client->sock, send_message);

						char list_end[MAX_MSG_LEN];
						snprintf(list_end, sizeof(list_end), ":Lserver 323 %s :End of /LIST\n", cur_client->nickname);
						sockPuts(cur_client->sock, list_end);

						break;
					case 6: // PRIVMSG, send to receiving client(s) or receiving channel(s), excluding sending client

						// should be updated for efficency, this loop is for privmsg to individual clients
						for (int i=0; i<recipients.user_count; i++){
							sockPuts(recipients.users[i]->sock, ":");
							sockPuts(recipients.users[i]->sock, cur_client->nickname);
							sockPuts(recipients.users[i]->sock, " PRIVMSG ");
							sockPuts(recipients.users[i]->sock, recipients.users[i]->nickname);
							sockPuts(recipients.users[i]->sock, " :");
							sockPuts(recipients.users[i]->sock, send_message);
							sockPuts(recipients.users[i]->sock, "\n");
						}

						// should be updated for efficency, this loop is for privmsg to channels
						for (int i=0; i<recipients_from_channel.user_count; i++){
							sockPuts(recipients_from_channel.users[i]->sock, ":");
							sockPuts(recipients_from_channel.users[i]->sock, cur_client->nickname);
							sockPuts(recipients_from_channel.users[i]->sock, " PRIVMSG ");
							sockPuts(recipients_from_channel.users[i]->sock, recipients_from_channel.users[i]->channel->name);
							sockPuts(recipients_from_channel.users[i]->sock, " :");
							sockPuts(recipients_from_channel.users[i]->sock, send_message);
							sockPuts(recipients_from_channel.users[i]->sock, "\n");
						}
						
						break;
					case 7: // WHO, send to requesting client

						char who_message_intro[MAX_MSG_LEN];
						snprintf(who_message_intro, sizeof(who_message_intro), ":Lserver 352 ");
						sockPuts(cur_client->sock, who_message_intro);

						for (int i=0; i<recipients.user_count; i++){

							client *cli_i = recipients.users[i]; // client whose info we're sending


							char who_message[MAX_MSG_LEN];
							snprintf(who_message, sizeof(who_message),
								"%s %s %s ", cli_i->nickname, cli_i->channel->name, cli_i->username);
							sockPuts(cur_client->sock, who_message);

							// no snprintf cause these might be too long
							sockPuts(cur_client->sock, cli_i->hostname);
							sockPuts(cur_client->sock, " ");
							sockPuts(cur_client->sock, cli_i->servername);
							sockPuts(cur_client->sock, " ");
							sockPuts(cur_client->sock, cli_i->nickname);
							sockPuts(cur_client->sock, " H :0 ");
							sockPuts(cur_client->sock, cli_i->realname);
							sockPuts(cur_client->sock, " ");
						}

					sockPuts(cur_client->sock, "\n");
					char who_end[MAX_MSG_LEN];
					snprintf(who_end, sizeof(who_end),":Lserver 315 %s %s :End of /WHO list\n", cur_client->nickname, cur_client->channel->name);
					sockPuts(cur_client->sock, who_end);
				}
				printf("\nResponded to %s\n", cur_client->nickname); // final server print to confirm dealing complete
			}
	}
}

/* All sockets that are sending data are handled */
void readReadySocks(fd_set *fdset, int listenfd, int *connfdArray) {
		
	/* If a client is trying to connect() to our listening
	 socket, select() will consider that as the socket
	 being 'readable'. Thus, if the listening socket is
	 part of the fd_set, we need to accept a new connection. */
	
	if (FD_ISSET(listenfd, fdset)){
		handleNewConn(listenfd, connfdArray);
	}
	/* Now check connfdArray for available data */
	
	/* Run through our sockets and check to see if anything
	 happened with them, if so 'service' them. */
	
	for (int i = 0; i < 5; i++) {
		if (FD_ISSET(connfdArray[i], fdset))
			dealWithData(i, connfdArray);
	} /* for (all entries in queue) */
}

/**
 * Bootstraps the program.
 */
int main( int argc, char *argv[] ) {


	// handle optional arguments
	extern char *optarg; // argument pointer
	extern int optind;   // argument index
	int ch;
		
	while ((ch = getopt(argc, argv, "d:")) != -1){
		switch (ch) {
		case 'd':
			if (set_debug(optarg)) { exit(0); }
			break;
		default: /* FALLTHROUGH */
			usage();
		}
	}
    
    // skip over any optional arguments
    argc -= optind;
    argv += optind;
  
    // we should be left with exactly one mandatory argument - the port
    if (argc < 1) { usage(); }
  
    // lets read the port number
    char *endptr; // pointer to 1st non-consumed character
    unsigned long port = strtoul(argv[0], &endptr, 10);
  
    if (*endptr != '\0') { // check that we converted all the characters
      eprintf("Error - invalid port input: %s\n", argv[0]);
      return 1;
    }
    
    DPRINTF(DEBUG_INIT, "Simple IRC server listening on port %lu for new users\n", port);

    int listenfd;
    struct sockaddr_in serverAddr; /* Local address */
    unsigned short serverPort = port;       /* Server port */

    /* Create socket for incoming connections */
	if ((listenfd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0){
		perror("socket() failed");
		exit(1);
	}
	
	/* Allow address reuse */
	const int reuseAddr = 1;  /* Used so we can re-bind to our port
                        while a previous connection is still
                        in TIME_WAIT state. */

	if(setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &reuseAddr, sizeof(reuseAddr)) < 0){
		perror("setsockopt() failed");
		exit(1);
	}
	
	/* Make the socket non-blocking */
	setNonBlocking(listenfd);
	
	/* Construct local address structure */
	memset(&serverAddr, 0, sizeof(serverAddr));   /* Zero out structure */
	serverAddr.sin_family = AF_INET;                /* Internet address family */
	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY); /* Any incoming interface */
	serverAddr.sin_port = htons(serverPort);      /* Local port */
	
	/* Bind to the local address */
	if (bind(listenfd, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0){
		perror("bind() failed");
		exit(1);
	}
	
	/* Mark the socket so it will listen for incoming connections */
	if (listen(listenfd, MAXPENDING) < 0){
		perror("listen() failed");
		exit(1);
	}

    struct timeval timeout;  /* Timeout for select */
	int highSock;
	fd_set fdset;
	int connfdArray[MAXPENDING];       /* Socket descriptor for clients */
	memset((char *) &connfdArray, 0, sizeof(connfdArray));


	// fill clients and channels with pointers to empty clients and channels
	for (int i=0; i<MAX_CLIENTS; i++){
		clients[i] = malloc(sizeof(client));
    	memset(clients[i], 0, sizeof(client));
	}
	for (int i=0; i<MAX_CHANNELS; i++){
		channels[i] = malloc(sizeof(channel));
    	memset(channels[i], 0, sizeof(channel));
	}

    while (1) { /* Main server loop - forever */
		buildSelectSet(&fdset, listenfd, &highSock, connfdArray);
  
		timeout.tv_sec = 1;
		timeout.tv_usec = 0;
		
		/* The first argument to select is the highest file
		 descriptor value plus 1. In most cases, you can
		 just pass FD_SETSIZE and you'll be fine. */
		
		/* The second argument to select() is the address of
		 the fd_set that contains sockets we're waiting
		 to be readable (including the listening socket). */
		
		/* The third parameter is an fd_set that you want to
		 know if you can write on -- this example doesn't
		 use it, so it passes 0, or NULL. The fourth parameter
		 is sockets you're waiting for out-of-band data for,
		 which usually, you're not. */
		
		/* The last parameter to select() is a time-out of how
		 long select() should block. If you want to wait forever
		 until something happens on a socket, you'll probably
		 want to pass NULL. */
		
  	    int nReady;       /* Number of sockets ready for reading */
		if((nReady = select(highSock+1, &fdset, (fd_set *) 0,
						   (fd_set *) 0, &timeout)) < 0){
			perror("select() failed");
			exit(1);
		}
		
		/* select() returns the number of sockets that had
		 things going on with them -- i.e. they're readable. */
		
		/* Once select() returns, the original fd_set has been
		 modified so it now reflects the state of why select()
		 woke up. i.e. If file descriptor 4 was originally in
		 the fd_set, and then it became readable, the fd_set
		 contains file descriptor 4 in it. */
		
		if (nReady == 0) { /* Nothing ready to read, just show that we're alive */
			printf(".");
			fflush(stdout);
		} else
			readReadySocks(&fdset, listenfd, connfdArray);
	} /* while(1) */
	
	/* NOT REACHED */
	close(listenfd);

    return 0;
}
