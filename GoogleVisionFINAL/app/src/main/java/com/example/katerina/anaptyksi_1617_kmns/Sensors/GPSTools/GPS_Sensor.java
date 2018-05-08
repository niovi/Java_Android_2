package com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;

import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.GPS_STATUS;


public class GPS_Sensor {

    private static final String TAG = "GPS";
    public static final String flag_gps_available = "Location Manager Available";
    public static final String flag_gps_not_available = "Location Manager NOT Available";

    private int time_interval; // ms
    private Handler handler;
    private boolean flag = false;

    String GPS_available, GPS_available_old="0";

    private Double gps_longitude=0.0;
    private Double gps_latitude=0.0;
    private Double gps_longitude_old=0.0;
    private Double gps_latitude_old=0.0;
    private Double gps_thres_longitude_a=0.0;
    private Double gps_thres_latitude_a=0.0;
    private Double gps_thres_longitude_b=0.0;
    private Double gps_thres_latitude_b=0.0;

    private String danger_long;
    private String danger_lat;
    private String danger_long_old;
    private String danger_lat_old;

    private String check_gps(Double reading_X, Double threshold_X_a,Double threshold_X_b,Double reading_Y, Double threshold_Y_a,Double threshold_Y_b, Context context) {

        if (((reading_X > threshold_X_a && reading_X < threshold_X_b)||(reading_X < threshold_X_a && reading_X > threshold_X_b) )
             && ((reading_Y > threshold_Y_a && reading_Y < threshold_Y_b)||(reading_Y < threshold_Y_a && reading_Y > threshold_Y_b) ))  {

            //Log.i(TAG, "GPS TRUE");
            // SAFE ZONE
            return "FALSE";

        } else {//Log.i(TAG, "GPS FALSE");
            //new OfflineNotify(context, time_interval);
            return "TRUE";
        }
    }


