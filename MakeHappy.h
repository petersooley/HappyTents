#ifndef _MAKEHAPPY_H__
#define _MAKEHAPPY_H__
#include "Camper.h"
#include "Tent.h"
#include "Range.h"
#include <string>
using namespace std;

class MakeHappy {
	public:
		MakeHappy();
		~MakeHappy();
		void setupPrefsTable(string filename, int size);
		void setupTents(string filename, int size);
		void doSearch();
		// to be deleted...
		void testCombinations();

	private:
		Camper * campers;
		int camperCount;
		Tent * tents;
		int tentCount;
		static int combinationsPossible(const int n, const int r);
		static int** combinations(const int n[], const int n_length, const int r, int& size);
		static void freeCombinations(int** combos, const int length);
		int search(Range& range, int tentIndex, int curHappiness);

};

#endif
