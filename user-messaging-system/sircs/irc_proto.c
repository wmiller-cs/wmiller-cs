#include "irc_proto.h"
#include "debug.h"

#include <string.h>
#include <stdlib.h>
#include <stdarg.h>
#include <unistd.h>
#include <fcntl.h>
#include "sircs.h"
#include <ctype.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

/* You'll want to define the CMD_ARGS to match up with how you
 * keep track of clients.  Probably add a few args...
 * The command handler functions will look like
 * void cmd_nick(CMD_ARGS)
 * e.g., void cmd_nick(your_client_thingy *c, char *prefix, ...)
 * or however you set it up.
 */

#define CMD_ARGS client *c, char *prefix, char **params, int n_params
typedef void (*cmd_handler_t)(CMD_ARGS);
#define COMMAND(cmd_name) void cmd_name(CMD_ARGS)

struct dispatch {
    char cmd[MAX_COMMAND];
    int needreg; /* Must the user be registered to issue this cmd? */
    int minparams; /* send NEEDMOREPARAMS if < this many params */
    cmd_handler_t handler;
};

#define NELMS(array) (sizeof(array) / sizeof(array[0]))

/* Define the command handlers here.  This is just a quick macro
 * to make it easy to set things up */
COMMAND(cmd_nick);
COMMAND(cmd_user);
COMMAND(cmd_quit);

COMMAND(cmd_join);
COMMAND(cmd_part);
COMMAND(cmd_list);

COMMAND(cmd_privmsg);
COMMAND(cmd_who);

/* Fill in the blanks */

/* Dispatch table.  "reg" means "user must be registered in order
 * to call this function".  "#param" is the # of parameters that
 * the command requires.  It may take more optional parameters.
 */
struct dispatch cmds[] = {
    /* cmd     reg  #parm  function */
    { "NICK",    0, 1, cmd_nick },
    { "USER",    0, 3, cmd_user },
    { "QUIT",    0, 0, cmd_quit },
    
	{ "JOIN",    1, 1, cmd_join },
    { "PART",    1, 1, cmd_part },
    { "LIST",    1, 0, cmd_list },

	{ "PRIVMSG", 1, 2, cmd_privmsg },
	{ "WHO",     1, 1, cmd_who },
    /* Fill in the blanks... */
};

void throw_error(err_t error){
	to_error = 1; // there is an error to communicate to client

	switch (error){
		case ERR_NICKNAMETOOLONG:
			strcpy(error_message, "error: Nickname is more than 9 characters long."); // error message to be printed
			break;
		case ERR_NICKNAMEINUSE:
			strcpy(error_message, "error: Nickname already in use.");
			break;
		case ERR_CHANNELFULL:
			strcpy(error_message, "error: The requested channel is already full.");
			break;
		case ERR_NOSUCHCLIENT:
			strcpy(error_message, "error: No client with those attributes.");
			break;
		case ERR_UNKNOWNCOMMAND:
			strcpy(error_message, "error: Unknown command.");
			break;
		case ERR_NOTREGISTERED:
			strcpy(error_message, "error: Unregistered user.");
			break;
		case ERR_NEEDMOREPARAMS:
			strcpy(error_message, "error: This command needs more parameters.");
			break;
		case ERR_INVALID:
			strcpy(error_message, "error: Invalid execution."); // currently not in-use
			break;
		case ERR_NOSUCHNICK:
			strcpy(error_message, "error: Nickname does not exist.");
			break;
		case ERR_NOCHANNELS:
			strcpy(error_message, "error: No channels.");
			break;
		case ERR_NOSUCHCHANNEL:
			strcpy(error_message, "error: Channel does not exist.");
			break;
		case ERR_NORECIPIENT:
			strcpy(error_message, "error: No recipient found.");
			break;
		case ERR_NOTEXTTOSEND:
			strcpy(error_message, "error: No text found to send.");
			break;
		case ERR_NONICKNAMEGIVEN:
			strcpy(error_message, "error: Potential nickname is not allowed.");
			break;
		case ERR_ERRONEOUSNICKNAME:
			strcpy(error_message, "error: Nickname contains characters not allowed.");
			break;
		case ERR_NOTONCHANNEL:
			strcpy(error_message, "error: User is not in channel.");
			break;
		case ERR_NOLOGIN:
			strcpy(error_message, "error: User is not logged in yet.");
			break;
		case ERR_ALREADYREGISTRED:
			strcpy(error_message, "error: User is already registered.");
			break;
		case ERR_NOSUCHRECIP:
			strcpy(error_message, "error: Not a valid recipient.");
			break;
	}
}

