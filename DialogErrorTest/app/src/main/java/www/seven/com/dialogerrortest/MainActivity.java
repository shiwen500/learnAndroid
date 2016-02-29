package www.seven.com.dialogerrortest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// 当从A到B界面后，立刻返回，由于timer还没执行完成，所以B界面没有释放，但是B界面的onDestroy还是会执行。
// 所以等到B界面的timer执行完成后，想要show dialog时，对应的Activity已经不再运行了，所以应用退出。
// 解决办法： 在onDestory中停止timer（推荐）
// 解决办法2：在showDialog之前，判断activity是否运行
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button showDialog;
    private Button next;

    private CountDownTimer cdTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDialog = (Button) findViewById(R.id.show_dialog);
        showDialog.setOnClickListener(this);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        cdTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                showDialog();
            }
        };

        cdTimer.start();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.show_dialog:
                showDialog();
                break;
            case R.id.next:
                next();
                break;
        }
    }

    private void showDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setMessage("Test Dialog")
                .setTitle("Hi")
                .create();

        dialog.show();
    }

    private void next() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
