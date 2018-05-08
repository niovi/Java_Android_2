package com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;


public class Gyroscope_Sensor {
    private static final String TAG = "Gyroscope  Sensor";
    public static final String flag_gyro_available = "Sensor.TYPE_GYROSCOPE Available";
    public static final String flag_gyro_not_available = "Sensor.TYPE_GYROSCOPE NOT Available";
    private int time_interval;

    private String Gyroscope_available, Gyroscope_available_old="0";

    private Double Gyroscope_reading_X=0.0;
    private Double Gyroscope_reading_X_old=0.0;
    private Double Gyroscope_threshold_X=0.0;

    private Double Gyroscope_reading_Y=0.0;
    private Double Gyroscope_reading_Y_old=0.0;
    private Double Gyroscope_threshold_Y=0.0;

    private Double Gyroscope_reading_Z=0.0;
    private Double Gyroscope_reading_Z_old=0.0;
    private Double Gyroscope_threshold_Z=0.0;

    private Handler handler;
    private boolean flag = false;

    private String Gyro_danger_X;
    private String Gyro_danger_Y;
    private String Gyro_danger_Z;

    private String Gyro_danger_X_old;
    private String Gyro_danger_Y_old;
    private String Gyro_danger_Z_old;


    private String check_gyroscope(Double reading, Double threshold, Context context){

        if(abs(reading)>threshold){

           // Log.i(TAG, "Gyroscope TRUE");


            //new OfflineNotify(context,time_interval);
            return "TRUE";
        }

        else { //Log.i(TAG, "Gyroscope FALSE");
                return "FALSE";}}



