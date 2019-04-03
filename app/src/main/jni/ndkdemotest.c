//
// Created by asus on 2019/4/3.
//

#include "apiratehat_androidsamplecode_jni_NDKTools.h"

JNIEXPORT jstring JNICALL Java_apiratehat_androidsamplecode_jni_NDKTools_getStringFromNDK
  (JNIEnv *env, jobject obj){
     return (*env)->NewStringUTF(env,"Hellow World，这是NDK的第一行代码");
  }