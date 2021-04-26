#include <jni.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <linux/in.h>

JNIEXPORT jint Java_io_nekohasekai_sagernet_utils_NativeUtil_openSocket(
        JNIEnv *env, jobject thiz) {
    return socket(AF_INET, SOCK_RAW, IPPROTO_TCP);
}