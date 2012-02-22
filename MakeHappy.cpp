#include <iostream>
#include <string>
#include <fstream>
#include "Camper.h"
#include "Tent.h"
#include "MakeHappy.h"
using namespace std;


MakeHappy::MakeHappy() : campers(NULL),camperCount(0),tents(NULL){
}

MakeHappy::~MakeHappy() {
	delete [] campers;
	delete [] tents;
}


void MakeHappy::setupPrefsTable(const string filename, int size) {
	campers = new Camper[size];
	ifstream prefs(filename.c_str());
	string camper;
	string mate;
	string pref;
	int rating;

	// First, create all the campers
	camperCount = 0;
	string previous = "";
	while(prefs.good()) {
		prefs >> camper;
		prefs >> mate; // ignored for now
		prefs >> pref; // ignored for now
		if(camper != previous) {
			cout << "adding " << camper << endl;
			campers[camperCount++] = Camper(camper);
		} 
		previous = camper;
	}

	// Second, set up the ratings
	while(prefs.good()) {
		prefs >> camper;
		prefs >> mate;
		prefs >> pref;
		rating = atoi(pref.c_str());

		for(int i = 0; i < camperCount; ++i) {
			if(campers[i].isName(camper)) {
				for(int j = 0; j < camperCount; ++j) {
					if(campers[j].isName(mate)) {
						campers[i].addPref(campers[j], rating);
					}
				}
			}
		}
	}

}

int main(void) {

	MakeHappy mh;
	int max_campers = 16;
	mh.setupPrefsTable("prefTable.txt", max_campers);

	return 0;
}
