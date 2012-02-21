#include <iostream>
#include "String.h"
#include "Camper.h"
using namespace std;
using namespace peter;

int main(void) {

	Camper james("James");
	Camper theo("Theo");
	Camper abby("Abby");
	Camper charles("Charles");
	james.addPref(theo, 3);
	james.addPref(abby, 8);
	james.addPref(charles, 0);
	cout << "James prefers Abby this much: " << james.getPref(abby) << endl;

	cout << "James prefers Theo this much: " << james.getPref(theo) << endl;


	return 0;
}
