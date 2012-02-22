CC = g++
CFLAGS = -g -Wall -c

OBJS = MakeHappy.o Camper.o Tent.o

MakeHappy: $(OBJS)
	$(CC) -o MakeHappy $(OBJS)

MakeHappy.o: MakeHappy.cpp
	$(CC) $(CFLAGS) MakeHappy.cpp MakeHappy.h

Camper.o: Camper.cpp 
	$(CC) $(CFLAGS) Camper.cpp Camper.h

Tent.o: Tent.cpp 
	$(CC) $(CFLAGS) Tent.cpp Tent.h

all: MakeHappy

clean:
	rm -f MakeHappy *.o *.gch
