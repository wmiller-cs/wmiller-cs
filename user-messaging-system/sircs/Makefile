# This is just a stub to get you started
# You'll want to compile debug.o into sircs to get
# the debugging functions.
# We suggest adding three targets:  all, clean, test

CC=gcc               # compiler
LD=gcc               # linker
CFLAGS=-Wall -DDEBUG -g # compilation flags
LDFLAGS=             # linking flags
	
all: sircs

sircs: sircs.c sircs.h irc_proto.o debug.o
	$(CC) $(CFLAGS) -c sircs.c
	$(LD) $(LDFLAGS) sircs.o irc_proto.o debug.o -o $@

debug.o: debug.c debug-text.h debug.h
	$(CC) $(CFLAGS) -c debug.c

irc-proto.o: irc_proto.c irc_proto.h sircs.h
	$(CC) $(CFLAGS) -c irc_proto.c

debug-text.h: debug.h
	./dbparse.pl <debug.h> debug-text.h

clean:
	rm -f sircs *.o
