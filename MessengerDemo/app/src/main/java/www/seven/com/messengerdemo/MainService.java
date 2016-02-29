package www.seven.com.messengerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by Seven on 2016/2/29.
 */
public class MainService extends Service {

    private static final String TAG = "MainService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return clientMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {

        private WeakReference<MainService> mService;

        private MainService getService() {
            return mService.get();
        }

        public MessengerHandler(MainService mainService) {

            mService = new WeakReference<MainService>(mainService);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MainActivity.MSG_FROM_CLIENT:
                    if (getService() != null) {
                        getService().log_message("收到来自客户端的信息 ==> " + msg.getData().getString("msg"));
                    }
                    // 应答客户端
                    Messenger client = msg.replyTo;

                    // 创建应答消息
                    msg.recycle();
                    msg = Message.obtain();
                    msg.what = MainActivity.MSG_FROM_SERVICE;

                    // 消息内容
                    Bundle data = new Bundle();
                    data.putString("msg", "客户端你好， 你的消息我已经收到了!");
                    msg.setData(data);

                    try {
                        client.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
            }

            super.handleMessage(msg);
        }
    }

    public void log_message(String msg) {
        Log.d(TAG, msg);
    }

    private Messenger clientMessenger = new Messenger(new MessengerHandler(this));

    @Override
    public void onDestroy() {
        clientMessenger = null;
        super.onDestroy();
    }
}
