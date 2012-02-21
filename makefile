CC = g++
CFLAGS = -g -Wall -c

OBJS = MakeHappy.o String.o Camper.o Tent.o

MakeHappy: $(OBJS)
	$(CC) -o MakeHappy $(OBJS)

MakeHappy.o: MakeHappy.cpp
	$(CC) $(CFLAGS) MakeHappy.cpp  

String.o: String.cpp String.h
	$(CC) $(CFLAGS) String.cpp String.h

Camper.o: Camper.cpp Camper.h
	$(CC) $(CFLAGS) Camper.cpp Camper.h

Tent.o: Tent.cpp Tent.h
	$(CC) $(CFLAGS) Tent.cpp Tent.h

clean:
	rm -f MakeHappy *.o
