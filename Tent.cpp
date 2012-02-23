#include "Tent.h"
#include <iostream>
#include <stdlib.h>

Tent::Tent() : capacity(0), camperCount(0), ID(0) {
}

Tent::~Tent() {
	delete [] campers;
}

void Tent::set(int limit, int id) {
	capacity = limit;
	ID = id;
	campers = new Camper * [capacity];
}


int Tent::addCamper(Camper& c) {
	if(camperCount == capacity)
		return -1;
	campers[camperCount++] = &c;
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
				happy += campers[i]->getPref(*campers[j]);
	return happy;
}


void Tent::print() {
	cout << "Tent " << ID << " -> ";
	for(int i = 0; i < camperCount; ++i) {
		campers[i]->print();
		cout << " ";
	}
	cout << endl;
}

int Tent::getCapacity() {
	return capacity;
}