/* Handle a command line.  NOTE:  You will probably want to
 * modify the way this function is called to pass in a client
 * pointer or a table pointer or something of that nature
 * so you know who to dispatch on...
 * Mostly, this is here to do the parsing and dispatching
 * for you
 *
 * This function takes a single line of text.  You MUST have
 * ensured that it's a complete line (i.e., don't just pass
 * it the result of calling read()).  
 * Strip the trailing newline off before calling this function.
 */
void handle_line(client *c, char *line){
	
	m_type = -1; // send message to current channel by default

	// clear the send messages
	send_message[0] = '\0'; 
	send_message2[0] = '\0';
	send_message3[0] = '\0';

	// clear the receiving channels
	recipients = (channel){0}; 
	recipients_from_channel = (channel){0};

	// clear error mechanics
	to_error = 0; 
	error_message[0] = '\0'; 
	

  char *prefix = NULL, *command, *pstart, *params[MAX_MSG_TOKENS];
  int n_params = 0;
	char *trailing = NULL;

  DPRINTF(DEBUG_INPUT, "Handling line: %s\n", line);
  command = line;
  if (*line == ':') {
		prefix = ++line;
		command = strchr(prefix, ' ');
  }
  if (!command || *command == '\0') {
		/* Send an unknown command error! */
    throw_error(ERR_UNKNOWNCOMMAND);
		return;
  }
  while (*command == ' ') {
		*command++ = 0;
  }
  if (*command == '\0') {
    /* Send an unknown command error! */
		throw_error(ERR_UNKNOWNCOMMAND);
		return;
  }
    pstart = strchr(command, ' ');
	if (pstart) {
		while (*pstart == ' ') {
			*pstart++ = '\0';
		}
		if (*pstart == ':') {
			trailing = pstart;
		} else {
			trailing = strstr(pstart, " :");
		}
		if (trailing) {
			while (*trailing == ' ')
			*trailing++ = 0;
			if (*trailing == ':')
			*trailing++ = 0;
		}
		
		do {
			if (*pstart != '\0') {
			params[n_params++] = pstart;
			} else {
			break;
			}
			pstart = strchr(pstart, ' ');
			if (pstart) {
				while (*pstart == ' ') {
					*pstart++ = '\0';
				}
			}
		} while (pstart != NULL && n_params < MAX_MSG_TOKENS);
  }

  if (trailing && n_params < MAX_MSG_TOKENS) {
		params[n_params++] = trailing;
  }
    
  DPRINTF(DEBUG_INPUT, "Prefix:  %s\nCommand: %s\nParams (%d):\n", prefix ? prefix : "<none>", command, n_params);
  int i;
  for (i = 0; i < n_params; i++) {
		DPRINTF(DEBUG_INPUT, "   %s\n", params[i]);
  }
  DPRINTF(DEBUG_INPUT, "\n");

  for (i = 0; i < NELMS(cmds); i++) {
		if (!strcasecmp(cmds[i].cmd, command)) {
			 // tests if command needs a reg. user and if it does, checking if user has sucessfuly sent NICK and USER
			if (cmds[i].needreg  && !((c->has_nicked) + (c->has_userd) == 2)) {
				throw_error(ERR_NOTREGISTERED);
				return;
			/* ERROR - the client is not registered and they need
				* to be in order to use this command! */
			} else if (n_params < cmds[i].minparams) {
			/* ERROR - the client didn't specify enough parameters
				* for this command! */
				throw_error(ERR_NEEDMOREPARAMS);
				return;
			} else {
			/* Here's the call to the cmd_foo handler... modify
				* to send it the right params per your program
				* structure. */

			m_type = i;

			(*cmds[i].handler)(c, prefix, params, n_params); 
			}
			break;
		}
	}
	
  if (i == NELMS(cmds)) {
		// ERROR - unknown command!
    throw_error(ERR_UNKNOWNCOMMAND);
  }
}


// command handler helper functions
int nick_exists(char nick[]){
	// returns 1 if a nickname is already in use, and 0 if not

	for (int i=0; i<MAX_CLIENTS; i++){
		if (!strcmp(clients[i]->nickname, nick)){
			return 1;
		}
	}
	return 0;
}

void part_helper(client *c){
	// leave current channel

	recipients = *c->channel;
	channel *cur_channel = c->channel;

	for (int j=0; j<cur_channel->user_count; j++){
		if (!strcmp(cur_channel->users[j]->nickname, c->nickname)){ // if user at index
			
			cur_channel->users[j] = &(client){0}; // set that user address to aim at a blank user
			cur_channel->user_count--; // update the user count
			
			*c->channel = (channel){0}; // set the client's channel to a blank channel
			
			break;
		}
	}
	
	if (cur_channel->user_count==0){ // if the parted channel is empty of users, set the channel to be a blank channel
		cur_channel = &(channel){0};
	}
}