    public GPS_Sensor(final Context context) {


        final Collision_Settings prefs5 = Collision_Settings.getInstance(context);
        final ConnectivitySettings prefs6 = ConnectivitySettings.getInstance(context);
        gps_thres_longitude_a = Double.parseDouble(prefs5.getData("threshold_06_X_A"));
        gps_thres_latitude_a = Double.parseDouble(prefs5.getData("threshold_06_Y_A"));
        gps_thres_longitude_b = Double.parseDouble(prefs5.getData("threshold_06_X_B"));
        gps_thres_latitude_b = Double.parseDouble(prefs5.getData("threshold_06_Y_B"));
        time_interval = (int) Double.parseDouble(prefs5.getData("time_interval_06"));

        handler = new Handler(Looper.getMainLooper());


        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null && locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            //Log.i(TAG, "GPS available");
            GPS_available = flag_gps_available;
            if(!GPS_available.equals(GPS_available_old)){
                GPS_available_old=GPS_available;
                prefs5.saveData("availability_06", GPS_available);
            }


            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    if (flag) {



                            String gps_longitude_received=String.valueOf( location.getLatitude());
                            String gps_latitude_received=String.valueOf(location.getLongitude());

                            gps_longitude=Double.parseDouble(gps_longitude_received);
                            gps_latitude=Double.parseDouble(gps_latitude_received);

                            if(!gps_longitude.equals(gps_longitude_old)){
                                gps_longitude_old=gps_longitude;
                                danger_long=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                                prefs5.saveData("value_06_X",gps_longitude_received);
                                if(!danger_long.equals(danger_long_old)){
                                    danger_long_old=danger_long;
                                    prefs5.saveData("danger_06_X",danger_long);
                                }
                            }

                            if(!gps_latitude.equals(gps_latitude_old)){
                                gps_latitude_old=gps_latitude;
                                danger_lat=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                                prefs5.saveData("value_06_Y",gps_latitude_received);
                                if(!danger_lat.equals(danger_lat_old)){
                                    danger_lat_old=danger_lat;
                                    prefs5.saveData("danger_06_Y",danger_lat);
                                }

                            }
                            //Log.i(TAG,"New GPS value returned");
                            flag = false;}

                    }





                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    prefs6.saveData(GPS_STATUS,"TRUE");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    prefs6.saveData(GPS_STATUS,"FALSE");
                }
            };
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener,Looper.getMainLooper());


            Runnable processSensors;
            processSensors = new Runnable() {
                @Override
                public void run() {
                    // Do work with the sensor values.

                    flag = true;
                    // The Runnable is posted to run again here:
                    handler.postDelayed(this, time_interval);
                }
            };
            handler.post(processSensors);


        } else {
            //Log.i(TAG,"GPS not available");
            GPS_available=flag_gps_not_available;
            if(!GPS_available.equals(GPS_available_old)){
                GPS_available_old=GPS_available;
                prefs5.saveData("availability_06",GPS_available);
            }


        }




        // handler for received data from service
        final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Collision_Settings.NEW_TIME_INTERVAL_06)) {
                    time_interval =(int) Double.parseDouble(intent.getStringExtra("time_interval_06"));


                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_06_X_A)) {
                    gps_thres_longitude_a= Double.parseDouble(intent.getStringExtra("threshold_06_X_A"));
                    danger_long=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                    if(!danger_long.equals(danger_long_old)){
                        danger_long_old=danger_long;
                        prefs5.saveData("danger_06_X",danger_long);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_06_Y_A)) {
                    gps_thres_latitude_a = Double.parseDouble(intent.getStringExtra("threshold_06_Y_A"));
                    danger_lat=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                    if(!danger_lat.equals(danger_lat_old)){
                        danger_lat_old=danger_lat;
                        prefs5.saveData("danger_06_Y",danger_lat);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_06_X_B)) {
                    gps_thres_longitude_b = Double.parseDouble(intent.getStringExtra("threshold_06_X_B"));
                    danger_long=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                    if(!danger_long.equals(danger_long_old)){
                        danger_long_old=danger_long;
                        prefs5.saveData("danger_06_X",danger_long);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_06_Y_B)) {
                    gps_thres_latitude_b = Double.parseDouble(intent.getStringExtra("threshold_06_Y_B"));
                    danger_lat=check_gps(gps_longitude,gps_thres_longitude_a, gps_thres_longitude_b,gps_latitude,gps_thres_latitude_a, gps_thres_latitude_b,context);
                    if(!danger_lat.equals(danger_lat_old)){
                        danger_lat_old=danger_lat;
                        prefs5.saveData("danger_06_Y",danger_lat);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Collision_Settings.NEW_TIME_INTERVAL_06);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
        bm.registerReceiver(mBroadcastReceiver, filter);


        IntentFilter filter2xa = new IntentFilter();
        filter2xa.addAction(Collision_Settings.NEW_THRESHOLD_06_X_A);
        LocalBroadcastManager bm2xa = LocalBroadcastManager.getInstance(context);
        bm2xa.registerReceiver(mBroadcastReceiver, filter2xa);

        IntentFilter filter2xb = new IntentFilter();
        filter2xb.addAction(Collision_Settings.NEW_THRESHOLD_06_X_B);
        LocalBroadcastManager bm2xb = LocalBroadcastManager.getInstance(context);
        bm2xb.registerReceiver(mBroadcastReceiver, filter2xb);

        IntentFilter filter2ya = new IntentFilter();
        filter2ya.addAction(Collision_Settings.NEW_THRESHOLD_06_Y_A);
        LocalBroadcastManager bm2ya = LocalBroadcastManager.getInstance(context);
        bm2ya.registerReceiver(mBroadcastReceiver, filter2ya);

        IntentFilter filter2yb = new IntentFilter();
        filter2yb.addAction(Collision_Settings.NEW_THRESHOLD_06_Y_B);
        LocalBroadcastManager bm2yb = LocalBroadcastManager.getInstance(context);
        bm2yb.registerReceiver(mBroadcastReceiver, filter2yb);




    }



}
