#include "Tent.h"
#include <iostream>
#include <stdlib.h>

Tent::Tent() {
}

Tent::Tent(int limit) : capacity(0),camperCount(0) {
	capacity = limit;
	campers = new Camper[capacity];	
}

Tent::~Tent() {
	delete [] campers;
}

int Tent::addCamper(Camper& c) {
	if(camperCount == capacity)
		return -1;
	campers[camperCount++] = c;
	return 0;
}

int Tent::isFull() {
	if(camperCount == capacity)
		return 1;
	return 0;
}

int Tent::getHappiness() {
	int happy = 0;
	
	for(int i = 0; i < camperCount; ++i) 
		for(int j = 0; j < camperCount; ++j) 
			if(i != j)
				happy += campers[i].getPref(campers[j]);
	return happy;
}

