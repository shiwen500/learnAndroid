package www.seven.com.scrollviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Seven on 2016/3/2.
 */
public class CustomTextView extends TextView {

    public static final String TAG = "CustomTextView";

    private int mLastX;
    private int mLastY;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawy = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE: {
                int deltax = rawX - mLastX;
                int deltay = rawy - mLastY;

                Log.d(TAG, "move deltax = " + deltax + " deltay = " + deltay);

                int translationX = (int)ViewHelper.getTranslationX(this) + deltax;
                int translationY = (int)ViewHelper.getTranslationY(this) + deltay;

                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }

        mLastX = rawX;
        mLastY = rawy;

        return true;
    }
}
