/**
 * @file String.h
 * @author Peter Soots
 * @date April 24, 2011
 */

#ifndef STRING_H_
#define STRING_H_

#include <iostream>
#include <fstream>
#include <cstring>
#include <cctype>

#ifndef null
#define null 0
#endif

namespace peter {

class String {
public:
	String();
	~String();
	String(const char*);
	String(const String &);
	// operators...
	String & operator = (const String &);
	String & operator = (const char *);
	friend std::istream & operator >> (std::istream &, String &);
	friend std::ostream & operator << (std::ostream &, const String&);
	friend std::ifstream & operator >> (std::ifstream &, String &);
	friend std::ofstream & operator << (std::ofstream &, const String &);
	friend String operator + (const String &, char *);
	friend String operator + (char *, const String &);
	friend String operator + (const String&, const String&);
	String & operator += (const String &);
	String & operator += (const char *);
	friend bool operator < (const String &, char *);
	friend bool operator < (char *, const String &);
	friend bool operator < (const String &, const String &);
	friend bool operator <= (const String &, char *);
	friend bool operator <= (char *, const String &);
	friend bool operator <= (const String &,const String &);
	friend bool operator > (const String &, char *);
	friend bool operator > (char *, const String &);
	friend bool operator > (const String &, const String &);
	friend bool operator >= (const String &, char *);
	friend bool operator >= (char *, const String &);
	friend bool operator >= (const String &,const String &);
	friend bool operator != (const String &, char *);
	friend bool operator != (char *, const String &);
	friend bool operator != (const String &,const String &);
	friend bool operator == (const String &, char *);
	friend bool operator == (char *, const String &);
	friend bool operator == (const String &,const String &);
	char & operator [] (int) const;
	// a getter method for the length property
	int length() const;
	// turns the string into a completely upper case string
	String & toupper() ;

protected:
	char * str;
	int len;

};

} // end namespace peter



#endif /* STRING_H_ */
