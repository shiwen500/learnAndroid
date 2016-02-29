package com.example.seven.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.seven.aidl.ForActivity;
import com.example.seven.aidl.ForService;
import com.example.seven.aidl.FroRemoteCallBack;

/**
 * Created by Seven on 2016/2/22.
 */
public class AIDLService extends Service {

    // 远程回调接口列表
    private RemoteCallbackList<FroRemoteCallBack>  rCallBacks = new RemoteCallbackList<>();

    private static final String TAG = "AIDLService";

    private ForService.Stub forService = new ForService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void invokCallBack() throws RemoteException {
            forActivity.performAction();

            // 遍历回调接口
            final int count = rCallBacks.beginBroadcast();

            for (int i = 0; i < count; ++i) {
                FroRemoteCallBack callBack = rCallBacks.getBroadcastItem(i);

                if (callBack != null) {
                    callBack.callback();
                }
            }

            rCallBacks.finishBroadcast();
        }

        @Override
        public void registerTestCall(ForActivity forAc) throws RemoteException {
            forActivity = forAc;
        }

        @Override
        public void registerRemoteCallBack(FroRemoteCallBack f) throws RemoteException {
            //注册
            rCallBacks.register(f);
        }

        @Override
        public void unregisterRemoteCallBack(FroRemoteCallBack f) throws RemoteException {
            // 反注册
            rCallBacks.unregister(f);
        }
    };

    private ForActivity forActivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return forService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service create ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "service start ==" + startId);
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "service on destroy");
        super.onDestroy();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "service on unbind");
        return super.onUnbind(intent);
    }
    public void onRebind(Intent intent) {
        Log.d(TAG, "service on rebind");
        super.onRebind(intent);
    }
}
