package www.seven.com.banner;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 参考自： http://blog.csdn.net/singwhatiwanna/article/details/46541225
 */

public class MainActivity extends AppCompatActivity {

    private ViewPager mPager;

    private static final int DEFAULT_PAGE_SIZE = 5;

    // 这个值要足够大，尽量避免 页面达到最大值。
    // 必须是 DEFAULT_PAGE_SIZE 的倍数
    private static final int MAX_PAGE_SIZE = 10000;
    private static final String TAG = "MainActivity";

    private int mBannerIndex;

    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return MAX_PAGE_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position %= DEFAULT_PAGE_SIZE;
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.page_item, container, false);

            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "click banner item :" + pos, Toast.LENGTH_SHORT).show();
                }
            });

            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(String.valueOf(position));
            Log.d(TAG, "position = " + position);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        /**
         * 滑动是周期性循环的，所以，viewpager的真实size, 应该是显示出来的size的正数倍数，而且是2倍以上。
         * 这里可以将100改成10
         * @param container
         */

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mPager.getCurrentItem();
            Log.d(TAG, "finish update before, position=" + position);
            if (position == 0) {
                position = DEFAULT_PAGE_SIZE;
                mPager.setCurrentItem(position, false);
            } else if (position == MAX_PAGE_SIZE - 1) {
                position = DEFAULT_PAGE_SIZE - 1;
                mPager.setCurrentItem(position, false);
            }

            // 时刻记录当前的位置
            mBannerIndex = position;
            Log.d(TAG, "finish update after, position=" + position);
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 更新ui线程
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            mPager.setCurrentItem(mBannerIndex + 1, true);
        }
    };

    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(update);
        }
    };

    private Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(mPageChangeListener);

        mTimer.schedule(mTask, 3000, 3000);
    }
}
