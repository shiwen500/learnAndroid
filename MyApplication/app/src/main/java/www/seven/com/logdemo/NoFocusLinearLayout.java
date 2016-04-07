package www.seven.com.logdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Seven on 2016/3/31.
 */
public class NoFocusLinearLayout extends LinearLayout {
    public NoFocusLinearLayout(Context context) {
        super(context);
    }

    public NoFocusLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoFocusLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoFocusLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }
}
