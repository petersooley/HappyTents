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

int** MakeHappy::combinations(const int n[], const int n_length, const int r, int& size) {
	size = combinationsPossible(n_length, r);

	int ** combos = new int*[size];
	int * index = new int[r];

	// start with normal ordering: 0,1,2,...,r - 1
	for(int a = 0; a < r; ++a)
		index[a] = a;

	for(int b = 0; b < size; ++b) {

		combos[b] = new int[r];
 		for(int c = 0; c < r; ++c)
			combos[b][c] = n[index[c]];


		// adjust the indices
		for(int d = r - 1; d >= 0; --d) {

			// If the current index has reached the end or hit our upper neighbor index...
			if((index[d] + 1) == n_length || ((d != r - 1) && (index[d + 1] == index[d] + 1))) {

				// If this is the last index to move, then we're  done.
				if (d == 0)
					break;

				// If our lower neighbor index is already next to us, then do nothing
				if ((index[d - 1] + 1) == index[d])
					continue;

				// Move all neighbor indices next to lower neighbor index.
				int newspot = index[d - 1] + 2;
				for(int e = d; e < r; ++e) {
					index[e] = newspot++;
				}
				++index[d - 1];
				break;

			}

			++index[d];
			break;
		}
	}

	return combos;
}

void MakeHappy::testCombinations() {
	int ** combos;
	int size;
	int range[camperCount];
	for(int i = 0; i < camperCount; ++i)
		range[i] = i;
	combos = combinations(range, camperCount, 3, size);
	cout << "got combos" << endl;
	for(int i = 0; i < size; ++i) {
		for(int j = 0; j < 3; ++j) {
			cout << combos[i][j] << " ";
		}
		cout << endl;
	}
	cout << "freeing combos" << endl;
	freeCombinations(combos, size);
}

void MakeHappy::freeCombinations(int** combos, const int length) {
	for(int i = 0; i < length; ++i)
		delete [] combos[i];
	delete [] combos;
}

int MakeHappy::combinationsPossible(const int n, const int r) {
	int m = n - r;
	int o, p;
	if(m > r) {
		o = m;
		p = r;
	}
	else {
		p = m;
		o = r;
	}
	/*     n!
	 *    ----
	 *    o!p!  where o > p
	 */
	int top = 1, bottom = 1;
	for(int i = n; i > o; --i)
		top *= i;
	for(int j = p; j > 1; --j)
		bottom *= j;
	return top / bottom;
}


int main(void) {

	MakeHappy mh;
	int max_campers = 16;
	int max_tents = 5;
	mh.setupPrefsTable("prefTable.txt", max_campers);
	mh.setupTents("tentList.txt", max_tents);
	mh.testCombinations();



	cout << "done." << endl;
	return 0;
}
