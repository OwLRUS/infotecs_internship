#include "server.h"

int main()
{
	Server s;
	s.run();
	while (true)
	{
		s.slisten();
	}

	return 0;
}