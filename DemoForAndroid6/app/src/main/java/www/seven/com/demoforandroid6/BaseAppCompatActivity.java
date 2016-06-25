package www.seven.com.demoforandroid6;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Seven on 2016/4/23.
 */
public class BaseAppCompatActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
