#ifndef _TENT_H__
#define _TENT_H__

#include "Camper.h"
#include <iostream>

class Tent {
	public:
		// Constructor -- do not use
		Tent();

		// Constructor
		Tent(int limit);

		// Destructor
		~Tent();

		// Add a camper, -1 if tent is full
		int addCamper(Camper& c);

		// 1 if full, 0 if room still available
		int isFull();

		// Total happiness of all campers
		int getHappiness();

	private:
		int capacity;
		int camperCount;
		Camper * campers;
	
};

#endif
