#include "Range.h"
#include <iostream>
using namespace std;

Range::Range() : head(NULL), count(0) {
}

Range::~Range() {
	RangeNode * grave;
	while(head) {
		grave = head;
		head = head->next;
		delete grave;
	}
}

Range::Range(const unsigned int size) : head(NULL), count(0){
	head = new RangeNode();
	head->data = size - 1;
	head->next = NULL;
	RangeNode * temp;
	++count;
	for(int i = size - 2; i >= 0; --i) {
		temp = head;
		head = new RangeNode();
		head->data = i;
		head->next = temp;
		++count;
	}
}

int * Range::createArray(int& size) {
	size = count;
	int * array = new int[count];
	RangeNode * current = head;
	int i = 0;
	while(current) {
		if(!current->setAside) {
			array[i++] = current->data;
		}
		current = current->next;
	}
	return array;
}

void Range::freeArray(int * array) {
	delete [] array;
}

void Range::setAside(const int nums[], const int length) {
	if(!head)
		return;

	RangeNode * current = head;
	while(current) {
		for(int j = 0; j < length; ++j) {
			if(current->data == nums[j]) {
				current->setAside = 1;
				--count;
				break;
			}
		}
		current = current->next;
	}
}

void Range::replace() {
	RangeNode * current = head;
	while(current) {
		if(current->setAside) {
			current->setAside = 0;
			++count;
		}
		current = current->next;
	}

}
