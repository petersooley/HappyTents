#include <iostream>
#include "String.h"
#include "Camper.h"
#include "Tent.h"
using namespace std;
using namespace peter;

int main(void) {

	Camper james("James");
	Camper theo("Theo");
	Camper abby("Abby");
	abby.addPref(james, 4);
	Camper charles("Charles");
	james.addPref(theo, 3);
	james.addPref(abby, 8);
	james.addPref(charles, 10);
	cout << "James prefers Abby this much: " << james.getPref(abby) << endl;

	cout << "James prefers Theo this much: " << james.getPref(theo) << endl;

	Tent t(3);
	t.addCamper(james);
	t.addCamper(theo);
	t.addCamper(abby);
	t.addCamper(charles);
	cout << "Tent is this happy: " << t.getHappiness() << endl;

	return 0;
}
