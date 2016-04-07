package www.seven.com.binderpooldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import www.seven.com.binderpooldemo.aidl.BinderPoolImpl;

/**
 * Created by Seven on 2016/3/1.
 */
public class ServerService extends Service {

    public static final String TAG = "ServerService";

    private BinderPoolImpl mBinderPool;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinderPool;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinderPool = new BinderPoolImpl();
        Log.d(TAG, "onCreate");
    }
}
