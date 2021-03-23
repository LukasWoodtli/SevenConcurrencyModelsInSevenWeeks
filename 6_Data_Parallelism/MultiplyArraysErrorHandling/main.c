#ifdef __APPLE__
#include <OpenCL/cl.h>
#else
#include <CL/cl.h>
#endif
#include <stdio.h>

#define NUM_ELEMENTS 1024

#define CHECK_STATUS(s) do { \
    cl_int stat = (s); \
    if (stat != CL_SUCCESS) { \
        fprintf(stderr, "Error %d at line %d", stat, __LINE__); \
        exit(1); \
    } } while (0)

// this would go into `multiply_arrays.cl`

static char const * const kernelSource = "__kernel void multiply_arrays(__global const float* inputA,"
                            "                              __global const float* inputB,"
                            "                              __global float* output) {"
                            "  int i = get_global_id(0);"
                            "  output[i] = inputA[i] * inputB[i];"
                            "}";

void random_fill(cl_float array[], size_t size) {
    for (int i = 0; i < size; ++i)
        array[i] = (cl_float)rand() / RAND_MAX;
}

int main() {
    cl_int status;

    cl_platform_id platform;
    CHECK_STATUS(clGetPlatformIDs(1, &platform, NULL));

    cl_device_id device;
    CHECK_STATUS(clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, 1, &device, NULL));

    cl_context context = clCreateContext(NULL, 1, &device, NULL, NULL, &status);
    CHECK_STATUS(status);

    cl_command_queue queue = clCreateCommandQueue(context, device, 0, &status);
    CHECK_STATUS(status);


    char const * const source = kernelSource;
    cl_program program = clCreateProgramWithSource(context, 1, (const char**)&source, NULL, NULL);
    CHECK_STATUS(status);

    CHECK_STATUS(clBuildProgram(program, 0, NULL, NULL, NULL, NULL));

    cl_kernel kernel = clCreateKernel(program, "multiply_arrays", &status);
    CHECK_STATUS(status);

    cl_float a[NUM_ELEMENTS];
    random_fill(a, NUM_ELEMENTS);
    cl_float b[NUM_ELEMENTS];
    random_fill(b, NUM_ELEMENTS);

    cl_mem inputA = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                                   sizeof(cl_float) * NUM_ELEMENTS, a, &status);
    CHECK_STATUS(status);
    cl_mem inputB = clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                                   sizeof(cl_float) * NUM_ELEMENTS, b, &status);
    CHECK_STATUS(status);
    cl_mem output = clCreateBuffer(context, CL_MEM_WRITE_ONLY,
                                   sizeof(cl_float) * NUM_ELEMENTS, NULL, &status);
    CHECK_STATUS(status);

    CHECK_STATUS(clSetKernelArg(kernel, 0, sizeof(cl_mem), &inputA));
    CHECK_STATUS(clSetKernelArg(kernel, 1, sizeof(cl_mem), &inputB));
    CHECK_STATUS(clSetKernelArg(kernel, 2, sizeof(cl_mem), &output));

    size_t work_units = NUM_ELEMENTS;
    CHECK_STATUS(clEnqueueNDRangeKernel(queue, kernel, 1, NULL, &work_units, NULL, 0, NULL, NULL));

    cl_float results[NUM_ELEMENTS];
    CHECK_STATUS(clEnqueueReadBuffer(queue, output, CL_TRUE, 0, sizeof(cl_float) * NUM_ELEMENTS,
                        results, 0, NULL, NULL));

    CHECK_STATUS(clReleaseMemObject(inputA));
    CHECK_STATUS(clReleaseMemObject(inputB));
    CHECK_STATUS(clReleaseMemObject(output));
    CHECK_STATUS(clReleaseKernel(kernel));
    CHECK_STATUS(clReleaseProgram(program));
    CHECK_STATUS(clReleaseCommandQueue(queue));
    CHECK_STATUS(clReleaseContext(context));

    for (int i = 0; i < NUM_ELEMENTS; ++i) {
        printf("%f * %f = %f\n", a[i], b[i], results[i]);
    }

    return 0;
}
