#ifdef __APPLE__
#include <OpenCL/cl.h>
#else
#include <CL/cl.h>
#endif
#include <stdio.h>

#define WIDTH_A 200
#define HEIGHT_A 400
#define ELEMENTS_A (WIDTH_A * HEIGHT_A)

#define WIDTH_B 300
#define HEIGHT_B 200
#define ELEMENTS_B (WIDTH_B * HEIGHT_B)

#define WIDTH_OUTPUT WIDTH_B
#define HEIGHT_OUTPUT HEIGHT_A
#define ELEMENTS_OUTPUT (WIDTH_OUTPUT * HEIGHT_OUTPUT)

#define CHECK_STATUS(s)                                                        \
  do {                                                                         \
    cl_int stat = (s);                                                         \
    if (stat != CL_SUCCESS) {                                                  \
      fprintf(stderr, "Error %d at line %d", stat, __LINE__);                  \
      exit(1);                                                                 \
    }                                                                          \
  } while (0)

// this would go into `matrix_multiply.cl`
static char const *const kernelSource =
    "__kernel void matrix_multiplication(uint widthA,"
    "                              __global const float* inputA,"
    "                              __global const float* inputB,"
    "                              __global float* output) {"
    "  int i = get_global_id(0);"
    "  int j = get_global_id(1);"
    "  int outputWidth = get_global_size(0);"
    "  int outputHeight = get_global_size(1);"
    "  int widthB = outputWidth;"
    "  float total = 0.0;"
    "  for (int k = 0; k < widthA; ++k)"
    "    total += inputA[j * widthA + k] * inputB[k * widthB + i];"
    "  output[j * outputWidth + i] = total;"
    "}";

void random_fill(cl_float array[], size_t size) {
  for (int i = 0; i < size; ++i)
    array[i] = (cl_float)rand() / RAND_MAX;
}

void print_array(const cl_float array[], const size_t width,
                 const size_t height) {
  for (size_t i = 0; i < height; ++i) {
    for (size_t j = 0; j < width; ++j) {
      printf("%f\t", array[i * width + j]);
    }
    printf("\n");
  }
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

  char const *const source = kernelSource;
  cl_program program =
      clCreateProgramWithSource(context, 1, (const char **)&source, NULL, NULL);
  CHECK_STATUS(status);

  CHECK_STATUS(clBuildProgram(program, 0, NULL, NULL, NULL, NULL));

  cl_kernel kernel = clCreateKernel(program, "matrix_multiplication", &status);
  CHECK_STATUS(status);

  cl_float a[ELEMENTS_A], b[ELEMENTS_B];
  random_fill(a, ELEMENTS_A);
  random_fill(b, ELEMENTS_B);

  cl_mem inputA =
      clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                     sizeof(cl_float) * ELEMENTS_A, a, &status);
  CHECK_STATUS(status);
  cl_mem inputB =
      clCreateBuffer(context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                     sizeof(cl_float) * ELEMENTS_B, b, &status);
  CHECK_STATUS(status);
  cl_mem output =
      clCreateBuffer(context, CL_MEM_WRITE_ONLY,
                     sizeof(cl_float) * ELEMENTS_OUTPUT, NULL, &status);
  CHECK_STATUS(status);

  cl_int widthA = WIDTH_A;
  CHECK_STATUS(clSetKernelArg(kernel, 0, sizeof(cl_int), &widthA));
  CHECK_STATUS(clSetKernelArg(kernel, 1, sizeof(cl_mem), &inputA));
  CHECK_STATUS(clSetKernelArg(kernel, 2, sizeof(cl_mem), &inputB));
  CHECK_STATUS(clSetKernelArg(kernel, 3, sizeof(cl_mem), &output));

  size_t work_units[] = {WIDTH_OUTPUT, HEIGHT_OUTPUT};
  CHECK_STATUS(clEnqueueNDRangeKernel(queue, kernel, 2, NULL, work_units, NULL,
                                      0, NULL, NULL));

  cl_float results[ELEMENTS_OUTPUT];
  CHECK_STATUS(clEnqueueReadBuffer(queue, output, CL_TRUE, 0,
                                   sizeof(cl_float) * ELEMENTS_OUTPUT, results,
                                   0, NULL, NULL));

  CHECK_STATUS(clReleaseMemObject(inputA));
  CHECK_STATUS(clReleaseMemObject(inputB));
  CHECK_STATUS(clReleaseMemObject(output));
  CHECK_STATUS(clReleaseKernel(kernel));
  CHECK_STATUS(clReleaseProgram(program));
  CHECK_STATUS(clReleaseCommandQueue(queue));
  CHECK_STATUS(clReleaseContext(context));

  print_array(a, WIDTH_A, HEIGHT_A);
  printf("\ntimes\n");
  print_array(b, WIDTH_B, HEIGHT_B);
  printf("\nequals\n");
  print_array(results, WIDTH_OUTPUT, HEIGHT_OUTPUT);

  return 0;
}
