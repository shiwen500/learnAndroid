package www.seven.com.binderpooldemo.aidl;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Seven on 2016/3/1.
 */
public class BinderPoolImpl extends IBinderPool.Stub {
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case 1:
                binder = new Client1Impl();
                break;
            case 2:
                binder = new Client2Impl();
                break;
            default:
                throw new IllegalArgumentException("binderCode 错误");
        }

        Log.d("DEBUG", "查询 binderCode = " + binderCode + " binder = " + binder);
        return binder;
    }
}
