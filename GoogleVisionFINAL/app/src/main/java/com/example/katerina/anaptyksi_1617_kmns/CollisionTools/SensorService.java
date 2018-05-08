package com.example.katerina.anaptyksi_1617_kmns.CollisionTools;

import android.app.Service;
import android.content.Intent;

import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivityHandler;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.Accelerator_Sensor;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.GPS_Sensor;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.Gyroscope_Sensor;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.Light_Sensor;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.Linear_Accelerator_Sensor;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.Proximity_Sensor;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class SensorService extends Service {


    private static final String TAG = "SensorService";




    public SensorService() {
        super();
    }




    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {


        Log.i(TAG, "Service onStartCommand");




        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {

                new Light_Sensor(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                new Proximity_Sensor(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                new Gyroscope_Sensor(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                new Linear_Accelerator_Sensor(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                new Accelerator_Sensor(getApplicationContext());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                new GPS_Sensor(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                ConnectivityHandler.getInstance(getApplicationContext());

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                new CollisionNotifier(getApplicationContext());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                new OfflineNotify(getApplicationContext());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                new OnlineNotify(getApplicationContext());

            }
        }).start();





        return Service.START_STICKY;
    }








    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }

}
