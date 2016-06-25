package www.seven.com.demoforandroid6.Animator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import www.seven.com.demoforandroid6.BaseAppCompatActivity;
import www.seven.com.demoforandroid6.R;

/**
 * Created by Seven on 2016/4/23.
 */
public class EnterExitAnimatorActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enterexitanimator);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("进入退出动画");
    }
}
