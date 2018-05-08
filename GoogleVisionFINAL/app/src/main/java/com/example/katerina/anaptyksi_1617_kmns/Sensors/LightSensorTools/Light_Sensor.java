package com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools;

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




public class Light_Sensor {
    private static final String TAG = "Light Sensor";
    public static final String flag_light_available = "Sensor.TYPE_LIGHT Available";
    public static final String flag_light_not_available = "Sensor.TYPE_LIGHT NOT Available";
    private Double light_reading=0.0;
    private Double light_reading_old=0.0;
    private Double light_threshold=0.0;
    private int time_interval; // ms
    private Handler handler;
    private boolean flag = false;
    private String Light_danger;
    private String Light_danger_old;
    private String light_available,light_available_old="0";


    private String check_light(Context context){


            if (light_reading < light_threshold) {

                //Log.i(TAG, "LIGHT TRUE");


                //new OfflineNotify(context,time_interval);
                return "TRUE";
            }

        else {  //Log.i(TAG, "LIGHT FALSE");
                return "FALSE";}

    }




    public  Light_Sensor(final Context context){


        handler = new Handler(Looper.getMainLooper());
        final Collision_Settings prefs3=Collision_Settings.getInstance(context);
        time_interval =(int) Double.parseDouble(prefs3.getData("time_interval_01"));
        light_threshold = Double.parseDouble(prefs3.getData("threshold_01"));



        final SensorEventListener LightSensorListener = new SensorEventListener() {

        //@Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        //@Override
        public void onSensorChanged(SensorEvent event) {
            if (flag) {

            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

                String light_reading_received=String.valueOf(event.values[0]);
                light_reading=Double.parseDouble(light_reading_received);

                //Log.i(TAG,"New light value returned from sensor");
                if(!light_reading.equals(light_reading_old)){
                    light_reading_old=light_reading;
                    Light_danger=check_light(context);
                    prefs3.saveData("value_01",light_reading_received);
                    if(!Light_danger.equals(Light_danger_old)){
                        Light_danger_old=Light_danger;
                        prefs3.saveData("danger_01",Light_danger);
                    }
                }



                flag = false;}

            }}

        };


    final SensorManager mySensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

    final Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    if (LightSensor != null) {
        //Log.i(TAG,"Light Sensor available");
        light_available=flag_light_available;
        if(!light_available.equals(light_available_old)){
            prefs3.saveData("availability_01",light_available);
            light_available_old=light_available;
        }

        mySensorManager.registerListener(LightSensorListener, LightSensor,SensorManager.SENSOR_DELAY_NORMAL);
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
        //Log.i(TAG,"Light Sensor not available");
        light_available=flag_light_not_available;
        if(!light_available.equals(light_available_old)){
            prefs3.saveData("availability_01",light_available);
            light_available_old=light_available;
        }

    }






        // handler for received data from service
        final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Collision_Settings.NEW_TIME_INTERVAL_01)) {
                    time_interval =(int) Double.parseDouble(intent.getStringExtra("time_interval_01"));


                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_01)) {
                    light_threshold = Double.parseDouble(intent.getStringExtra("threshold_01"));
                    Light_danger=check_light(context);
                    if(!Light_danger.equals(Light_danger_old)){
                        Light_danger_old=Light_danger;
                        prefs3.saveData("danger_01",Light_danger);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Collision_Settings.NEW_TIME_INTERVAL_01);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
        bm.registerReceiver(mBroadcastReceiver, filter);


        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(Collision_Settings.NEW_THRESHOLD_01);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(context);
        bm2.registerReceiver(mBroadcastReceiver, filter2);




}}











