#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <string>
#include "Camper.h"
#include "Tent.h"
#include "MakeHappy.h"
#include "Range.h"
using namespace std;


MakeHappy::MakeHappy() : campers(NULL),camperCount(0),tents(NULL),tentCount(0){
}

MakeHappy::~MakeHappy() {
	delete [] campers;
	delete [] tents;
}


int MakeHappy::setupPrefsTable(const string filename, int size) {
	campers = new Camper[size];
	ifstream prefs(filename.c_str());
	if(prefs.fail()) {
		cerr << "Oops, could not open " << filename << endl;
		return 0;
	}
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
	return 1;
}

int MakeHappy::setupTents(string filename, int size) {
	tents = new Tent[size];

	ifstream tentList(filename.c_str());
	if(tentList.fail()) {
		cerr << "Oops, could not open " << filename << endl;
		return 0;
	}
	string cap;
	int capacity = 0;
	tentCount = 0;

	while(tentList.good()) {
		tentList >> cap;
		capacity = atoi(cap.c_str());

		tents[tentCount++].set(capacity, tentCount);
	}
	tentList.close();
	return 1;
}

static unsigned int stateCount = 0;

int MakeHappy::search(Range& range, int tentIndex, int curHappiness) {
	// return the happiness once we hit the bottom of the tree
	if(tentIndex == tentCount) {
		return curHappiness;
	}

	int ** combos;
	int comboCount;
	int capacity;
	int newHappiness;
	int happiness = 0;
	int maxHappiness = 0;

	// grab the current tent
	Tent * t = &tents[tentIndex++];
	capacity = t->getCapacity();

	// figure out all possible combinations of campers in this tent
	int rangeSize;
	int * array = range.createArray(rangeSize);
	combos = combinations(array, rangeSize, capacity, comboCount);

	// fill this tent with each combo and calculate how much
	// happiness we can get.
	for(int i = 0; i < comboCount; ++i) {

		// for your viewing pleasure...
		if(++stateCount % 500000 == 0) {
			cout << ".";
			cout.flush();
		}

		// Prepare the search
		for(int j = 0; j < capacity; ++j)
			t->addCamper(campers[combos[i][j]]);
		range.setAside(combos[i], capacity, tentIndex);
		newHappiness = t->getHappiness() + curHappiness;

		// Search...
		happiness = search(range, tentIndex, newHappiness);

		// If this was the best search we've gotten so far, save it.
		if(happiness > maxHappiness) {
			maxHappiness = happiness;

			if(tentIndex == (tentCount - 1))
				saveTents();
		}

		// Clean up from the search, so the next iteration can
		// start fresh
		for(int k = 0; k < capacity; ++k)
			t->removeCamper(campers[combos[i][k]]);
		range.replace(tentIndex);
	}

	range.freeArray(array);
	freeCombinations(combos, comboCount);

	return maxHappiness;
}

int** MakeHappy::combinations(const int n[], const int n_length, const int r, int& size) {
	size = combinationsPossible(n_length, r);

	int ** combos = new int*[size];
	int * index = new int[r];

	// start with normal ordering: 0,1,2,...,r - 1
	for(int a = 0; a < r; ++a)
		index[a] = a;

	for(int b = 0; b < size; ++b) {

		// allocate and assign values
		combos[b] = new int[r];
 		for(int c = 0; c < r; ++c)
			combos[b][c] = n[index[c]];


		// adjust the indices for the next iteration
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

	delete [] index;

	return combos;
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

void MakeHappy::doSearch() {
	Range range(camperCount);
	int happy;
	happy = search(range, 0, 0);
	cout << "Happiness: " << happy << endl;
	cout << finalArrangement;
}


void MakeHappy::saveTents() {
	ostringstream out;
	for(int i = 0; i < tentCount; ++i)
		out << tents[i].toString();
	finalArrangement = out.str();
}


int main(void) {

	MakeHappy mh;
	int max_campers = 16;
	int max_tents = 5;
	if(!mh.setupPrefsTable("prefTable.txt", max_campers))
		return 1;
	if(!mh.setupTents("tentList.txt", max_tents))
		return 1;
	mh.doSearch();



	cout << "done." << endl;
	return 0;
}
