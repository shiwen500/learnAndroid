package www.seven.com.binderpooldemo.aidl;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Seven on 2016/3/1.
 */
public class Client1Impl extends IClient1.Stub {
    @Override
    public void add(int a, int b) throws RemoteException {
        Log.d("DEBUG", "a + b = " + (a+b));
    }
}
