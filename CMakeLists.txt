cmake_minimum_required (VERSION 3.8)
project(infotecsInternship)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS ON)

set(CLIENT_SOURCES client/src/client.cpp client/src/main.cpp)
set(SERVER_SOURCES server/src/server.cpp server/src/main.cpp)

add_library(INFOTECS_LIB SHARED ${CMAKE_SOURCE_DIR}/lib/src/infotecs_internship.cpp ${CMAKE_SOURCE_DIR}/lib/include/infotecs_internship.h)
target_include_directories(INFOTECS_LIB PUBLIC ${CMAKE_SOURCE_DIR}/lib/include)
target_compile_definitions(INFOTECS_LIB PRIVATE INFOTECS_LIB_EXPORTS)

add_executable (server ${SERVER_SOURCES} "${CMAKE_SOURCE_DIR}/server/include/server.h")
add_executable (client ${CLIENT_SOURCES} "${CMAKE_SOURCE_DIR}/client/include/client.h")

target_include_directories(client PRIVATE ${CMAKE_SOURCE_DIR}/lib/include ${CMAKE_SOURCE_DIR}/client/include)
target_include_directories(server PRIVATE ${CMAKE_SOURCE_DIR}/lib/include ${CMAKE_SOURCE_DIR}/server/include)

target_link_libraries(client INFOTECS_LIB ws2_32)
target_link_libraries(server INFOTECS_LIB ws2_32)

add_dependencies(client INFOTECS_LIB)
add_dependencies(server INFOTECS_LIB)
