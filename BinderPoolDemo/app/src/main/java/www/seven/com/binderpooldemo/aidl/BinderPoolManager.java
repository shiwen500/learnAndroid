package www.seven.com.binderpooldemo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import www.seven.com.binderpooldemo.ServerService;

/**
 * Created by Seven on 2016/3/1.
 */
public class BinderPoolManager {

    public static final String TAG = "BinderPoolManager";

    private Context mContext;

    private IBinderPool mBinderPool;

    // 单例模式
    private static volatile BinderPoolManager instance;

    // 需要将构造函数设置为私有的
    private BinderPoolManager(Context context) {

        // 只要应用没退出，这个context一直有效
        mContext = context.getApplicationContext();
        connect();
    }

    public static BinderPoolManager getInstance(Context context) {

        if (instance == null) {

            // 线程安全的
            synchronized (BinderPoolManager.class) {
                if (instance == null) {
                    instance = new BinderPoolManager(context);
                }
            }
        }

        return instance;
    }

    // 链接Service进程, 保留一个BinderPoll
    private synchronized void connect() {

        Intent service = new Intent(mContext, ServerService.class);

        mContext.bindService(service, sc, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 持有绑定后的binder对象
            mBinderPool = IBinderPool.Stub.asInterface(service);

            Log.d(TAG, "绑定 binderPool 成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.d(TAG, "断开 binderPool 绑定");
            // 断开后，重绑定
            connect();
        }
    };


    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;

        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        return binder;
    }
}
