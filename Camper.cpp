#include "Camper.h"
using namespace std;
using namespace peter;

Camper::Camper() {
}

Camper::Camper(String new_name): head(NULL) {
	name = new_name;
}

Camper::~Camper() {
	PrefNode * grave;
	while(head) {
		grave = head;	
		head = head->next;
		delete grave;
	}
}

int Camper::getPref(Camper& mate) {
	PrefNode * current = head;
	while(current) {
		if(current->mate->name == mate.name) 
			return current->rating;
		current = current->next;
	}
	// no preference is just plain 0
	return 0;
}

int Camper::addPref(Camper& mate, int rating) {
	if(!rating)
		return 0;
	PrefNode * temp = head;
	head = new PrefNode();
	head->mate = &mate;
	head->rating = rating;
	head->next = temp;
	return 0;
}
