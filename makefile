CC = g++
CFLAGS = -g -Wall -c -O2 

OBJS = MakeHappy.o Camper.o Tent.o Range.o

MakeHappy: $(OBJS)
	$(CC) -o MakeHappy $(OBJS)

MakeHappy.o: MakeHappy.cpp
	$(CC) $(CFLAGS) MakeHappy.cpp MakeHappy.h

Camper.o: Camper.cpp 
	$(CC) $(CFLAGS) Camper.cpp Camper.h

Tent.o: Tent.cpp 
	$(CC) $(CFLAGS) Tent.cpp Tent.h

Range.o: Range.cpp
	$(CC) $(CFLAGS) Range.cpp Range.h

all: MakeHappy

clean:
	rm -f MakeHappy *.o *.gch
