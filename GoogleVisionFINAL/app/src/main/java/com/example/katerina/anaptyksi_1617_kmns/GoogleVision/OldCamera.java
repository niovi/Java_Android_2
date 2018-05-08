package com.example.katerina.anaptyksi_1617_kmns.GoogleVision;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.PHOTO_URI;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.VISION_AVAILABLE;


public class OldCamera {

    private static final String TAG = "Old Camera";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    static  ConnectivitySettings consets;
    Camera mCamera;


    //////   old camera


    public OldCamera(Context context){

        consets=ConnectivitySettings.getInstance(context);

        if(getOldCamera(context)&&isNetworkConnected(context)){
            consets.saveData(VISION_AVAILABLE,"TRUE");
        } else consets.saveData(VISION_AVAILABLE,"FALSE");
    }



    private Boolean getOldCamera(Context context){
        if(checkCameraHardware(context)){

            mCamera = getCameraInstance();
            return true;

        }else {
            return false;}

    }

    public boolean isNetworkConnected(Context context) {


        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.i(TAG, "Internet "+isConnected);

        return isConnected;

    }

    public void take_picture(){


        mCamera.takePicture(null, null, mPicture);


    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {



            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }


            Uri photo_uri = Uri.fromFile(pictureFile);
            consets.saveData(PHOTO_URI,String.valueOf(photo_uri));
        }
    };


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(1); // attempt to get a Camera instance
        }
        catch (Exception e){
            consets.saveData(VISION_AVAILABLE,"FALSE");
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            consets.saveData(VISION_AVAILABLE,"FALSE");
            // no camera on this device
            return false;
        }
    }



}
