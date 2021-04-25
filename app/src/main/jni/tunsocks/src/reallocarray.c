#include <stdint.h>
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>
#include "reallocarray.h"

void *reallocarray(void *ptr, size_t nmemb, size_t size) {
    unsigned long long res = nmemb * size;

    (void) limit;
    (void) used;
    if (res <= MAX_SIZE_T) {
        return (realloc(ptr, nmemb * size));
    } else {
        return (NULL);
    }
}