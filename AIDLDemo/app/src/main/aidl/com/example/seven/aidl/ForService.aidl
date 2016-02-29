// ForService.aidl
package com.example.seven.aidl;
import com.example.seven.aidl.ForActivity;
import com.example.seven.aidl.FroRemoteCallBack;

// Declare any non-default types here with import statements

interface ForService {

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void invokCallBack();


    // 提供一个接口
    // 获取启动自己的Activity
    // Service 可以通过这个接口，操作Activity
    void registerTestCall(ForActivity forAc);

    void registerRemoteCallBack(FroRemoteCallBack f);
    void unregisterRemoteCallBack(FroRemoteCallBack f);
}
