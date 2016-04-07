//
// Created by Seven on 2016/3/15.
//
#include "www_seven_com_ndklib_MyNdkLib.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_www_seven_com_ndklib_MyNdkLib_getHelloWorld
        (JNIEnv * env, jobject thiz) {
    return env->NewStringUTF("I'm comes from to Native Function!");
}
