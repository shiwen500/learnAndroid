package www.seven.com.takingphotoandcompress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        mTakePhotoUtil.dispatchTakePictureIntent();
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
}
