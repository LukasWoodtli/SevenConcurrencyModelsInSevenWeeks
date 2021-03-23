cmake_minimum_required(VERSION 3.19)
project(MatrixMultiply C)

set(CMAKE_C_STANDARD 99)

find_package(OpenCL)

add_executable(MatrixMultiply
        main.c
)

target_include_directories(MatrixMultiply PRIVATE ${OpenCL_INCLUDE_DIRS})

target_link_libraries(MatrixMultiply PRIVATE ${OpenCL_LIBRARIES})