package www.seven.com.takingphotoandcompress.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Seven on 2016/4/12.
 */
public class TakePhotoUtil {

    private static final String TAG = "TakePhotoUtil";

    private static final boolean DEBUG = true;

    private Activity activity;

    private File mImageDir;

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private String mCurrentPhotoPath;

    private TakePhotoInterface mTakePhotoImpl;

    public TakePhotoUtil(Activity activity) {
        this.activity = activity;

        mImageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        if (DEBUG) {
            Log.d(TAG, "default image directory is " + mImageDir.getAbsolutePath());
        }
    }

    // set the photo location
    public void setImageDir(File imgDir) {
        mImageDir = imgDir;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mImageDir;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        if (DEBUG) {
            Log.d(TAG, "generate a new file " + image.getAbsolutePath());
        }

        return image;
    }


    public void dispatchTakePictureIntent() {
        mCurrentPhotoPath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void dispatchTakePictureIntentWithExtra() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(TAG, "fail to create the File");

                if (mTakePhotoImpl != null) {
                    mTakePhotoImpl.onCreateFileError();
                }
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {


            if (mTakePhotoImpl != null) {
                mTakePhotoImpl.onGetFullSizePhoto(mCurrentPhotoPath);
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (DEBUG) {
                Log.d(TAG, "h = " + imageBitmap.getHeight() + " w = " + imageBitmap.getWidth());
            }

            if (mTakePhotoImpl != null) {
                mTakePhotoImpl.onGetIcon(imageBitmap);
            }
        }
    }

    public interface TakePhotoInterface {
        void onCreateFileError();
        void onGetIcon(Bitmap iconBmp);
        void onGetFullSizePhoto(String fullSizePath);
    }

    public void setTakePhotoListener(TakePhotoInterface takePhotoListener) {
        mTakePhotoImpl = takePhotoListener;
    }

    public Bitmap getBitmapForRequestSize(int reqHeight, int reqWidth) {
        // Get the dimensions of the View
        int targetW = reqWidth;
        int targetH = reqHeight;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        if (DEBUG) {
            Log.d(TAG, "h = " + bitmap.getHeight() + " w = " + bitmap.getWidth());
        }

        return bitmap;
    }

    public byte[] getBytesForRequestSize(int reqHeight, int reqWidth) {
        Bitmap bitmap = getBitmapForRequestSize(reqHeight, reqWidth);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return  baos.toByteArray();
    }
}
