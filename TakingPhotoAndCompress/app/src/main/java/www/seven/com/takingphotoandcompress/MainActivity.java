package www.seven.com.takingphotoandcompress;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import www.seven.com.takingphotoandcompress.util.TakePhotoUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    protected static final boolean DEBUG = true;

    private TakePhotoUtil mTakePhotoUtil;
    private Button takePhoto;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePhoto = findView(R.id.take_photo);
        photo = findView(R.id.photo);
        takePhoto.setOnClickListener(this);
        mTakePhotoUtil = new TakePhotoUtil(this);
        mTakePhotoUtil.setTakePhotoListener(mTakePhotoImpl);

        test();
    }

    public <T> T findView(@Nullable int id) {
        return (T)findViewById(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTakePhotoUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        mTakePhotoUtil.dispatchTakePictureIntentWithExtra();
    }

    private TakePhotoUtil.TakePhotoInterface mTakePhotoImpl = new TakePhotoUtil.TakePhotoInterface() {
        @Override
        public void onCreateFileError() {
            Toast.makeText(MainActivity.this, "onCreateFileError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGetIcon(Bitmap iconBmp) {
            Toast.makeText(MainActivity.this, "onGetIcon", Toast.LENGTH_SHORT).show();
            photo.setImageBitmap(iconBmp);
        }

        @Override
        public void onGetFullSizePhoto(String fullSizePath) {
            Toast.makeText(MainActivity.this, "onGetFullSizePhoto", Toast.LENGTH_SHORT).show();

            int height = photo.getHeight();
            int width = photo.getWidth();
            if (DEBUG) {
                Log.d(TAG, "reqH = " + height + " reqW = " + width);
                photo.setImageBitmap(mTakePhotoUtil.getBitmapForRequestSize(height, width));
            }
        }
    };

    private void test() {
        final TextView textView = findView(R.id.text);
        final String text = "这是很长的字符串，长度无法忍受的，这是很长的字符串，长度无法忍受的，这是很长的字符串，" +
                "长度无法忍受的，这是很长的字符串，长度无法忍受的，这是很长的字符串，长度无法忍受的，这是很长的" +
                "字符串，长度无法忍受的，这是很长的字符串，长度无法忍受的";
        textView.setText(text);


        textView.post(new Runnable() {
            @Override
            public void run() {
                StaticLayout staticLayout = new StaticLayout(text, textView.getPaint(), textView.getWidth(), Layout.Alignment.ALIGN_NORMAL, 0,0,
                        false);
                int line2 = staticLayout.getLineStart(2);
                int line3 = staticLayout.getLineStart(3);

                Log.d(TAG, line2 + " " + line3);
                Log.d(TAG, text.substring(line2,line2+1) + " " + text.substring(line3, line3+1));

                int line2Middle = (line2+line3)/2;
                String showStr = text.substring(0, line2Middle) + "..................................";

                textView.setText(showStr);
            }
        });


    }


}
