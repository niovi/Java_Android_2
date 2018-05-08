package com.example.katerina.anaptyksi_1617_kmns.CollisionTools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings.GLOBAL_DANGER;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.Accelerator_Sensor.flag_accel_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.GPS_Sensor.flag_gps_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.Gyroscope_Sensor.flag_gyro_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.Light_Sensor.flag_light_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.Linear_Accelerator_Sensor.flag_linear_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.Proximity_Sensor.flag_proximity_available;


public class CollisionNotifier {

    private static final String TAG = "Collision Notifier";

    private String LIGHT_AVAIL;
    private String LIGHT_DANGER;

    private String PROXIMITY_AVAIL;
    private String PROXIMITY_DANGER;

    private String GYROSCOPE_AVAIL;
    private String GYRO_DANGER_X;
    private String GYRO_DANGER_Y;
    private String GYRO_DANGER_Z;

    private String LINEAR_AVAIL;
    private String LINEAR_DANGER_X;
    private String LINEAR_DANGER_Y;
    private String LINEAR_DANGER_Z;

    private String ACCEL_AVAIL;
    private String ACCEL_DANGER_X;
    private String ACCEL_DANGER_Y;
    private String ACCEL_DANGER_Z;

    private String GPS_AVAIL;
    private String GPS_DANGER_X;
    private String GPS_DANGER_Y;

    private Boolean DANGER_old=null;


