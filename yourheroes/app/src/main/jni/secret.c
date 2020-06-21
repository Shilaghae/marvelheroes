#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_withplum_yourheroes_network_ApiKeyInterceptor_getBruceWayne(JNIEnv *env, jobject instance) {

 return (*env)->  NewStringUTF(env, "d38841dc061d96fb404f941162cf00b6");
}

JNIEXPORT jstring JNICALL
Java_com_withplum_yourheroes_network_ApiKeyInterceptor_getBatman(JNIEnv *env, jobject instance) {

 return (*env)->NewStringUTF(env, "1a4e5c0f76615e38c6f6c0fa43bc8bcda42db2b7");
}