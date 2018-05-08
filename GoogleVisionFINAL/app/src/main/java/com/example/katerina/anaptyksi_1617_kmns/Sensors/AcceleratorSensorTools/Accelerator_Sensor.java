package com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools;

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
import android.util.Log;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;


public class Accelerator_Sensor {

    private static final String TAG = "Accelerator";
    public static final String flag_accel_available = "Sensor.TYPE_ACCELEROMETER Available";
    public static final String flag_accel_not_available = "Sensor.TYPE_ACCELEROMETER NOT Available";
    private int time_interval;

    private Double Accelerator_reading_X=0.0;
    private Double Accelerator_reading_X_old=0.0;
    private Double Accelerator_threshold_X=0.0;

    private Double Accelerator_reading_Y=0.0;
    private Double Accelerator_reading_Y_old=0.0;
    private Double Accelerator_threshold_Y=0.0;

    private Double Accelerator_reading_Z=0.0;
    private Double Accelerator_reading_Z_old=0.0;
    private Double Accelerator_threshold_Z=0.0;

    private String Accel_danger_X;
    private String Accel_danger_Y;
    private String Accel_danger_Z;

    private String Accel_danger_X_old;
    private String Accel_danger_Y_old;
    private String Accel_danger_Z_old;

    private Handler handler;
    private boolean flag = false;

    String Accelerator_available, Accelerator_available_old="0";


    private String check_accelerator(Double reading, Double threshold, Context context){

        if(abs(reading)>threshold){

            //Log.i(TAG, "Accelerator TRUE");
            return "TRUE";


            //new OfflineNotify(context,time_interval);
        }

        else  { //Log.i(TAG, "Accelerator FALSE");
                return "FALSE";}

    }



