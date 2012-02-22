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
		void setupTents(string filename, int size);
		
		int search(int camperIndex);

	private:
		Camper * campers;
		int camperCount;
		Tent * tents;
		int tentCount;

};

#endif
