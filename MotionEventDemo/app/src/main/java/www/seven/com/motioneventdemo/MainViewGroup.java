package www.seven.com.motioneventdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sunyun004_macmini on 16/3/3.
 */
public class MainViewGroup extends LinearLayout {

    private static final String TAG = "MainViewGroup";

    public MainViewGroup(Context context) {
        super(context);
    }

    public MainViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MainViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean r = super.dispatchTouchEvent(ev);

        Log.d(TAG, "dispatchTouchEvent 消耗事件? " + r);

        return r;
    }

    int i = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean r = super.onTouchEvent(event);
        return r;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean r = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent 拦截事件? " + r);
        return r;
    }
}
