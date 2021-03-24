cmake_minimum_required(VERSION 3.19)
project(FindMinimum C)

set(CMAKE_C_STANDARD 99)

find_package(OpenCL)

add_executable(FindMinimum
        main.c
)

target_include_directories(FindMinimum PRIVATE ${OpenCL_INCLUDE_DIRS})

target_link_libraries(FindMinimum PRIVATE ${OpenCL_LIBRARIES})