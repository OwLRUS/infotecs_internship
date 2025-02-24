#pragma once

#include "infotecs_internship.h"
#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

#define PORT 8888

class Server
{
public:
	void run();
	void slisten();
	~Server();

private:
	SOCKET server_fd;
	sockaddr_in server_addr;
	void initWinsock();
};