void who_helper(char channame[], char message[]){
	for (int i=0; i<MAX_CHANNELS; i++){ // search for the right channel name
		channel *cur_channel = channels[i];
		if(!strcmp(channame, cur_channel->name)){
			// print the right chnannel
			strcat(message, cur_channel->name);
			strcat(message, ":");


			
			for (int j=0; j<cur_channel->user_count; j++){
				client *cur_user = cur_channel->users[j];
				// print each real user in that channel
				if (strlen(cur_user->nickname)){
					strcat(message, " ");
					strcat(message, cur_user->nickname); 
				}
			}
			return;
		}
	}
	throw_error(ERR_NOSUCHCHANNEL); // if here, the requested channel doesn't exist
}


/* Command handlers */
/* MODIFY to take the arguments you specified above! */
void cmd_nick(client *c, char *prefix, char **params, int n_params){
	// set your unique nickname

	if (strlen(params[0])>9){ // reject if too long
		throw_error(ERR_NICKNAMETOOLONG);
		return;
	}
	
	if (nick_exists(params[0])){ // reject if already exists
		throw_error(ERR_NICKNAMEINUSE);
		return;
	}

	if (isdigit(params[0][0])) { // reject if begins with number
		throw_error(ERR_ERRONEOUSNICKNAME);
		return;
	}

	strcpy(c->nickname, params[0]);

	
	strcat(send_message, c->nickname);
	strcat(send_message, " is now nicknamed ");
	strcat(send_message, c->nickname);
	strcat(send_message, ".");

	c->has_nicked = 1;
	if (c->has_userd && !c->motd){
		c->motd = 1;
	}
}

void cmd_user(client *c, char *prefix, char **params, int n_params){
	// set your username, hostname, and realname
	/*
	params[0] is the username of the client
	params[1] is their hostname
	params[2] is their server name
	params[3] is their real name
	*/

	strcpy(c->username, params[0]);
	// hostname already set, when connection first handled
	strcpy(c->servername, params[2]);
	
	if (n_params > 3) {
		strcpy(c->realname, params[3]);
	}


	c->has_userd = 1;
	if (c->has_nicked && !c->motd){
		c->motd = 1;
	}
}

void cmd_quit(client *c, char *prefix, char **params, int n_params){
	// quit the messaging system, which includes leaving current channel

	// send to channel the reason for depature
	if (n_params) {
		// send params[0] as quit message to all in channels
		strcpy(send_message, " :");
		strcat(send_message, params[0]);
	}

	//remove user from list of clients
}

void cmd_join(client *c, char *prefix, char **params, int n_params){	
	// join a channel, leaving current channel if in one

	for (int i=0; i<MAX_CHANNELS; i++) { // search for correct channel to join
		channel *cur_channel = channels[i];
		if (!strcmp(params[0], cur_channel->name)){ // search for open spot in channel's user array
			for (int j=0; j<MAX_CLIENTS; j++){
				
				if (cur_channel->users[j] == NULL || cur_channel->users[j]->sock == 0){ // check if open spot
					if (strlen(c->channel->name)){ // check if on a channel already
						part_helper(c); // leave current channel
					}
					
					strcpy(cur_channel->name, params[0]); // sets the name just in case double joining same channel
					cur_channel->users[j] = c; // put the client into the channel's user array
					cur_channel->user_count++; // update the channel's user count
					c->channel = cur_channel; // update the user's current channel
					
					strcat(send_message2, c->nickname);
					strcat(send_message2, " =channel ");
					strcat(send_message2, c->channel->name);
					strcat(send_message2, ".");
					
					who_helper(c->channel->name, send_message3); // load info of client's new channel into send_message3

					return;
				}
			}
			throw_error(ERR_CHANNELFULL); // if here, the requested channel is full
			return;
		}
	}
	
 	// if here, the requested channel name doesn't exist, so make a new channel with that name
	for (int i=0; i<MAX_CHANNELS; i++){
		if (!strlen(channels[i]->name)){ // check if empty channel
			if (strlen(c->channel->name)){ // check if on a channel already
				part_helper(c); // leave current channel
			}

			channels[i]->users[0] = c; // set first user to this client
			channels[i]->user_count++; // update the user count
			strcpy(channels[i]->name, params[0]);  // set the name of the channel
			
			c->channel = channels[i]; // set clients' channel to this new channel

			strcat(send_message2, c->nickname);
			strcat(send_message2, " joined Channel ");
			strcat(send_message2, c->channel->name);
			strcat(send_message2, ".");

			who_helper(c->channel->name, send_message3); // load info of client's new channel into send_message3

			return;
		}
	}
}

