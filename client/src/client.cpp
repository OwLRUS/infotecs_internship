#include "client.h"

using namespace std;

Monitor Client::monitor;

void Client::initWinsock() 
{
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) 
    {
        cerr << "client > WSAStartup failed" << endl;
        exit(1);
    }
}

bool Client::Connect()
{
    initWinsock();

    sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == INVALID_SOCKET) 
    {
        cerr << "client > Error creating socket!" << endl;
        WSACleanup();
        return false;
    }

    sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT);
    inet_pton(AF_INET, SERVER_ADR, &server_addr.sin_addr);

    if (connect(sock, (struct sockaddr*)&server_addr, sizeof(server_addr)) == SOCKET_ERROR) 
    {
        cerr << "client > Connection failed! Trying again next time." << endl;
        closesocket(sock);
        WSACleanup();
        return false;
    }

    return true;
}

void Client::Disconnect()
{
    closesocket(sock);
    WSACleanup();
}

Client::~Client()
{
    Disconnect();
}

void Client::Read() 
{
    for (;;) 
    {
        unique_lock<mutex> lk(monitor.lock);

        monitor.cond.wait(lk, [] { return !monitor.ready || monitor.reconnect; });

        string in;
        bool done = false;
        while (!done)
        {
            cout << "1st thread: Enter a number: > ";

            cin >> in;

            size_t size = in.size();
            if (size > 64)
            {
                cout << "1st thread: ERROR: The length of the entered number must be less than 64 characters! Try again." << endl;
                continue;
            }
            
            int i = 0;
            for (; i < size; ++i)
            {
                if (in[i] < '0' || in[i] > '9')
                {
                    cout << "1st thread: ERROR: The entered number must consist of digits! Try again." << endl;
                    break;
                }
            }

            if (i != size) continue;

            done = true;
        }

        sortKB(in);

        monitor.buf.push(in);
        monitor.ready = true;
        monitor.reconnect = false;

        monitor.cond.notify_one();
    }
}

void Client::Send() 
{
    for (;;) 
    {        
        unique_lock<mutex> lk(monitor.lock);
        monitor.cond.wait(lk, [] { return monitor.ready; });

        if (!Connect())
        {
            monitor.reconnect = true;
            monitor.cond.notify_one();
            lk.unlock();

            this_thread::sleep_for(chrono::milliseconds(10));

            continue;
        }

        monitor.ready = false;
        monitor.reconnect = false;
        while (monitor.buf.size() != 0)
        {
            string in = monitor.buf.front();
            monitor.buf.pop();

            cout << "2nd thread: Recieved data: " << in << endl;

            string _sum = sum(in);

            send(sock, _sum.c_str(), sizeof(_sum), 0);

            Disconnect();

            if (monitor.buf.size() != 0 && !Connect())
            {
                monitor.reconnect = true;
                break;
            }
        }

        monitor.cond.notify_one();
    }
}

void Client::Run() 
{    
    reader = thread(&Client::Read, this);
    sender = thread(&Client::Send, this);

    reader.join();
    sender.join();
}