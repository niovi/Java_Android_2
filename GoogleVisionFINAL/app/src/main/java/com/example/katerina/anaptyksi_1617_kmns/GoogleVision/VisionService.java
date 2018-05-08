package com.example.katerina.anaptyksi_1617_kmns.GoogleVision;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import android.os.IBinder;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings.GLOBAL_DANGER;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.PHOTO_URI;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.REMOTE_DANGER;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.VISION_AVAILABLE;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.VISION_STATUS;

public class VisionService extends Service {

    private static final String TAG = "VisionService";

    private static final String CLOUD_VISION_API_KEY = "AIzaSyDRnKicdTNurdOpNtOq0hU9scBtofaLKx8";
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";

    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    int sdk;
    NewCamera mNCamera;
    OldCamera mOCamera;
    TextToSpeech t1;



    public VisionService() {
        super();
    }




    @Override
    public void onCreate() {
        Log.i(TAG, "Vision Service onCreate");

    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        Log.i(TAG, "Vision Service onStartCommand");

        final ConnectivitySettings con_prefs=ConnectivitySettings.getInstance(getApplicationContext());



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mNCamera = new NewCamera(getApplicationContext());
            }else{
                mOCamera = new OldCamera(getApplicationContext());
            }




        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });






        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(Collision_Settings.GLOBAL_DANGER);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(getApplicationContext());

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(ConnectivitySettings.REMOTE_DANGER);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(getApplicationContext());

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(ConnectivitySettings.PHOTO_URI);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(getApplicationContext());


        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Collision_Settings.GLOBAL_DANGER)) {
                    final String param = intent.getStringExtra(GLOBAL_DANGER);
                    if(con_prefs.getData(VISION_AVAILABLE).equals("TRUE")&&con_prefs.getData(VISION_STATUS).equals("TRUE")) {
                        if (param.equals("TRUE")){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    Log.i(TAG, "New Local Danger: "+param+" vision is available: "+con_prefs.getData(VISION_AVAILABLE)+" with status: "+con_prefs.getData(VISION_STATUS));
                                    take_picture();

                                }
                            }).start();}
                    }

                }

                if (intent.getAction().equals(REMOTE_DANGER)) {

                    final String param = intent.getStringExtra(REMOTE_DANGER);
                    if(con_prefs.getData(VISION_AVAILABLE).equals("TRUE")&&con_prefs.getData(VISION_STATUS).equals("TRUE")) {
                        if (param.equals("SIMPLE")||param.equals("VERIFIED")){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, "New Remote Danger: "+param+" vision is available: "+con_prefs.getData(VISION_AVAILABLE)+" with status: "+con_prefs.getData(VISION_STATUS));
                                    take_picture();

                                }
                            }).start();}
                    }


                }

                if (intent.getAction().equals(PHOTO_URI)) {
                    final String param = intent.getStringExtra(PHOTO_URI);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            uploadImage(Uri.parse(param));

                        }
                    }).start();



                }

            }
        };


        bm2.registerReceiver(mBroadcastReceiver, filter2);
        bm3.registerReceiver(mBroadcastReceiver, filter3);
        bm4.registerReceiver(mBroadcastReceiver, filter4);






        return Service.START_STICKY;
    }





private void take_picture(){


    Log.i(TAG, "Taking Picture");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNCamera.take_picture();
        }else{mOCamera.take_picture();}



}








    //////// google vision

    public void uploadImage(Uri uri) {
        if (uri != null) {


            Log.i(TAG, "Uploading image ");

            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                1200);

                callCloudVision(bitmap);


            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
        }
    }

    private void callCloudVision(final Bitmap bitmap) throws IOException {
        // Switch text to loading
        // mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer =
                            new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                                /**
                                 * We override this so we can inject important identifying fields into the HTTP
                                 * headers. This enables use of a restricted cloud platform API key.
                                 */
                                @Override
                                protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                                        throws IOException {
                                    super.initializeVisionRequest(visionRequest);

                                    String packageName = getPackageName();
                                    visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                                    String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                                    visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                                }
                            };

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(1);
                            add(labelDetection);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " +
                            e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                Log.i(TAG,"Result label: "+result);
                t1.speak(result, TextToSpeech.QUEUE_FLUSH, null);

            }

        }.execute();
    }

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message=""; //= "I found these things:\n\n";

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message += String.format("%s", label.getDescription());

                message += "\n";
            }
        } else {
            message += "nothing";
        }

        return message;
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    @Override
    public void onDestroy() {
        Log.i(TAG, "Vision Service onDestroy");

        // release cameras

    }

}
