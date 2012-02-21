#ifndef _CAMPER_H__
#define _CAMPER_H__

#include <string>
#include <iostream>
#include <stdlib.h>

using namespace std;

class PrefNode;

class Camper {
	public:

		// Constructor -- do not use
		Camper();

		// Constructor
		Camper(string name);
		
		// Destructor
		~Camper();

		// Get this camper's preference of the given camper 
		int getPref(Camper& mateName);

		// Add a preference to this camper. Duplicates
		// are not checked.
		// No significant return value, currently.
		int addPref(Camper& mateName, int rating);


	private:
		string name;
		PrefNode * head;
};

class PrefNode {
	public:
		Camper * mate;
		int rating;
		PrefNode * next;
};



#endif
