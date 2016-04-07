package www.seven.com.viewpagerimpl.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Seven on 2016/3/3.
 */
public class ViewPagerImpl extends LinearLayout {

    public static final String TAG = "ViewPagerImpl";

    private View[] children;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;

    public ViewPagerImpl(Context context) {
        super(context);

        init();
    }

    public ViewPagerImpl(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ViewPagerImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerImpl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }


    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);


        final int count = getChildCount();
        children = new View[count];
        for (int i = 0; i < count; ++i) {

            children[i] = getChildAt(i);
        }

        removeAllViews();

        for (int i = 0; i < count; ++i) {

            View view  = children[i];
            FrameLayout fl = new FrameLayout(getContext());
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(measureWidth, measureHeight);
            fl.setLayoutParams(lp);
            fl.addView(view);

            addView(fl);

            children[i] = fl;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intercept = false;

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercept = false;

                // 如果父view仍然在滑动，那么接下来的事件，仍然是父view处理
                // 在处理下次的事件之前，先停止当前动画
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }

        mLastInterceptY = y;
        mLastInterceptX = x;

        // 有可能在一个事件序列的中途拦截事件，所以要记录拦截时的位置
        mLastX = x;
        mLastY = y;

        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        mVelocityTracker.addMovement(ev);

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                scrollBy(-deltaX, 0);

                break;
            }
            case MotionEvent.ACTION_UP: {

//                int scrollX = getScrollX();
//                mVelocityTracker.computeCurrentVelocity(200);
//                float vx = mVelocityTracker.getXVelocity();
//                if (Math.abs(vx) >= 50) {
//
//                } else {
//
//                }

                smoothScrollTo(getCurrentIndex());


                break;
            }
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    private int getCurrentIndex() {
        int scrollX = getScrollX();
        int delta = getMeasuredWidth() / 2;

        return (scrollX + delta) / getMeasuredWidth();
    }

    private void smoothScrollTo(int index) {

        int desScrollX = index * getMeasuredWidth();
        int scrollX = getScrollX();

        int dx = desScrollX - scrollX;
        smoothScrollBy(dx, 0);
    }

    private void smoothScrollBy(int dx, int dy) {

        if (dx == 0) {
            return ;
        }

        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);

        // 会触发computeScroll函数
        invalidate();
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 限制滑动的范围
     * @param x 最小为0，最大为(childCount - 1) * width
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {

        final int childCount = children.length;
        final int minX = 0;
        final int maxX = getWidth() * (childCount - 1);

        Log.d(TAG, "minX = " + minX + ",maxX = " + maxX);

        if (x < minX) {
            x = minX;
        } else if (x > maxX) {
            x = maxX;
        }

        super.scrollTo(x, y);
    }
}
