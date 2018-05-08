package com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools;

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


public class Linear_Accelerator_Sensor {

    private static final String TAG = "Linear Accelerator";
    public static final String flag_linear_available = "Sensor.TYPE_LINEAR_ACCELERATION Available";
    public static final String flag_linear_not_available = "Sensor.TYPE_LINEAR_ACCELERATION NOT Available";
    private int time_interval;

    private String Linear_Accerelator_available, Linear_Accelerator_available_old="0";

    private Double Linear_Accelerator_reading_X=0.0;
    private Double Linear_Accelerator_reading_X_old=0.0;
    private Double Linear_Accelerator_threshold_X=0.0;

    private Double Linear_Accelerator_reading_Y=0.0;
    private Double Linear_Accelerator_reading_Y_old=0.0;
    private Double Linear_Accelerator_threshold_Y=0.0;

    private Double Linear_Accelerator_reading_Z=0.0;
    private Double Linear_Accelerator_reading_Z_old=0.0;
    private Double Linear_Accelerator_threshold_Z=0.0;

    private Handler handler;
    private boolean flag = false;

    private String Linear_danger_X;
    private String Linear_danger_Y;
    private String Linear_danger_Z;

    private String Linear_danger_X_old;
    private String Linear_danger_Y_old;
    private String Linear_danger_Z_old;


    private String check_linear_accerelator(Double reading, Double threshold, Context context){

        if(abs(reading)>threshold){

            //Log.i(TAG, "Linear Accelerator TRUE");


            //new OfflineNotify(context,time_interval);
            return "TRUE";
        }

        else { //Log.i(TAG, "Linear Accelerator FALSE");
                return "FALSE";}}



