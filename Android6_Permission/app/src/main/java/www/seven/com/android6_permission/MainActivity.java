package www.seven.com.android6_permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.reqbodysensor).setOnClickListener(this);
        findViewById(R.id.reqcalendar).setOnClickListener(this);
        findViewById(R.id.reqcamera).setOnClickListener(this);
        findViewById(R.id.reqcontacts).setOnClickListener(this);
        findViewById(R.id.reqlocation).setOnClickListener(this);
        findViewById(R.id.reqphone).setOnClickListener(this);
        findViewById(R.id.reqrecord).setOnClickListener(this);
        findViewById(R.id.reqsms).setOnClickListener(this);
        findViewById(R.id.reqstorage).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        PermissionsManager pm = PermissionsManager.getInstance();

        switch (id) {
            case R.id.reqbodysensor:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.BODY_SENSORS},
                        new MyPermissionAction("身体传感器权限"));
                break;
            case R.id.reqcalendar:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.WRITE_CALENDAR},
                        new MyPermissionAction("日期权限"));
                break;
            case R.id.reqcamera:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.CAMERA},
                        new MyPermissionAction("相机权限"));
                break;
            case R.id.reqcontacts:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.WRITE_CONTACTS},
                        new MyPermissionAction("联系人权限"));
                break;
            case R.id.reqlocation:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        new MyPermissionAction("位置权限"));
                break;
            case R.id.reqphone:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.CALL_PHONE},
                        new MyPermissionAction("电话权限"));
                break;
            case R.id.reqrecord:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.RECORD_AUDIO},
                        new MyPermissionAction("录音权限"));
                break;
            case R.id.reqsms:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.SEND_SMS},
                        new MyPermissionAction("短信权限"));
                break;
            case R.id.reqstorage:
                pm.requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new MyPermissionAction("存储卡权限"));
                break;
        }
    }

    private class MyPermissionAction extends PermissionsResultAction {

        private String permissionName;

        public MyPermissionAction(String name) {
            permissionName = name;
        }

        @Override
        public void onGranted() {
            Log.d(TAG, permissionName + " 授权成功");
            Toast.makeText(MainActivity.this, permissionName + " 授权成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDenied(String permission) {
            Log.d(TAG, permissionName + " 授权失败");
            Toast.makeText(MainActivity.this, permissionName + " 授权失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
