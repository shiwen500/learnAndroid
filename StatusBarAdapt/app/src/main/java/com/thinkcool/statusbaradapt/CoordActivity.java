package com.thinkcool.statusbaradapt;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Seven on 2016/4/7.
 */
public class CoordActivity extends BaseActivity {

    private WebView webView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coord);

        // android 4.4 statusbar 颜色透明，需要改变之
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
            changeStatusBarColor();
        }


        webView = (WebView) findViewById(R.id.webview);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=78040160_36_pg&wd=android&rsv_pq=acefdd30000392e7&rsv_t=ef96IvUp7PoUc61oOMuf8/AmsFpBMxQjQiLSYu6tinq+BZxHyrOEFXBVNDXDnMNB/W//eXQ&rsv_enter=1&rsv_sug3=8&rsv_sug1=5&rsv_sug7=100&rsv_sug2=0&inputT=3633&rsv_sug4=3905&rsv_sug=2");
    }
}
