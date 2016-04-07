package www.seven.com.canvasdemo.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Seven on 2016/3/26.
 */
public class HardFrameLayout extends FrameLayout {

    private int originWidth;
    private int originHeight;

    private boolean isHard;

    public HardFrameLayout(Context context) {
        super(context);
    }

    public HardFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HardFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HardFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        ViewGroup.LayoutParams lp = getLayoutParams();

        if (!isHard) {
            originHeight = lp.height;
            originWidth = lp.width;
        }


    }

    public void hardIt() {
        isHard = true;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        lp.width = width;
    }

    public void rehard() {
        isHard = false;
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = originHeight;
        lp.width = originWidth;

        requestLayout();
    }
}
