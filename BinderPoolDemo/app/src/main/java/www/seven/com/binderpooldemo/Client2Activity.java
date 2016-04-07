package www.seven.com.binderpooldemo;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import www.seven.com.binderpooldemo.aidl.BinderPoolManager;
import www.seven.com.binderpooldemo.aidl.IClient1;
import www.seven.com.binderpooldemo.aidl.IClient2;

/**
 * Created by Seven on 2016/3/1.
 */
public class Client2Activity extends AppCompatActivity {

    private BinderPoolManager mBinderPoolManager;
    private IClient2 mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_client2);

        mBinderPoolManager = BinderPoolManager.getInstance(this);

    }

    public void onSend(View view) {

        try {
            mBinder = IClient2.Stub.asInterface(mBinderPoolManager.queryBinder(2));
            mBinder.dec(2, 1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
