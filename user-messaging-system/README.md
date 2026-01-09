# CMPU-375 (Fall 2025) Project #1 - Simple IRC server

* THE DESIGN

Our additions to the server design are largely called in dealWithData() in sircs.c. dealWithData() calls handle_line() from sirc_proto.c, which handles different commands that clients can enter. After handle_line() is called, which creates part of the appropriate response messages, dealWithData() sends the appropriate messages to the appropriate recipients.

Our server is capable of receiving and transmitting messages between clients. By using the server, clients can nickname themselves, register themselves, join and part channels, quit the server, send messages to channels or clients, get information about channels, and exit.

Given a client X that has joined the server, X can enter the following commands:
- NICK <nickname>: sets X's nickname if it is not in use.
- USER <username> <hostname> <realname>: sets X's username and realname.
- QUIT <optional quit message>: PARTs X's channel, sending a custom or default message to their channel, and leaves the server, ending the connection to X.
- JOIN <channelname>: PARTs X's current channel if on one and joins a channel or creates a new channel if that channel name is not in use, sending a join message to clients on the joined channel and sending WHO info to X.
- PART <channelname>: leaves X's current channel while sending a default quit message to their channel for any channel(s) that X wants to leave, removing a channel if it is empty of clients.
- LIST: sends X a list of all channels on the server and their user counts.
- PRIVMSG <client nickname(s seperated by ,) or channel name(s seperated by ,)> <message>: messages client(s) or channel(s).
- WHO <channelname>: sends X a list of the clients of a channel to the client.

When X has successfully used USER and NICK for the first time, they will be sent the Message of the Day.

If X uses commands improperly or a request cannot be fulfilled, X will receive a relevant error message.

If X leaves the server without using QUIT, they will be properly removed.

* TESTING

While making the IRC server, we tested the server by interacting with and observing the server and clients.

After much of the functionality was created, we tried to match the exact syntax of the given tests while further improving our server. After all of the given tests passed, we created our own tests for more specific cases.


* POTENTIAL IMPROVEMENTS

If a client tries to join the channel they are already on, there is a segmentation fault and the server ends.