    public  Linear_Accelerator_Sensor(final Context context){

        final Collision_Settings prefs5=Collision_Settings.getInstance(context);
        Linear_Accelerator_threshold_X = Double.parseDouble(prefs5.getData("threshold_04_X"));
        Linear_Accelerator_threshold_Y = Double.parseDouble(prefs5.getData("threshold_04_Y"));
        Linear_Accelerator_threshold_Z = Double.parseDouble(prefs5.getData("threshold_04_Z"));
        time_interval =(int) Double.parseDouble(prefs5.getData("time_interval_04"));

        handler = new Handler(Looper.getMainLooper());




        final SensorEventListener LinearAcceleratorSensorListener = new SensorEventListener() {

            //@Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }

            //@Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    if (flag) {
                        String linear_reading_received_X=String.valueOf(event.values[0]);
                        String linear_reading_received_Y=String.valueOf(event.values[1]);
                        String linear_reading_received_Z=String.valueOf(event.values[2]);

                        Linear_Accelerator_reading_X =Double.parseDouble(linear_reading_received_X);
                        Linear_Accelerator_reading_Y =Double.parseDouble(linear_reading_received_Y);
                        Linear_Accelerator_reading_Z =Double.parseDouble(linear_reading_received_Z);


                        if(!Linear_Accelerator_reading_X.equals(Linear_Accelerator_reading_X_old)){
                            Linear_Accelerator_reading_X_old=Linear_Accelerator_reading_X;
                            Linear_danger_X=check_linear_accerelator(Linear_Accelerator_reading_X, Linear_Accelerator_threshold_X,context);
                            prefs5.saveData("value_04_X",linear_reading_received_X);
                            if(!Linear_danger_X.equals(Linear_danger_X_old)){
                                Linear_danger_X_old=Linear_danger_X;
                                prefs5.saveData("danger_04_X",Linear_danger_X);
                            }
                        }
                        if(!Linear_Accelerator_reading_Y.equals(Linear_Accelerator_reading_Y_old)){
                            Linear_Accelerator_reading_Y_old=Linear_Accelerator_reading_Y;
                            Linear_danger_Y=check_linear_accerelator(Linear_Accelerator_reading_Y, Linear_Accelerator_threshold_Y,context);
                            prefs5.saveData("value_04_Y",linear_reading_received_Y);
                            if(!Linear_danger_Y.equals(Linear_danger_Y_old)){
                                Linear_danger_Y_old=Linear_danger_Y;
                                prefs5.saveData("danger_04_Y",Linear_danger_Y);
                            }
                        }
                        if(!Linear_Accelerator_reading_Z.equals(Linear_Accelerator_reading_Z_old)){
                            Linear_Accelerator_reading_Z_old=Linear_Accelerator_reading_Z;
                            Linear_danger_Z=check_linear_accerelator(Linear_Accelerator_reading_Z, Linear_Accelerator_threshold_Z,context);
                            prefs5.saveData("value_04_Z",linear_reading_received_Z);
                            if(!Linear_danger_Z.equals(Linear_danger_Z_old)){
                                Linear_danger_Z_old=Linear_danger_Z;
                                prefs5.saveData("danger_04_Z",Linear_danger_Z);
                            }
                        }

                        //Log.i(TAG,"New linear accelerator value returned from sensor");


                        flag = false;}
                }}};


        final SensorManager mySensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        final Sensor LinearAcceleratorSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (LinearAcceleratorSensor != null) {
            //Log.i(TAG,"Linear Accelerator Sensor available");
            Linear_Accerelator_available=flag_linear_available;
            if(!Linear_Accerelator_available.equals(Linear_Accelerator_available_old)){
                Linear_Accelerator_available_old=Linear_Accerelator_available;
                prefs5.saveData("availability_04",Linear_Accerelator_available);
            }

            mySensorManager.registerListener(LinearAcceleratorSensorListener, LinearAcceleratorSensor,SensorManager.SENSOR_DELAY_NORMAL);
            Runnable processSensors = new Runnable() {
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
            //Log.i(TAG,"Linear Accelerator Sensor not available");
            Linear_Accerelator_available=flag_linear_not_available;
            if(!Linear_Accerelator_available.equals(Linear_Accelerator_available_old)){
                Linear_Accelerator_available_old=Linear_Accerelator_available;
                prefs5.saveData("availability_04",Linear_Accerelator_available);
            }

        }



        // handler for received data from service
        final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Collision_Settings.NEW_TIME_INTERVAL_04)) {
                    time_interval = (int)Double.parseDouble(intent.getStringExtra("time_interval_04"));


                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_04_X)) {
                    Linear_Accelerator_threshold_X = Double.parseDouble(intent.getStringExtra("threshold_04_X"));
                    Linear_danger_X=check_linear_accerelator(Linear_Accelerator_reading_X, Linear_Accelerator_threshold_X,context);
                    if(!Linear_danger_X.equals(Linear_danger_X_old)){
                        Linear_danger_X_old=Linear_danger_X;
                        prefs5.saveData("danger_04_X",Linear_danger_X);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_04_Y)) {
                    Linear_Accelerator_threshold_Y = Double.parseDouble(intent.getStringExtra("threshold_04_Y"));
                    Linear_danger_Y=check_linear_accerelator(Linear_Accelerator_reading_Y, Linear_Accelerator_threshold_Y,context);
                    if(!Linear_danger_Y.equals(Linear_danger_Y_old)){
                        Linear_danger_Y_old=Linear_danger_Y;
                        prefs5.saveData("danger_04_Y",Linear_danger_Y);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_04_Z)) {
                    Linear_Accelerator_threshold_Z = Double.parseDouble(intent.getStringExtra("threshold_04_Z"));
                    Linear_danger_Z=check_linear_accerelator(Linear_Accelerator_reading_Z, Linear_Accelerator_threshold_Z,context);
                    if(!Linear_danger_Z.equals(Linear_danger_Z_old)){
                        Linear_danger_Z_old=Linear_danger_Z;
                        prefs5.saveData("danger_04_Z",Linear_danger_Z);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Collision_Settings.NEW_TIME_INTERVAL_04);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
        bm.registerReceiver(mBroadcastReceiver, filter);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(Collision_Settings.NEW_THRESHOLD_04_X);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);
        bm3.registerReceiver(mBroadcastReceiver, filter3);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(Collision_Settings.NEW_THRESHOLD_04_Y);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(context);
        bm4.registerReceiver(mBroadcastReceiver, filter4);

        IntentFilter filter5 = new IntentFilter();
        filter5.addAction(Collision_Settings.NEW_THRESHOLD_04_Z);
        LocalBroadcastManager bm5 = LocalBroadcastManager.getInstance(context);
        bm5.registerReceiver(mBroadcastReceiver, filter5);


    }




}