    private void check_danger(Context context) {

        Log.i(TAG, "Checking for danger");

        Boolean light;
        if (flag_light_available.equals(LIGHT_AVAIL)) {
            if ("TRUE".equals(LIGHT_DANGER)) {
                light = true;
                Log.i(TAG, "Light in danger");
            } else light = false;
        } else light = false;

        Boolean proximity;
        if (flag_proximity_available.equals(PROXIMITY_AVAIL)) {
            if (PROXIMITY_DANGER.equals("TRUE")) {
                Log.i(TAG, "Proximity in danger");
                proximity = true;
            } else proximity = false;
        } else proximity = false;

        Boolean gyro = flag_gyro_available.equals(GYROSCOPE_AVAIL) && (GYRO_DANGER_X.equals("TRUE") || GYRO_DANGER_Y.equals("TRUE") || GYRO_DANGER_Z.equals("TRUE"));

        Boolean linear = flag_linear_available.equals(LINEAR_AVAIL) && (LINEAR_DANGER_X.equals("TRUE") || LINEAR_DANGER_Y.equals("TRUE") || LINEAR_DANGER_Z.equals("TRUE"));

        Boolean accel = flag_accel_available.equals(ACCEL_AVAIL) && (ACCEL_DANGER_X.equals("TRUE") || ACCEL_DANGER_Y.equals("TRUE") || ACCEL_DANGER_Z.equals("TRUE"));

        Boolean gps = flag_gps_available.equals(GPS_AVAIL) && (GPS_DANGER_X.equals("TRUE") || GPS_DANGER_Y.equals("TRUE"));


        Boolean DANGER_new = (light || proximity || gyro || linear || accel);
        //Log.i(TAG, "Current value of danger: " + DANGER_new + " previous: " + DANGER_old);

            if (!DANGER_new.equals(DANGER_old)) {
                final Collision_Settings prefs2 = Collision_Settings.getInstance(context);
                if(DANGER_new){
                    prefs2.saveData(GLOBAL_DANGER, "TRUE");

                }else{
                    prefs2.saveData(GLOBAL_DANGER,"FALSE");

                }
                DANGER_old = DANGER_new;
                Log.i(TAG, "DANGER " + DANGER_new);

            }

    }


    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {



            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_01)) {
                LIGHT_AVAIL = intent.getStringExtra("availability_01");
                Log.i(TAG,"Got new availability 01");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_02)) {
                PROXIMITY_AVAIL = intent.getStringExtra("availability_02");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_03)) {
                GYROSCOPE_AVAIL = intent.getStringExtra("availability_03");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_04)) {
                LINEAR_AVAIL = intent.getStringExtra("availability_04");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_05)) {
                ACCEL_AVAIL = intent.getStringExtra("availability_05");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_06)) {
                GPS_AVAIL = intent.getStringExtra("availability_06");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_01)) {
                LIGHT_DANGER = intent.getStringExtra("danger_01");
                Log.i(TAG,"got new danger 01");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_02)) {
                PROXIMITY_DANGER = intent.getStringExtra("danger_02");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_03_X)) {
                GYRO_DANGER_X = intent.getStringExtra("danger_03_X");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_03_Y)) {
                GYRO_DANGER_Y = intent.getStringExtra("danger_03_Y");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_03_Z)) {
                GYRO_DANGER_Z = intent.getStringExtra("danger_03_Z");
                check_danger(context);
            }


            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_04_X)) {
                LINEAR_DANGER_X = intent.getStringExtra("danger_04_X");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_04_Y)) {
                LINEAR_DANGER_Y = intent.getStringExtra("danger_04_Y");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_04_Z)) {
                LINEAR_DANGER_Z = intent.getStringExtra("danger_04_Z");
                check_danger(context);
            }


            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_05_X)) {
                ACCEL_DANGER_X = intent.getStringExtra("danger_05_X");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_05_Y)) {
                ACCEL_DANGER_Y = intent.getStringExtra("danger_05_Y");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_05_Z)) {
                ACCEL_DANGER_Z = intent.getStringExtra("danger_05_Z");
                check_danger(context);
            }

            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_06_X)) {
                GPS_DANGER_X = intent.getStringExtra("danger_06_X");
                check_danger(context);
            }
            if (intent.getAction().equals(Collision_Settings.NEW_DANGER_06_Y)) {
                GPS_DANGER_Y = intent.getStringExtra("danger_06_Y");
                check_danger(context);
            }


        }


    };





  CollisionNotifier(Context context) {

       //Log.i(TAG,"Collision Notifier created" );

       final Collision_Settings prefs2=Collision_Settings.getInstance(context);


       LIGHT_AVAIL=prefs2.getData("availability_01");
       LIGHT_DANGER=prefs2.getData("danger_01");

       PROXIMITY_AVAIL=prefs2.getData("availability_02");
       PROXIMITY_DANGER=prefs2.getData("danger_02");

       //Log.i(TAG,"After Light and Proximity");

       GYROSCOPE_AVAIL=prefs2.getData("availability_03");
       GYRO_DANGER_X=prefs2.getData("danger_03_X");
       GYRO_DANGER_Y=prefs2.getData("danger_03_Y");
       GYRO_DANGER_Z=prefs2.getData("danger_03_Z");

       LINEAR_AVAIL=prefs2.getData("availability_04");
       LINEAR_DANGER_X=prefs2.getData("danger_04_X");
       LINEAR_DANGER_Y=prefs2.getData("danger_04_Y");
       LINEAR_DANGER_Z=prefs2.getData("danger_04_Z");

       ACCEL_AVAIL=prefs2.getData("availability_05");
       ACCEL_DANGER_X=prefs2.getData("danger_05_X");
       ACCEL_DANGER_Y=prefs2.getData("danger_05_Y");
       ACCEL_DANGER_Z=prefs2.getData("danger_05_Z");

       GPS_AVAIL=prefs2.getData("availability_06");
       GPS_DANGER_X=prefs2.getData("danger_06_X");
       GPS_DANGER_Y=prefs2.getData("danger_06_Y");
       //Log.i(TAG,"Just before check");
       check_danger(context);
       //Log.i(TAG,"Right after check");


       // Intent filters

       IntentFilter filter2 = new IntentFilter();
       filter2.addAction(Collision_Settings.NEW_AVAILABILITY_01);
       LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(context);
       bm2.registerReceiver(mBroadcastReceiver, filter2);

       IntentFilter filter4 = new IntentFilter();
       filter4.addAction(Collision_Settings.NEW_AVAILABILITY_02);
       LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(context);
       bm4.registerReceiver(mBroadcastReceiver, filter4);

       IntentFilter filter6 = new IntentFilter();
       filter6.addAction(Collision_Settings.NEW_AVAILABILITY_03);
       LocalBroadcastManager bm6 = LocalBroadcastManager.getInstance(context);
       bm6.registerReceiver(mBroadcastReceiver, filter6);

       IntentFilter filter7 = new IntentFilter();
       filter7.addAction(Collision_Settings.NEW_AVAILABILITY_04);
       LocalBroadcastManager bm7 = LocalBroadcastManager.getInstance(context);
       bm7.registerReceiver(mBroadcastReceiver, filter7);

       IntentFilter filter8 = new IntentFilter();
       filter8.addAction(Collision_Settings.NEW_AVAILABILITY_05);
       LocalBroadcastManager bm8 = LocalBroadcastManager.getInstance(context);
       bm8.registerReceiver(mBroadcastReceiver, filter8);

       IntentFilter filter9 = new IntentFilter();
       filter9.addAction(Collision_Settings.NEW_AVAILABILITY_06);
       LocalBroadcastManager bm9 = LocalBroadcastManager.getInstance(context);
       bm9.registerReceiver(mBroadcastReceiver, filter9);

       IntentFilter filter11 = new IntentFilter();
       filter11.addAction(Collision_Settings.NEW_DANGER_01);
       LocalBroadcastManager bm11 = LocalBroadcastManager.getInstance(context);
       bm11.registerReceiver(mBroadcastReceiver, filter11);

       IntentFilter filter22 = new IntentFilter();
       filter22.addAction(Collision_Settings.NEW_DANGER_02);
       LocalBroadcastManager bm22 = LocalBroadcastManager.getInstance(context);
       bm22.registerReceiver(mBroadcastReceiver, filter22);

       IntentFilter filter3x = new IntentFilter();
       filter3x.addAction(Collision_Settings.NEW_DANGER_03_X);
       LocalBroadcastManager bm3x = LocalBroadcastManager.getInstance(context);
       bm3x.registerReceiver(mBroadcastReceiver, filter3x);

       IntentFilter filter3y = new IntentFilter();
       filter3y.addAction(Collision_Settings.NEW_DANGER_03_Y);
       LocalBroadcastManager bm3y = LocalBroadcastManager.getInstance(context);
       bm3y.registerReceiver(mBroadcastReceiver, filter3y);

       IntentFilter filter3z = new IntentFilter();
       filter3z.addAction(Collision_Settings.NEW_DANGER_03_Z);
       LocalBroadcastManager bm3z = LocalBroadcastManager.getInstance(context);
       bm3z.registerReceiver(mBroadcastReceiver, filter3z);

       IntentFilter filter4x = new IntentFilter();
       filter4x.addAction(Collision_Settings.NEW_DANGER_04_X);
       LocalBroadcastManager bm4x = LocalBroadcastManager.getInstance(context);
       bm4x.registerReceiver(mBroadcastReceiver, filter4x);

       IntentFilter filter4y = new IntentFilter();
       filter4y.addAction(Collision_Settings.NEW_DANGER_04_Y);
       LocalBroadcastManager bm4y = LocalBroadcastManager.getInstance(context);
       bm4y.registerReceiver(mBroadcastReceiver, filter4y);

       IntentFilter filter4z = new IntentFilter();
       filter4z.addAction(Collision_Settings.NEW_DANGER_04_Z);
       LocalBroadcastManager bm4z = LocalBroadcastManager.getInstance(context);
       bm4z.registerReceiver(mBroadcastReceiver, filter4z);


       IntentFilter filter7x = new IntentFilter();
       filter7x.addAction(Collision_Settings.NEW_DANGER_05_X);
       LocalBroadcastManager bm7x = LocalBroadcastManager.getInstance(context);
       bm7x.registerReceiver(mBroadcastReceiver, filter7x);

       IntentFilter filter7y = new IntentFilter();
       filter7y.addAction(Collision_Settings.NEW_DANGER_05_Y);
       LocalBroadcastManager bm7y = LocalBroadcastManager.getInstance(context);
       bm7y.registerReceiver(mBroadcastReceiver, filter7y);

       IntentFilter filter7z = new IntentFilter();
       filter7z.addAction(Collision_Settings.NEW_DANGER_05_Z);
       LocalBroadcastManager bm7z = LocalBroadcastManager.getInstance(context);
       bm7z.registerReceiver(mBroadcastReceiver, filter7z);


       IntentFilter filter6x = new IntentFilter();
       filter6x.addAction(Collision_Settings.NEW_DANGER_06_X);
       LocalBroadcastManager bm6x = LocalBroadcastManager.getInstance(context);
       bm6x.registerReceiver(mBroadcastReceiver, filter6x);

       IntentFilter filter6y = new IntentFilter();
       filter6y.addAction(Collision_Settings.NEW_DANGER_06_Y);
       LocalBroadcastManager bm6y = LocalBroadcastManager.getInstance(context);
       bm6y.registerReceiver(mBroadcastReceiver, filter6y);





  }











}
