#include "Tent.h"
#include <iostream>
#include <stdlib.h>

Tent::Tent() {
	std::cerr << "Oops, default Tent constructor not supported.\n";
	exit(1);
}

Tent::Tent(int limit) : capacity(0),camperCount(0) {
	capacity = limit;
	campers = new Camper[capacity];	
}

Tent::~Tent() {
	for(int i = 0; i < capacity; ++i)
		delete campers[i];
}

int Tent::addCamper(Camper& c) {
	if(camperCount == capacity)
		return -1;
	campers[camperCount++] = &c;
}

int Tent::isFull() {
	if(camperCount == capacity)
		return 1;
	return 0;
}

int Tent::getHappiness() {
	int happy = 0;
	
	// TODO !!!!
}
