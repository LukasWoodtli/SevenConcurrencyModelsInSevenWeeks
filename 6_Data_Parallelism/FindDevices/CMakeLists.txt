cmake_minimum_required(VERSION 3.19)
project(FindDevices C)

set(CMAKE_C_STANDARD 99)

find_package(OpenCL)

add_executable(FindDevices
        main.c
)

target_include_directories(FindDevices PRIVATE ${OpenCL_INCLUDE_DIRS})

target_link_libraries(FindDevices PRIVATE ${OpenCL_LIBRARIES})