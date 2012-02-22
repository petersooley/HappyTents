#include <iostream>
#include <string>
#include <fstream>
#include "Camper.h"
#include "Tent.h"
#include "MakeHappy.h"
using namespace std;


MakeHappy::MakeHappy() : campers(NULL),camperCount(0),tents(NULL),tentCount(0){
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
			campers[camperCount++].set(camper);
		} 
		previous = camper;
	}

	prefs.close();
	prefs.open(filename.c_str());
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
	prefs.close();
}

void MakeHappy::setupTents(string filename, int size) {
	tents = new Tent[size];

	ifstream tentList(filename.c_str());
	string cap;
	int capacity = 0;
	tentCount = 0;

	while(tentList.good()) {
		tentList >> cap;
		capacity = atoi(cap.c_str());

		tents[tentCount++].set(capacity, tentCount);
	}
	tentList.close();

}

int MakeHappy::search(int camperIndex) {
	if(camperIndex == camperCount)
		return 0;
	campers[camperIndex++].print();
	cout << endl;
	return search(camperIndex);
}

int main(void) {

	MakeHappy mh;
	int max_campers = 16;
	int max_tents = 5;
	mh.setupPrefsTable("prefTable.txt", max_campers);
	mh.setupTents("tentList.txt", max_tents);
	//mh.search(0);



	cout << "done." << endl;
	return 0;
}
