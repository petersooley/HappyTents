#include "Tent.h"
#include <iostream>
#include <stdlib.h>
#include <sstream>
#include <string>
using namespace std;

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

int Tent::removeCamper(Camper& c) {
	for(int i = 0; i < camperCount; ++i){
		if(campers[i] == &c) {
			for(int j = i; j < camperCount - 1; ++j) {
				campers[j] = campers[j+1];
			}
		}
	}
	--camperCount;
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


string Tent::toString() {
	ostringstream out;
	out << "Tent " << ID << " -> ";
	for(int i = 0; i < camperCount; ++i)
		out << campers[i]->toString() << " ";
	out << "\n";
	return out.str();
}

int Tent::getCapacity() {
	return capacity;
}

