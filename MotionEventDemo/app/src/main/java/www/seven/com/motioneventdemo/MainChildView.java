package www.seven.com.motioneventdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sunyun004_macmini on 16/3/3.
 */
public class MainChildView extends View {

    private static final String TAG = "MainChildView";

    public MainChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MainChildView(Context context) {
        super(context);
        init();
    }

    public MainChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MainChildView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setClickable(true);

        setOnTouchListener(onTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean r = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent 消耗事件? " + r);
        return r;
    }

    /**
     * 如果onTouchListener返回了true
     * 那么这个函数不会被调用
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean r = super.onTouchEvent(event);
        Log.d(TAG, "onTouchEvent 消耗事件? " + r);
        return r;
    }

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouchListener 返回ture");
            return true;
        }
    };
}
