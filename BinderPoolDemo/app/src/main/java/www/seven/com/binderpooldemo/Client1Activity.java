package www.seven.com.binderpooldemo;

import android.content.Intent;
import android.os.Binder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import www.seven.com.binderpooldemo.aidl.BinderPoolManager;
import www.seven.com.binderpooldemo.aidl.IClient1;
import www.seven.com.binderpooldemo.aidl.IClient2;

public class Client1Activity extends AppCompatActivity {

    private static final String TAG = "Client1Activity";

    private IClient1 mBinder;
    private BinderPoolManager bpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client1);

        // 绑定过程比较耗时，所以不能在create这里直接取binder
        bpm = BinderPoolManager.getInstance(this);
//        mBinder = IClient1.Stub.asInterface(bpm.queryBinder(1));
//
//        if (mBinder == null) {
//            Log.d(TAG, "binder 为空");
//        }
    }



    public void onSend(View view) {
        try {
            mBinder = IClient1.Stub.asInterface(bpm.queryBinder(1));
            mBinder.add(1, 2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void goToClient2(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Client2Activity.class);
        startActivity(intent);
    }
}
