cmake_minimum_required (VERSION 3.8)
project(infotecsInternship)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS ON)

add_library(INFOTECS_LIB SHARED ${CMAKE_SOURCE_DIR}/lib/src/infotecs_internship.cpp ${CMAKE_SOURCE_DIR}/lib/include/infotecs_internship.h)
target_include_directories(INFOTECS_LIB PUBLIC ${CMAKE_SOURCE_DIR}/lib/include)
target_compile_definitions(INFOTECS_LIB PRIVATE INFOTECS_LIB_EXPORTS)

add_executable (server "${CMAKE_SOURCE_DIR}/server/server.cpp")
add_executable (client "${CMAKE_SOURCE_DIR}/client/client.cpp")

target_include_directories(client PRIVATE ${CMAKE_SOURCE_DIR}/lib/include)
target_include_directories(server PRIVATE ${CMAKE_SOURCE_DIR}/lib/include)

target_link_libraries(client INFOTECS_LIB ws2_32)
target_link_libraries(server INFOTECS_LIB ws2_32)

add_dependencies(client INFOTECS_LIB)
add_dependencies(server INFOTECS_LIB)
