package com.thinkcool.statusbaradapt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by thinkcool on 2015/11/22.
 */
public class MainActivity extends BaseActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //让toolbar同actionbar一样使用,include自定义的topbar时注释到下面两句
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    public void showSec(View view) {
        Intent intent = new Intent(this, ToolBarActivity.class);
        startActivity(intent);
    }
}
