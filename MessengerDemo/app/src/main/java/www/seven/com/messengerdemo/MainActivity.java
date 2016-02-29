package www.seven.com.messengerdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ClientActivity";
    public static final int MSG_FROM_SERVICE = 1;
    public static final int MSG_FROM_CLIENT = 2;

    private Messenger mService;

    private EditText input;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);

        Intent intent = new Intent(this, MainService.class);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.send:
                sendMessage();
                break;
        }
    }

    public void sendMessage() {
        String msgWord = input.getText().toString();
        input.setText("");

        // 表明是客户端发送过去的数据
        Message msg = Message.obtain();
        msg.what = MSG_FROM_CLIENT;

        // 设置传输消息
        Bundle data = new Bundle();
        data.putString("msg", "你好！服务器。我是客户端。==> " + msgWord);
        msg.setData(data);

        // 设置应答对象
        // 服务器会调用这个对象，对客户端的问题进行应答。
        msg.replyTo = mServiceReplyMessenger;

        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();

            log_message("客户端发送数据失败！");
        }
    }

    private static class MessengerHander extends Handler {

        // 通过弱引用，就算服务器没及时返回，activity也可以被及时释放。
        // 代价是，在要用activity的地方，需要判断是否为空
        private WeakReference<MainActivity> mWeakActivity;

        private MainActivity getActivity() {
            return mWeakActivity.get();
        }

        public MessengerHander(MainActivity mainActivity) {
            mWeakActivity = new WeakReference<MainActivity>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch(msg.what) {
                case MSG_FROM_SERVICE:
                    if (getActivity() != null) {
                        getActivity().log_message("获取到服务器的数据 ==> " + msg.getData().getString("msg"));
                        Toast.makeText(getActivity(), msg.getData().getString("msg"), Toast.LENGTH_LONG).show();
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public void log_message(String msg) {
        Log.d(TAG, msg);
    }

    private Messenger mServiceReplyMessenger = new Messenger(new MessengerHander(this));

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 创建信鸽
            mService = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(mServiceConn);
        super.onDestroy();
    }
}
