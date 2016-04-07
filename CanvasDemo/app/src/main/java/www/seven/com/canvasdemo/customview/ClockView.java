package www.seven.com.canvasdemo.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Seven on 2016/3/17.
 */
public class ClockView extends View {
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getMeasureSpec(widthMeasureSpec), getMeasureSpec(heightMeasureSpec));
    }

    private int getMeasureSpec(int measureSpce) {
        int size = MeasureSpec.getSize(measureSpce);
        int mode = MeasureSpec.getMode(measureSpce);

        switch (mode) {
            case MeasureSpec.EXACTLY: {
                return measureSpce;
            }
            case MeasureSpec.AT_MOST: {
                size = size > 200? 200 : size;
                return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            }
        }

        return measureSpce;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int radius = ( width > height ? height : width ) / 2 - 5;

        // 画圆
        Paint circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(5.0f);
        canvas.drawCircle(width / 2, height / 2, radius, circle);
        canvas.save();

        canvas.translate(width / 2, height / 2);
        canvas.rotate(30);
        Paint degree = new Paint(Paint.ANTI_ALIAS_FLAG);
        degree.setStyle(Paint.Style.STROKE);
        for (int i = 1; i <= 12; ++i) { // 24小时

            if (i%3 == 0) {
                degree.setStrokeWidth(5.0f);
                degree.setTextSize(30);

                canvas.drawLine(0, -radius, 0, -radius + 60, degree); // 画刻度线

                String text = String.valueOf(i);
                float textLen = degree.measureText(text);

                canvas.drawText(text, -textLen / 2, -radius + 90, degree); // 写字

            } else {

                degree.setStrokeWidth(2.0f);
                degree.setTextSize(12);

                canvas.drawLine(0, -radius, 0, -radius + 30, degree); // 画刻度线

                String text = String.valueOf(i);
                float textLen = degree.measureText(text);

                canvas.drawText(text, -textLen / 2, -radius + 60, degree); // 写字

            }

            canvas.rotate(30);
        }

        canvas.restore(); // 坐标重置了,所以需要重新平移

        canvas.translate(width / 2, height / 2);

        // 画指针
        Paint hour = new Paint(Paint.ANTI_ALIAS_FLAG);
        hour.setStrokeWidth(20);

        Paint minu = new Paint(Paint.ANTI_ALIAS_FLAG);
        minu.setStrokeWidth(10);

        canvas.drawLine(0, 0, 100, 0, hour);
        canvas.drawLine(0, 0, 100, 100, minu);

        canvas.restore();
    }
}
