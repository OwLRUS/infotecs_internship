#pragma once

#include "infotecs_internship.h"
#include <iostream>
#include <cstdlib>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <chrono>
#include <string>
#include <queue>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

#define PORT 8888
#define SERVER_ADR "127.0.0.1"

struct Monitor
{
    std::condition_variable cond;
    std::mutex lock;
    bool ready = false;
    bool reconnect = false;
    std::queue<std::string> buf;
};

class Client
{
public:
    void Run();
    bool Connect();
    void Disconnect();
    ~Client();
private:
    std::thread reader;
    std::thread sender;
    static Monitor monitor;
    SOCKET sock;
    void Read();
    void Send();
    void initWinsock();
};