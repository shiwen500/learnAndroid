
package com.example.seven.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.seven.aidl.ForActivity;
import com.example.seven.aidl.ForService;

public class AIDLActivity extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = "AIDLActivity";

    private Button startServer;
    private Button stopServer;
    private Button callback;

    private ForActivity forActivity = new ForActivity.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void performAction() throws RemoteException {
            Toast.makeText(AIDLActivity.this, "this is call by service !", Toast.LENGTH_LONG).show();
        }
    };

    private ForService forService ;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            forService = ForService.Stub.asInterface(service);

            try {
                forService.registerTestCall(forActivity);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, "register TestCall fail !");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            forService = null;
            Log.d(TAG, "service disconnect !");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        startServer = (Button) findViewById(R.id.start);
        stopServer = (Button) findViewById(R.id.stop);
        callback = (Button) findViewById(R.id.callback);


        startServer.setOnClickListener(this);
        stopServer.setOnClickListener(this);
        callback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final int viewId = v.getId();

        switch (viewId) {
            case R.id.start: {
                Intent intent = new Intent();
                intent.setClass(this, AIDLService.class);

                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                startService(intent);
            }
                break;
            case R.id.stop: {
                unbindService(serviceConnection);
            }
                break;
            case R.id.callback: {
                try {

                    // 如果这个方法比较耗时，那么最好使用非UI线程
                    forService.invokCallBack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d(TAG, "invok callBack fial !");
                }
            }
                break;
        }
    }
}
