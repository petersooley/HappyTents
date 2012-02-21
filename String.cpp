/**
 * @file String.cpp
 * @author Peter Soots
 * @date April 24, 2011
 */

#include "String.h"

namespace peter {

String::String(): str(null), len(0) {}

String::~String() {
	delete [] str;
}
String::String(const char * source) {
	len = strlen(source);
	str = new char[len + 1];
	strcpy(str, source);
}

String::String(const String & x) : len(x.len){
	str = new char[x.len +1];
	strcpy(str, x.str);
}

String & String::operator = (const String & x) {
	if(this == &x) return *this;
	delete [] str;
	len = x.len;
	str = new char[len+1];
	strcpy(str, x.str);
	return *this;
}

String & String::operator = (const char * x) {
	delete [] str;
	len = strlen(x);
	str = new char[len+1];
	strcpy(str, x);
	return *this;
}

std::istream & operator >> (std::istream & in, String & x) {
	char buf[200];
	in >> buf;
	x.len = strlen(buf);
	x.str = new char[x.len +1];
	strcpy(x.str, buf);
	return in;
}
std::ostream & operator << (std::ostream & out, const String & x) {
	out << x.str;
	return out;
}
std::ifstream & operator >> (std::ifstream & in, String & x) {
	char buf[200];
	in >> buf;
	x.len = strlen(buf);
	x.str = new char[x.len +1];
	strcpy(x.str, buf);
	return in;
}
std::ofstream & operator << (std::ofstream & out, const String & x) {
	out << x.str;
	return out;
}

String operator + (const String & x, char * y) {
	char * buf = new char[x.len +strlen(y)+1];
	strcpy(buf, x.str);
	strcat(buf, y);
	return String(buf);
}
String operator + (char * y, const String & x) {
	char * buf = new char[x.len +strlen(y)+1];
	strcpy(buf, y);
	strcat(buf, x.str);
	return String(buf);
}
String operator + (const String & x, const String & y) {
	char * buf = new char[x.len +y.len+1];
	strcpy(buf, x.str);
	strcat(buf, y.str);
	return String(buf);
}
String & String::operator += (const String & x) {
	len += x.len;
	char * buf = new char[len+1];
	strcpy(buf, str);
	strcat(buf, x.str);
	str = buf;
	return *this;
}
String & String::operator += (const char * x) {
	len += strlen(x);
	char * buf = new char[len+1];
	strcpy(buf, str);
	strcat(buf, x);
	str = buf;
	return *this;
}
bool operator < (const String & l, char * r) {
	return strcmp(l.str, r) < 0;
}
bool operator < (char * l, const String & r){
	return strcmp(l, r.str) < 0;
}
bool operator < (const String & l, const String & r){
	return strcmp(l.str, r.str) < 0;
}
bool operator <= (const String & l, char * r){
	return strcmp(l.str, r) <= 0;
}
bool operator <= (char * l, const String & r){
	return strcmp(l, r.str) <= 0;
}
bool operator <= (const String & l,const String & r){
	return strcmp(l.str, r.str) <= 0;
}
bool operator > (const String & l, char * r){
	return strcmp(l.str, r) > 0;
}
bool operator > (char * l, const String & r){
	return strcmp(l, r.str) > 0;
}
bool operator > (const String & l, const String & r){
	return strcmp(l.str, r.str) > 0;
}
bool operator >= (const String & l, char * r){
	return strcmp(l.str, r) >= 0;
}
bool operator >= (char * l, const String & r){
	return strcmp(l, r.str) >= 0;
}
bool operator >= (const String & l,const String & r){
	return strcmp(l.str, r.str) >= 0;
}
bool operator != (const String & l, char * r){
	return strcmp(l.str, r) != 0;
}
bool operator != (char * l, const String & r){
	return strcmp(l, r.str) != 0;
}
bool operator != (const String & l,const String & r){
	return strcmp(l.str, r.str) != 0;
}
bool operator == (const String & l, char * r){
	return strcmp(l.str, r) == 0;
}
bool operator == (char * l, const String & r){
	return strcmp(l, r.str) == 0;
}
bool operator == (const String & l,const String & r){
	return strcmp(l.str, r.str) == 0;
}
char & String::operator [] (int index) const {
	return str[index];
}
int String::length() const {
	return len;
}
String & String::toupper() {
	for(int x = 0; x <= len; ++x) {
		str[x] = std::toupper(str[x]);
	}
	return *this;
}

} // end namespace peter










