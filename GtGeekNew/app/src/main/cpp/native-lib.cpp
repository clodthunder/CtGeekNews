#include <jni.h>
#include <string>

extern "C"
jstring
Java_gt_lskj_com_geeknew_ui_module_main_activity_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
