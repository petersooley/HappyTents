#ifndef _MAKEHAPPY_H__
#define _MAKEHAPPY_H__
#include "Camper.h"
#include "Tent.h"
#include <string>
using namespace std;

class MakeHappy {
	public:
		MakeHappy();
		~MakeHappy();

		void setupPrefsTable(string filename, int size);
		
	private:
		Camper * campers;
		int camperCount;
		Tent * tents;

};

#endif
