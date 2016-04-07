package www.seven.com.logdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import www.seven.com.logdemo.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = (ListView) findViewById(R.id.list);

        myList.setAdapter(new MyListAdapter(this));

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "MainActivity");
                // 通过这个打印出调用栈
                Exception exception = new Exception();
                StackTraceElement[] stes = exception.getStackTrace();
                for (StackTraceElement s: stes) {
                    Log.d(TAG, s.getClassName() + "==" + s.getMethodName() + "=="+s.getLineNumber());
                }
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter(Context context) {
            super(context, R.layout.item_list, new ArrayList<String>());
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list, parent, false);
        }
    }


}
