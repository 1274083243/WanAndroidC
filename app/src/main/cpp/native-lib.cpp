#include <jni.h>
#include <string>
#include <android/log.h>
#define  LOG_TAG    "ike_v"
#define  log(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
/**
 * 字符串复制
 */
char *strCopy(char *dest, char *src) {
    char *p = dest;
    while (*src != '\0') {
        *dest++ = *src++;
    }
    return p;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_ike_wanandroidc_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int i;
    for (i=0 ; i <30 ; ++i) {
        log("*");
    }

    return env->NewStringUTF(hello.c_str());
}


