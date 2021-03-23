cmake_minimum_required(VERSION 3.19)
project(MultiplyArraysErrorHandling C)

set(CMAKE_C_STANDARD 99)

find_package(OpenCL)

add_executable(MultiplyArrays
        main.c
)

target_include_directories(MultiplyArrays PRIVATE ${OpenCL_INCLUDE_DIRS})

target_link_libraries(MultiplyArrays PRIVATE ${OpenCL_LIBRARIES})