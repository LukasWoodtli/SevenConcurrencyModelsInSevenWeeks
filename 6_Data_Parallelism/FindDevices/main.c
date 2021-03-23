#ifdef __APPLE__
#include <OpenCL/cl.h>
#else
#include <CL/cl.h>
#endif
#include <stdio.h>

#define CHECK_STATUS(s) do { \
  cl_int stat = (s); \
  if (stat != CL_SUCCESS) { \
    fprintf(stderr, "Error %d at line %d\n", stat, __LINE__); \
    exit(1); \
    } } while(0)

static void print_device_param_string(cl_device_id device,
                               cl_device_info param_id,
                               const char* param_name) {
  char value[1024];
  CHECK_STATUS(clGetDeviceInfo(device, param_id, sizeof(value), value, NULL));
  printf("%s: %s\n", param_name, value);
}

static void print_device_param_uint(cl_device_id device,
                             cl_device_info param_id,
                             const char* param_name) {
  cl_uint value;
  CHECK_STATUS(clGetDeviceInfo(device, param_id, sizeof(value), &value, NULL));
  printf("%s: %u\n", param_name, value);
}

static void print_device_param_ulong(cl_device_id device,
                              cl_device_info param_id,
                              const char* param_name) {
  cl_ulong value;
  CHECK_STATUS(clGetDeviceInfo(device, param_id, sizeof(value), &value, NULL));
  printf("%s: %lu\n", param_name, (unsigned long)value);
}

static void print_device_param_sizet(cl_device_id device,
                              cl_device_info param_id,
                              const char* param_name) {
  size_t value;
  CHECK_STATUS(clGetDeviceInfo(device, param_id, sizeof(value), &value, NULL));
  printf("%s: %zd\n", param_name, value);
}

static void print_platform_param(cl_platform_id platform,
                          cl_platform_info param_id,
                          const char* param_name) {
  char value[1024];
  CHECK_STATUS(clGetPlatformInfo(platform, param_id, sizeof(value), value, NULL));
  printf("%s: %s\n", param_name, value);
}

static void print_device_info(cl_device_id device) {
  print_device_param_string(device, CL_DEVICE_NAME, "Name");
  print_device_param_string(device, CL_DEVICE_VENDOR, "Vendor");
  print_device_param_uint(device, CL_DEVICE_MAX_COMPUTE_UNITS, "Compute Units");
  print_device_param_ulong(device, CL_DEVICE_GLOBAL_MEM_SIZE, "Global Memory");
  print_device_param_ulong(device, CL_DEVICE_LOCAL_MEM_SIZE, "Local Memory");
  print_device_param_sizet(device, CL_DEVICE_MAX_WORK_GROUP_SIZE, "Workgroup size");
}

static void print_platfor m_info(cl_platform_id platform) {
  print_platform_param(platform, CL_PLATFORM_NAME, "Name");
  print_platform_param(platform, CL_PLATFORM_VENDOR, "Vendor");

  cl_uint num_devices;
  CHECK_STATUS(clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, 0, NULL, &num_devices));

  cl_device_id * devices = (cl_device_id*)malloc(sizeof(cl_device_id) + num_devices);
  CHECK_STATUS(clGetDeviceIDs(platform, CL_DEVICE_TYPE_ALL, num_devices, devices, NULL));

  printf("\nFound %u device(s)\n", num_devices);
  for (int i = 0; i < num_devices; ++i) {
    printf("\nDevice %d\n", i);
    print_device_info(devices[i]);
  }
}

int main() {

  cl_uint num_platforms;
  CHECK_STATUS(clGetPlatformIDs(0, NULL, &num_platforms));

  cl_platform_id *platforms = (cl_platform_id*)malloc(sizeof(cl_platform_id) * num_platforms);
  CHECK_STATUS(clGetPlatformIDs(num_platforms, platforms, NULL));

  printf("\nFound %u OpenCL platform(s)\n\n",num_platforms);
  for (int i = 0; i < num_platforms; ++i) {
    printf("Platform %d\n", i);
    print_platform_info(platforms[i]);
  }
}

