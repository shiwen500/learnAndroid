package www.seven.com.binderpooldemo.aidl;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Seven on 2016/3/1.
 */
public class Client2Impl extends IClient2.Stub {
    @Override
    public void dec(int a, int b) throws RemoteException {
        Log.d("DEBUG", " a - b = " + (a-b));
    }
}