    public Accelerator_Sensor(final Context context){

        final Collision_Settings prefs5=Collision_Settings.getInstance(context);
        Accelerator_threshold_X = Double.parseDouble(prefs5.getData("threshold_05_X"));
        Accelerator_threshold_Y = Double.parseDouble(prefs5.getData("threshold_05_Y"));
        Accelerator_threshold_Z = Double.parseDouble(prefs5.getData("threshold_05_Z"));
        time_interval =(int) Double.parseDouble(prefs5.getData("time_interval_05"));

        handler = new Handler(Looper.getMainLooper());




        final SensorEventListener AcceleratorSensorListener = new SensorEventListener() {

            //@Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }

            //@Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    if (flag) {
                        String accel_reading_received_X=String.valueOf(event.values[0]);
                        String accel_reading_received_Y=String.valueOf(event.values[1]);
                        String accel_reading_received_Z=String.valueOf(event.values[2]);

                        Accelerator_reading_X =Double.parseDouble(accel_reading_received_X);
                        Accelerator_reading_Y =Double.parseDouble(accel_reading_received_Y);
                        Accelerator_reading_Z =Double.parseDouble(accel_reading_received_Z);

                        if(!Accelerator_reading_X.equals(Accelerator_reading_X_old)){
                            Accelerator_reading_X_old=Accelerator_reading_X;
                            Accel_danger_X= check_accelerator(Accelerator_reading_X, Accelerator_threshold_X,context);
                            prefs5.saveData("value_05_X",accel_reading_received_X);
                            if(!Accel_danger_X.equals(Accel_danger_X_old)){
                                Accel_danger_X_old=Accel_danger_X;
                                prefs5.saveData("danger_05_X",Accel_danger_X);
                            }
                        }

                        if(!Accelerator_reading_Y.equals(Accelerator_reading_Y_old)){
                            Accelerator_reading_Y_old=Accelerator_reading_Y;
                            Accel_danger_Y= check_accelerator(Accelerator_reading_Y, Accelerator_threshold_Y,context);
                            prefs5.saveData("value_05_Y",accel_reading_received_Y);
                            if(!Accel_danger_Y.equals(Accel_danger_Y_old)){
                                Accel_danger_Y_old=Accel_danger_Y;
                                prefs5.saveData("danger_05_Y",Accel_danger_Y);
                            }
                        }

                        if(!Accelerator_reading_Z.equals(Accelerator_reading_Z_old)){
                            Accelerator_reading_Z_old=Accelerator_reading_Z;
                            Accel_danger_Z= check_accelerator(Accelerator_reading_Z, Accelerator_threshold_Z,context);
                            prefs5.saveData("value_05_Z",accel_reading_received_Z);
                            if(!Accel_danger_Z.equals(Accel_danger_Z_old)){
                                Accel_danger_Z_old=Accel_danger_Z;
                                prefs5.saveData("danger_05_Z",Accel_danger_Z);
                            }
                        }

                        //Log.i(TAG,"New accelerator value returned from sensor");

                        flag = false;}
                }}};


        final SensorManager mySensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        final Sensor AcceleratorSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (AcceleratorSensor != null) {
            Log.i(TAG,"Accelerator Sensor available");
            Accelerator_available=flag_accel_available;
            if(!Accelerator_available.equals(Accelerator_available_old)){
                Accelerator_available_old=Accelerator_available;
                prefs5.saveData("availability_05",Accelerator_available);
            }

            mySensorManager.registerListener(AcceleratorSensorListener, AcceleratorSensor,SensorManager.SENSOR_DELAY_NORMAL);
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
            Log.i(TAG,"Linear Accelerator Sensor not available");
            Accelerator_available=flag_accel_not_available;
            if(!Accelerator_available.equals(Accelerator_available_old)){
                Accelerator_available_old=Accelerator_available;
                prefs5.saveData("availability_05",Accelerator_available);
            }

        }



        // handler for received data from service
        final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Collision_Settings.NEW_TIME_INTERVAL_05)) {
                    time_interval = (int)Double.parseDouble(intent.getStringExtra("time_interval_05"));


                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_05_X)) {
                    Accelerator_threshold_X = Double.parseDouble(intent.getStringExtra("threshold_05_X"));
                    Accel_danger_X= check_accelerator(Accelerator_reading_X, Accelerator_threshold_X,context);
                    if(!Accel_danger_X.equals(Accel_danger_X_old)){
                        Accel_danger_X_old=Accel_danger_X;
                        prefs5.saveData("danger_05_X",Accel_danger_X);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_05_Y)) {
                    Accelerator_threshold_Y = Double.parseDouble(intent.getStringExtra("threshold_05_Y"));
                    Accel_danger_Y= check_accelerator(Accelerator_reading_Y, Accelerator_threshold_Y,context);
                    if(!Accel_danger_Y.equals(Accel_danger_Y_old)){
                        Accel_danger_Y_old=Accel_danger_Y;
                        prefs5.saveData("danger_05_Y",Accel_danger_Y);
                    }
                }
                if (intent.getAction().equals(Collision_Settings.NEW_THRESHOLD_05_Z)) {
                    Accelerator_threshold_Z = Double.parseDouble(intent.getStringExtra("threshold_05_Z"));
                    Accel_danger_Z= check_accelerator(Accelerator_reading_Z, Accelerator_threshold_Z,context);
                    if(!Accel_danger_Z.equals(Accel_danger_Z_old)){
                        Accel_danger_Z_old=Accel_danger_Z;
                        prefs5.saveData("danger_05_Z",Accel_danger_Z);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Collision_Settings.NEW_TIME_INTERVAL_05);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);
        bm.registerReceiver(mBroadcastReceiver, filter);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(Collision_Settings.NEW_THRESHOLD_05_X);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);
        bm3.registerReceiver(mBroadcastReceiver, filter3);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(Collision_Settings.NEW_THRESHOLD_05_Y);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(context);
        bm4.registerReceiver(mBroadcastReceiver, filter4);

        IntentFilter filter5 = new IntentFilter();
        filter5.addAction(Collision_Settings.NEW_THRESHOLD_05_Z);
        LocalBroadcastManager bm5 = LocalBroadcastManager.getInstance(context);
        bm5.registerReceiver(mBroadcastReceiver, filter5);


    }




}
