package www.seven.com.meiapp.main;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import www.seven.com.meiapp.R;
import www.seven.com.meiapp.main.mei.MeiFragment;
import www.seven.com.meiapp.main.mei.MeiPresenter;
import www.seven.com.meiapp.main.mei.data.MeiRemoteDataSources;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private PageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);

        tabLayout.addTab(tabLayout.newTab().setText("美").setIcon(R.mipmap.ic_launcher));
        tabLayout.addTab(tabLayout.newTab().setText("发现").setIcon(R.mipmap.ic_launcher));
        tabLayout.addTab(tabLayout.newTab().setText("我").setIcon(R.mipmap.ic_launcher));

        mAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


    }

    public class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MeiFragment meiFragment = new MeiFragment();

            new MeiPresenter(meiFragment, new MeiRemoteDataSources());

            return meiFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}