void cmd_part(client *c, char *prefix, char **params, int n_params){
	// leave one of some channels

	for (int i=0; i<n_params; i++){ // check if any of the requested channel names are the current channel name of the user
		if (!strcmp(c->channel->name, params[i])){ // if a channel matches, then drop it
			part_helper(c);
			return;
		}
	}

	throw_error(ERR_NOTONCHANNEL); // if here, then the client isn't on any of the requested channels
}

void cmd_list(client *c, char *prefix, char **params, int n_params){
	// prints out all channels and user counts of those channels

	for (int i = 0; i < MAX_CHANNELS; i++) {
		channel *cur_channel = channels[i];

		if (strlen(cur_channel->name)){ // if correct channel
			// print channel name
			strcat(send_message, cur_channel->name);
			
			char str_count[20];
			sprintf(str_count, ": %d ", cur_channel->user_count);
			strcat(send_message, str_count);
			strcat(send_message, "\n");
			return;
		}
	}

	throw_error(ERR_NOCHANNELS);
}

void cmd_privmsg(client *c, char *prefix, char **params, int n_params){
	// message channel(s) or client(s)
    
	// Clear recipients
    memset(&recipients, 0, sizeof(channel));
		memset(&recipients_from_channel, 0, sizeof(channel));
    recipients.user_count = 0;
		recipients_from_channel.user_count = 0;

    
    char targets_copy[MAX_MSG_LEN];
    strcpy(targets_copy, params[0]);
    
    char *token = strtok(targets_copy, ",");
    
    while (token != NULL) {
        // Remove leading whitespace
        while (*token == ' ') token++;
        
        int found = 0;
        
        // If we haven't determined the type yet, check both
        
        // Try to find as a client
            for (int i = 0; i < MAX_CLIENTS; i++) {
                client *cur_client = clients[i];
                
                // NULL checks!
                if (cur_client == NULL) {
                    continue;
                }
                
                if (!strcmp(token, cur_client->nickname)) {
                    // Found matching client
                    if (recipients.user_count < MAX_CLIENTS) {
                        recipients.users[recipients.user_count] = cur_client;
                        recipients.user_count++;
                    }
                    found = 1;
                    break;
                }
            }
        
        
        // If not found as client, try as channel
        if (!found) {
            for (int j = 0; j < MAX_CHANNELS; j++) {
                channel *cur_channel = channels[j];
                
                // Check if channel exists
                if (cur_channel == NULL || !strlen(cur_channel->name)) {
                    continue;
                }
                
                if (!strcmp(token, cur_channel->name)) {
                    // Found matching channel - add all its users
                    for (int k = 0; k < MAX_CLIENTS; k++) {
                        if (cur_channel->users[k] != NULL && cur_channel->users[k]->nickname != c->nickname && recipients_from_channel.user_count < MAX_CLIENTS) {
                            
                            recipients_from_channel.users[recipients_from_channel.user_count] = cur_channel->users[k];
                            recipients_from_channel.user_count++;
														found = 1;
                        }
                    }
                    break;
                }
            }
        }
        
        if (!found) {
          throw_error(ERR_NOSUCHRECIP);
        }
        
        // IMPORTANT: Get next token!
        token = strtok(NULL, ",");
    }
    
    // Check if we found any recipients
    if (recipients.user_count == 0 && recipients_from_channel.user_count == 0) {
        throw_error(ERR_NORECIPIENT);
        return;
    }
    
    // Set the message
    strcpy(send_message, params[n_params-1]);
}


// UPDATE
void cmd_who(client *c, char *prefix, char **params, int n_params){
	// print a list of users on a channel

	memset(&recipients, 0, sizeof(channel));

	for (int i=0; i<MAX_CHANNELS; i++){ // search for the right channel name
		channel *cur_channel = channels[i];
		if(!strcmp(params[0], cur_channel->name)){
			
			for (int j=0; j<cur_channel->user_count; j++){
				client *cur_user = cur_channel->users[j];
				// print each real user in that channel
				if (strlen(cur_user->nickname)){
					recipients.users[recipients.user_count] = cur_user;
          recipients.user_count++;
				}
			}
			return;
		}
	}
	throw_error(ERR_NOSUCHCHANNEL); // if here, the requested channel doesn't exist

	return;

}