    public  Gyroscope_Sensor(final Context context){

        final Collision_Settings prefs5=Collision_Settings.getInstance(context);
        Gyroscope_threshold_X = Double.parseDouble(prefs5.getData("threshold_03_X"));
        Gyroscope_threshold_Y = Double.parseDouble(prefs5.getData("threshold_03_Y"));
        Gyroscope_threshold_Z = Double.parseDouble(prefs5.getData("threshold_03_Z"));
        time_interval =(int) Double.parseDouble(prefs5.getData("time_interval_03"));

        handler = new Handler(Looper.getMainLooper());




        final SensorEventListener GyroscopeSensorListener = new SensorEventListener() {

            //@Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }

            //@Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    if (flag) {
                    String gyro_reading_received_X=String.valueOf(event.values[0]);
                    String gyro_reading_received_Y=String.valueOf(event.values[1]);
                    String gyro_reading_received_Z=String.valueOf(event.values[2]);

                    Gyroscope_reading_X=Double.parseDouble(gyro_reading_received_X);
                    Gyroscope_reading_Y=Double.parseDouble(gyro_reading_received_Y);
                    Gyroscope_reading_Z=Double.parseDouble(gyro_reading_received_Z);


                        if(!Gyroscope_reading_X.equals(Gyroscope_reading_X_old)){
                            Gyroscope_reading_X_old=Gyroscope_reading_X;
                            Gyro_danger_X=check_gyroscope(Gyroscope_reading_X,Gyroscope_threshold_X,context);
                            prefs5.saveData("value_03_X",gyro_reading_received_X);
                            if(!Gyro_danger_X.equals(Gyro_danger_X_old)){
                                Gyro_danger_X_old=Gyro_danger_X;
                                prefs5.saveData("danger_03_X",Gyro_danger_X);
                            }
                        }
                        if(!Gyroscope_reading_Y.equals(Gyroscope_reading_Y_old)){
                            Gyroscope_reading_Y_old=Gyroscope_reading_Y;
                            Gyro_danger_Y=check_gyroscope(Gyroscope_reading_Y,Gyroscope_threshold_Y,context);
                            prefs5.saveData("value_03_Y",gyro_reading_received_Y);
                            if(!Gyro_danger_Y.equals(Gyro_danger_Y_old)){
                                Gyro_danger_Y_old=Gyro_danger_Y;
                                prefs5.saveData("danger_03_Y",Gyro_danger_Y);
                            }
                        }
                        if(!Gyroscope_reading_Z.equals(Gyroscope_reading_Z_old)){
                            Gyroscope_reading_Z_old=Gyroscope_reading_Z;
                            Gyro_danger_Z=check_gyroscope(Gyroscope_reading_Z,Gyroscope_threshold_Z,context);
                            prefs5.saveData("value_03_Z",gyro_reading_received_Z);
                            if(!Gyro_danger_Z.equals(Gyro_danger_Z_old)){
                                Gyro_danger_Z_old=Gyro_danger_Z;
                                prefs5.saveData("danger_03_Z",Gyro_danger_Z);
                            }
                        }


                        //Log.i(TAG,"New gyroscope value returned from sensor");


                        flag = false;}
                }}};


        final SensorManager mySensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        final Sensor GyroscopeSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (GyroscopeSensor != null) {
           // Log.i(TAG,"Gyroscope Sensor available");
            Gyroscope_available=flag_gyro_available;
            if(!Gyroscope_available.equals(Gyroscope_available_old)){
                Gyroscope_available_old=Gyroscope_available;
                prefs5.saveData("availability_03",Gyroscope_available);
            }

            mySensorManager.registerListener(GyroscopeSensorListener, GyroscopeSensor,time_interval);
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
            //Log.i(TAG,"Gyroscope Sensor not available");
            Gyroscope_available=flag_gyro_not_available;
            if(!Gyroscope_available.equals(Gyroscope_available_old)){
                Gyroscope_available_old=Gyroscope_available;
                prefs5.saveData("availability_03",Gyroscope_available);
            }

        }



        // handler for received data from service
        final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Collision_Settings.NEW_TIME_INTERVAL_03)) {
                    time_interval = (int)Double.parseDouble(intent.getStringExtra("time_interval_03_X"));


                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_03_X)) {
                    Gyroscope_threshold_X = Double.parseDouble(intent.getStringExtra("threshold_03_X"));
                    Gyro_danger_X=check_gyroscope(Gyroscope_reading_X,Gyroscope_threshold_X,context);
                    if(!Gyro_danger_X.equals(Gyro_danger_X_old)){
                        Gyro_danger_X_old=Gyro_danger_X;
                        prefs5.saveData("danger_03_X",Gyro_danger_X);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_03_Y)) {
                    Gyroscope_threshold_Y = Double.parseDouble(intent.getStringExtra("threshold_03_Y"));
                    Gyro_danger_Y=check_gyroscope(Gyroscope_reading_Y,Gyroscope_threshold_Y,context);
                    if(!Gyro_danger_Y.equals(Gyro_danger_Y_old)){
                        Gyro_danger_Y_old=Gyro_danger_Y;
                        prefs5.saveData("danger_03_Y",Gyro_danger_Y);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_03_Z)) {
                    Gyroscope_threshold_Z = Double.parseDouble(intent.getStringExtra("threshold_03_Z"));
                    Gyro_danger_Z=check_gyroscope(Gyroscope_reading_Z,Gyroscope_threshold_Z,context);
                    if(!Gyro_danger_Z.equals(Gyro_danger_Z_old)){
                        Gyro_danger_Z_old=Gyro_danger_Z;
                        prefs5.saveData("danger_03_Z",Gyro_danger_Z);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Collision_Settings.NEW_TIME_INTERVAL_03);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
        bm.registerReceiver(mBroadcastReceiver, filter);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(Collision_Settings.NEW_THRESHOLD_03_X);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);
        bm3.registerReceiver(mBroadcastReceiver, filter3);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(Collision_Settings.NEW_THRESHOLD_03_Y);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(context);
        bm4.registerReceiver(mBroadcastReceiver, filter4);

        IntentFilter filter5 = new IntentFilter();
        filter5.addAction(Collision_Settings.NEW_THRESHOLD_03_Z);
        LocalBroadcastManager bm5 = LocalBroadcastManager.getInstance(context);
        bm5.registerReceiver(mBroadcastReceiver, filter5);


    }




}
