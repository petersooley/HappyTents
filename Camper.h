#ifndef _CAMPER_H__
#define _CAMPER_H__

#include <string>
#include <iostream>
#include <stdlib.h>

using namespace std;

class PrefNode;

class Camper {
	public:

		// Constructor
		Camper();

		// shallow copy constructor
		Camper(string name);

		// deep copy constructor
		Camper(Camper& copy);

		// deep copy operator
		Camper& operator = (const Camper& right);

		// Destructor
		~Camper();

		void set(string name);

		// Get this camper's preference of the given camper 
		int getPref(Camper& mateName);

		// 1 if match, 0 if not
		int isName(string name);

		// Add a preference to this camper. Duplicates
		// are not checked.
		// No significant return value, currently.
		int addPref(Camper& mateName, int rating);

		void print();

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
