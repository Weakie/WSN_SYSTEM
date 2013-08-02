#include "d_random.h"


const long randomNumber::A = 48271;
const long randomNumber::M = 2147483647;
const long randomNumber::Q = M / A;
const long randomNumber::R = M % A;
/*const static long A = 48271;
const static long M = 2147483647;
const static long Q = M / A;
const static long R = M % A;*/

randomNumber::randomNumber(long s)
{
	if (s < 0)
		s = 0;

	if (s == 0)
	{
		// get time of day in seconds since 12:00 AM,
		// January 1, 1970
		long t_time = time(NULL);

		// mix-up bits by squaring
		t_time *= t_time;
		// result can overflow. handle cases
		// > 0, < 0, = 0
		if (t_time > 0)
			s = t_time ^ 0x5EECE66DL;
		else if (t_time < 0)
			s = (t_time & 0x7fffffff) ^ 0x5EECE66DL;
		else
			s = 0x5EECE66DL;
	}

	seed = s;
}

long randomNumber::random()
{
	long tmpSeed = A * ( seed % Q ) - R * ( seed / Q );

	if( tmpSeed >= 0 )
		seed = tmpSeed;
	else
		seed = tmpSeed + M;

	return seed;
}

long randomNumber::random(long n)
{
	double fraction = double(random())/double(M);

	return int(fraction * n);
}

double randomNumber::frandom()
{
	return double(random())/double(M);
}