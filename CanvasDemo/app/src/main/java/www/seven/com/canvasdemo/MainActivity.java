package www.seven.com.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import www.seven.com.canvasdemo.customview.HardFrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(R.id.hard).setOnClickListener(this);
        findViewById(R.id.rehard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HardFrameLayout pa = (HardFrameLayout) findViewById(R.id.input).getParent();
        if (v.getId() == R.id.remove) {

            pa.removeView(findViewById(R.id.input));
        } else if (v.getId() == R.id.hard) {
            HardFrameLayout hardFrameLayout = pa;
            hardFrameLayout.hardIt();
        } else if (v.getId() == R.id.rehard) {
            pa.rehard();
        }
    }
}
