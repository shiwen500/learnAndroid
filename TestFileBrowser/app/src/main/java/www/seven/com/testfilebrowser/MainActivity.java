package www.seven.com.testfilebrowser;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView title;

    private ListView list;

    // 目录栈
    private List<List<File>> stacks = new ArrayList<>();

    private FileListAdapter mAdapter;

    private ArrayList<File> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.setPriority(1000);// 设置最高优先级
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);// sd卡存在，但还没有挂载
//        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);// sd卡被移除
//        intentFilter.addAction(Intent.ACTION_MEDIA_SHARED);// sd卡作为 USB大容量存储被共享，挂载被解除
//        intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);// sd卡已经从sd卡插槽拔出，但是挂载点还没解除
//        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);// 开始扫描
//        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);// 扫描完成
        intentFilter.addDataScheme("file");

        registerReceiver(sdcardMountedRcv, intentFilter);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//                Snackbar.make(findViewById(android.R.id.content), "请去设置权限那里打开应用存储权限！", Snackbar.LENGTH_LONG).show();

                AlertDialog ad = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("应用需要申请sdcard权限,是否申请?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        0);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();

                ad.show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            // 在Android6.0系统上，表明用户已经授予了SDCARD权限；如果是低于6.0系统，那么不需要授予权限，所以直接进入到这个Section。
            Toast.makeText(this, "应用已经获取到写入sdcard权限,无需继续申请", Toast.LENGTH_LONG).show();

            initStorage();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                Toast.makeText(this, "应用已经获取到写入sdcard权限", Toast.LENGTH_LONG).show();

                initStorage();

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(this, "申请写入sdcard权限被拒绝", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    private void initView() {

        title = (TextView) findViewById(R.id.title);

        list = (ListView) findViewById(R.id.list);

//        Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());

        mAdapter = new FileListAdapter(this, datas);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File row = datas.get(position);
                if (row.isDirectory()) {
                    File[] files = row.listFiles();
                    List<File> nextFiles = Arrays.asList(files);

                    datas.clear();
                    datas.addAll(nextFiles);
                    mAdapter.notifyDataSetChanged();

                    stacks.add(nextFiles);
                }
            }
        });

        title.setText("系统根目录");
    }

    private void initStorage() {

        // /storage/emulated/0
        Log.d(TAG, Environment.getExternalStorageDirectory().getAbsolutePath());
        // /storage/emulated/0/Music
        Log.d(TAG, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());

        // /data/data/www.seven.com.testfilebrowser/files
        Log.d(TAG, getFilesDir().getAbsolutePath());

        // /data/data/www.seven.com.testfilebrowser/cache
        Log.d(TAG, getCacheDir().getAbsolutePath());

        // /data/data/www.seven.com.testfilebrowser/app_Test
        Log.d(TAG, getDir("Test", MODE_PRIVATE).getAbsolutePath());

        // /storage/emulated/0/Android/data/www.seven.com.testfilebrowser/cache
        Log.d(TAG, getExternalCacheDir().getAbsolutePath());

        // /storage/emulated/0/Android/data/www.seven.com.testfilebrowser/files
        // 需要处理空指针, 外部存储有可能被卸载掉
        Log.d(TAG, getExternalFilesDir(null).getAbsolutePath());

        // /storage/emulated/0/Android/data/www.seven.com.testfilebrowser/files/Music
        Log.d(TAG, getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath());

        // /storage/emulated/0/Android/data/www.seven.com.testfilebrowser/files
        // /storage/78E9-0BEE/Android/data/www.seven.com.testfilebrowser/files
        File[] cacheFiles  = ContextCompat.getExternalFilesDirs(this, null);

        ArrayList<File> files = new ArrayList<>();

        for (File f: cacheFiles) {
            if (f != null) {
                Log.d(TAG, f.getAbsolutePath());

                String fabs = f.getAbsolutePath();

                int StrAndroidIndex = fabs.indexOf("Android");
                String volume = fabs.substring(0, StrAndroidIndex);
                files.add(new File(volume));

            } else {
                // 如果不存在扩展的（外置）sdcard，那么返回null
                Log.d(TAG, "null");
            }
        }


        //...不靠谱在6.0上没有外接sdcard
//        List<StorageInfo> storages = StorageInfo.listAvaliableStorage(this);
//
//        ArrayList<File> files = new ArrayList<>();
//        for (StorageInfo info : storages) {
//
//            // StorageInfo{path='/storage/emulated/0', state='mounted', isRemoveable=false}
//            Log.d(TAG, info.toString());
//
//            if (info.isMounted()) {
//                files.add(new File(info.path));
//            }
//        }

        stacks.add(files);

        datas.clear();
        datas.addAll(files);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (stacks.size() <= 1) {
            super.onBackPressed();
        } else {

            stacks.remove(stacks.size() - 1);

            datas.clear();
            datas.addAll(stacks.get(stacks.size() - 1));

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(sdcardMountedRcv);
        super.onDestroy();
    }

    private BroadcastReceiver sdcardMountedRcv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stacks.clear();

            initStorage();
        }
    };
}
