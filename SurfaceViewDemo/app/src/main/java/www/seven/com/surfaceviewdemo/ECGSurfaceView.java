package www.seven.com.surfaceviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Seven on 2016/3/19.
 */
public class ECGSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private static final String TAG = ECGSurfaceView.class.getSimpleName();

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private boolean isDrawing;

    private Pair<Integer, Integer> xLimit;

    private int mPhase; // 相位

    private float T; // 周期

    private int mAmp; // 振幅

    private int mOffset; // Y偏移

    private void init() {
        // 设置callback
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

    }

    public ECGSurfaceView(Context context) {
        super(context);
        init();
    }

    public ECGSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ECGSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ECGSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;

        Log.d(TAG, "getMeasuredWidth = " + getMeasuredWidth());
        xLimit = new Pair<>(getMeasuredWidth(), getMeasuredWidth());
        mPhase = 0;
        T = getMeasuredWidth() / 2.0f;

        mAmp = getMeasuredHeight() / 4;
        mOffset = getMeasuredHeight() / 2;

        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            Log.d(TAG, "start draw");
            draw();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turnForword();
        }
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            // 清理之前的曲线
            mCanvas.drawColor(Color.WHITE);

            Path newSinPath = new Path();
            newSinPath.moveTo(xLimit.first, getY(xLimit.first));
            for (int i = xLimit.first + 1; i <= xLimit.second; ++i) {
                newSinPath.lineTo(i, getY(i));
            }

            mCanvas.drawPath(newSinPath, mPaint);

        } catch (Exception e) {

        } finally {
            // 在最后总是需要将画的内容提交
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private int getY(int x) {

        // 因为Y轴的方向是向下的所以需要翻转 加上负号
        return (int) (-mAmp * Math.sin(2*Math.PI/T* (x + mPhase)) + mOffset);
    }

    private void turnForword() {
        // 步长 10；
        int newFirst = xLimit.first - 10;
        if (newFirst < 0) {
            newFirst = 0;
        }

        xLimit = Pair.create(Integer.valueOf(newFirst), xLimit.second);

        mPhase += 10;
    }
}
