package www.seven.com.customview.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import www.seven.com.customview.R;

/**
 * Created by Seven on 2016/3/6.
 */
public class CircleView extends View {

    private int mPaintColor = Color.BLUE;

    // 抗锯齿
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mPaintColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.BLUE);
        typedArray.recycle();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mPaintColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.BLUE);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint.setColor(mPaintColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);

        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        if (wMode == MeasureSpec.AT_MOST) {
            wSize = 200;
        }

        if (hMode == MeasureSpec.AT_MOST) {
            hSize = 200;
        }

        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingBottom() - getPaddingTop();

        int radius = Math.min(width, height) / 2;

        canvas.drawCircle(getPaddingLeft() + width/2, getPaddingTop() + height/2, radius, mPaint);
    }
}
