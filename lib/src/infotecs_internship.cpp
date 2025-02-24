#include "infotecs_internship.h"

using namespace std;

bool cmp(const char a, const char b)
{
	return a > b;
}

void sortKB(string& str)
{
	sort(str.begin(), str.end(), cmp);

	size_t size = str.size();
	for (int i = 0; i < size; ++i)
	{
		if (str[i] % 2 == 0)
		{
			str.replace(i, 1, "KB");
			++i;
			++size;
		}
	}
}

string sum(const string& str)
{
	int sum = 0;

	for (const auto& chr : str)
	{
		if (chr >= '0' && chr <= '9') sum += chr - '0';
	}

	return to_string(sum);
}

bool isSize32(const string& num_str)
{
	size_t size = num_str.size();

	if (size <= 2) return false;

	unsigned short num = 0;
	for (int i = 0; i < size; ++i)
	{
		num *= 10;
		num += num_str[i] - '0';
	}

	return num % 32 == 0;
}
