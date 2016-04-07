package www.seven.com.remoteviewdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button show_notification;
    private Button show_customnitification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show_notification = (Button) findViewById(R.id.show_notification);
        show_notification.setOnClickListener(this);

        show_customnitification = (Button) findViewById(R.id.show_custom_notification);
        show_customnitification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.show_notification:{
                showNotification();
                break;
            }
            case R.id.show_custom_notification:{
                showCustomNotification();
                break;
            }
        }
    }

    private void showNotification() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("hello world!")
                .setContentText("this is notification")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setColor(Color.RED)
                .build();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.contentIntent = pendingIntent;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, notification);
    }

    private void showCustomNotification() {
        Notification notification = new Notification();

        notification.icon = R.mipmap.ic_launcher;
        notification.when = System.currentTimeMillis();
        notification.tickerText = "你有新的通知";
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_layout);
        rv.setTextViewText(R.id.title, "主题");
        rv.setTextViewText(R.id.subtitle, "副主题");
        rv.setImageViewResource(R.id.icon, R.mipmap.icon);

        notification.contentView = rv;
        notification.contentIntent = pendingIntent;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(2, notification);
    }
}
