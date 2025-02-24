#include "infotecs_internship.h"
#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>

using namespace std;

#pragma comment(lib, "ws2_32.lib")

#define PORT 8888

void initWinsock() 
{
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) 
    {
        cerr << "server > WSAStartup failed!" << endl;
        exit(1);
    }
}

int main() 
{
    initWinsock();

    SOCKET server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd == INVALID_SOCKET) 
    {
        cerr << "server > Error creating socket!" << endl;
        WSACleanup();
        return 1;
    }

    sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(PORT);

    if (bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr)) == SOCKET_ERROR) 
    {
        cerr << "server ERROR > Bind failed!" << endl;
        closesocket(server_fd);
        WSACleanup();
        return 1;
    }

    if (listen(server_fd, 1) == SOCKET_ERROR) 
    {
        cerr << "server ERROR > Listen failed!" << endl;
        closesocket(server_fd);
        WSACleanup();
        return 1;
    }

    cout << "server > Server started on port " << PORT << endl;

    while (true) 
    {
        sockaddr_in client_addr;
        int addr_len = sizeof(client_addr);
        SOCKET client_fd = accept(server_fd, (struct sockaddr*)&client_addr, &addr_len);
        if (client_fd == INVALID_SOCKET) 
        {
            cerr << "server ERROR > Accept failed!" << endl;
            continue;
        }

        cout << "server > New client connected" << endl;

        char buffer[4] = { 0, 0, 0, '\0'};
        int valread = recv(client_fd, buffer, sizeof(buffer), 0);
        string str(buffer);
        if (valread > 0 && isSize32(str)) 
        {
            cout << "server > Message received: " << buffer << endl;
            send(client_fd, "Message received", 17, 0);
        }
        else if (valread > 0)
        {
            cout << "server ERROR > Received message legth is not a multiple of 32!" << endl;
        }

        closesocket(client_fd);
        cout << "server > Client disconnected" << endl;
    }

    closesocket(server_fd);
    WSACleanup();
    return 0;
}
