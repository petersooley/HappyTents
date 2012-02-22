#include "Camper.h"
using namespace std;

Camper::Camper(): head(NULL) {
}

Camper::Camper(string newname) {
	name = newname;
}

Camper::Camper(Camper& copy) {
	name = copy.name;
	PrefNode * current = copy.head;
	while(current) {
		addPref(*current->mate, current->rating);
		current = current->next;
	}
}

Camper& Camper::operator = (const Camper& x) {
	if(this == &x)
		return *this;
	PrefNode * grave;
	while(head) {
		grave = head;
		head = head->next;
		delete grave;
	}
	head = NULL;
	PrefNode * current = x.head;
	while(current) {
		addPref(*current->mate, current->rating);
		current = current->next;
	}
	name = x.name;
	return *this;
}

Camper::~Camper() {
	PrefNode * grave;
	while(head) {
		grave = head;	
		head = head->next;
		delete grave;
	}
}

void Camper::set(string newname) {
	name = newname;
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

int Camper::isName(string namecheck) {
	if(name == namecheck)
		return 1;
	return 0;
}

int Camper::addPref(Camper& mate, int rating) {
	if(!rating)
		return 0;
	PrefNode * temp = head;
	head = new PrefNode();
	head->mate = new Camper(mate.name); // shallow copy is fine
	head->rating = rating;
	head->next = temp;
	return 0;
}

void Camper::print() {
	cout << name;
	/*
	cout << ": ";
	PrefNode * current = head;
	while(current) {
		cout << current->mate->name << " ";
		current = current->next;
	}
	cout << endl;
	*/
}
