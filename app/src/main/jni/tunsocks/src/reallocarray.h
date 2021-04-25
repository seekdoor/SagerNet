#include <stdlib.h>

static size_t limit = 0;
static size_t used = 0;
void *reallocarray(void *ptr, size_t nmemb, size_t size);
#define MAX_SIZE_T 4294967295