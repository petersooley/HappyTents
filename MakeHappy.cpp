#include <iostream>
#include "String.h"
#include "Camper.h"
#include "Tent.h"
using namespace std;
using namespace peter;

int main(void) {

	ifstream prefs("prefTable.txt");
	string line;

	while(prefs.good()) {
		getline(prefs, line);
		cout << line << endl;
	}

	return 0;
}
