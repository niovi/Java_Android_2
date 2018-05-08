package com.example.katerina.anaptyksi_1617_kmns.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;
import com.example.katerina.anaptyksi_1617_kmns.R;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.SetAcceleratorFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.SetGPSFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.SetGyroscopeFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.SetLightFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.SetLinearAcceleratorFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.SetProximityFragment;

import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.Accelerator_Sensor.flag_accel_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.GPS_Sensor.flag_gps_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.Gyroscope_Sensor.flag_gyro_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.Light_Sensor.flag_light_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.Linear_Accelerator_Sensor.flag_linear_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.Proximity_Sensor.flag_proximity_available;

public class SettingsActivity extends AppCompatActivity {

   // private static final String TAG = "Settings Activity";


    String LIGHT_AVAIL;
    TextView textLIGHT_available;

    String PROXIMITY_AVAIL;
    TextView textPROXIMITY_available;

    String GYROSCOPE_AVAIL;
    TextView textGYROSCOPE_available;

    String LINEAR_AVAIL;
    TextView textLINEAR_available;

    String ACCEL_AVAIL;
    TextView textACCEL_available;

    String GPS_AVAIL;
    TextView textGPS_available;






    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_01)) {
                final String param = intent.getStringExtra("availability_01");
                textLIGHT_available.setText(param);
                LIGHT_AVAIL=param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_02)) {
                final String param = intent.getStringExtra("availability_02");
                textPROXIMITY_available.setText(param);
                PROXIMITY_AVAIL=param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_03)) {
                final String param = intent.getStringExtra("availability_03");
                textGYROSCOPE_available.setText(param);
                GYROSCOPE_AVAIL=param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_04)) {
                final String param = intent.getStringExtra("availability_04");
                textLINEAR_available.setText(param);
                LINEAR_AVAIL=param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_05)) {
                final String param = intent.getStringExtra("availability_05");
                textACCEL_available.setText(param);
                ACCEL_AVAIL=param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_06)) {
                final String param = intent.getStringExtra("availability_06");
                textGPS_available.setText(param);
                GPS_AVAIL=param;
                set_frag_view();
            }
        }
    };






    private void set_frag_view(){

        final Collision_Settings prefs2=Collision_Settings.getInstance(getApplicationContext());
        final ConnectivitySettings conset2=ConnectivitySettings.getInstance(getApplicationContext());

        String avail01 = prefs2.getData("availability_01");
        String avail02 = prefs2.getData("availability_02");
        String avail03 = prefs2.getData("availability_03");
        String avail04 = prefs2.getData("availability_04");
        String avail05 = prefs2.getData("availability_05");
        String avail06 = prefs2.getData("availability_06");
        String connect = conset2.getData(CONNECTIVITY_STATUS);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        SetLightFragment lightS = new SetLightFragment();
        SetProximityFragment proximityS = new SetProximityFragment();
        SetGyroscopeFragment gyroscopeS = new SetGyroscopeFragment();
        SetLinearAcceleratorFragment linearS = new SetLinearAcceleratorFragment();
        SetAcceleratorFragment accelS = new SetAcceleratorFragment();
        SetGPSFragment gpsS = new SetGPSFragment();
        OnlineSettingsFragment onset = new OnlineSettingsFragment();


        if(connect.equals("TRUE"))fragmentTransaction.replace(R.id.fragment_container_01,onset);
        else fragmentTransaction.remove(onset);

        if((avail01.equals(flag_light_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_01, lightS, "LIGHT");
        }else
            fragmentTransaction.remove(lightS);

        if((avail02.equals(flag_proximity_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_02, proximityS, "PROXIMITY");
        }else fragmentTransaction.remove(proximityS);

        if((avail03.equals(flag_gyro_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_03, gyroscopeS, "GYROSCOPE");
        }else fragmentTransaction.remove(gyroscopeS);

        if((avail04.equals(flag_linear_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_04, linearS, "LINEAR ACCELERATOR");
        }else fragmentTransaction.remove(linearS);

        if((avail05.equals(flag_accel_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_05, accelS, "ACCELERATOR");
        }else fragmentTransaction.remove(accelS);

        if((avail06.equals(flag_gps_available))&&connect.equals("FALSE"))
        {

            fragmentTransaction.replace(R.id.fragment_container_06, gpsS, "LOCATION");
        }fragmentTransaction.remove(gpsS);

        fragmentTransaction.commit();




    }




    private void set_filters(){

        // Intent filters

        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(Collision_Settings.NEW_AVAILABILITY_01);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(this);
        bm2.registerReceiver(mBroadcastReceiver, filter2);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(Collision_Settings.NEW_AVAILABILITY_02);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(this);
        bm4.registerReceiver(mBroadcastReceiver, filter4);

        IntentFilter filter6 = new IntentFilter();
        filter6.addAction(Collision_Settings.NEW_AVAILABILITY_03);
        LocalBroadcastManager bm6 = LocalBroadcastManager.getInstance(this);
        bm6.registerReceiver(mBroadcastReceiver, filter6);

        IntentFilter filter7 = new IntentFilter();
        filter7.addAction(Collision_Settings.NEW_AVAILABILITY_04);
        LocalBroadcastManager bm7 = LocalBroadcastManager.getInstance(this);
        bm7.registerReceiver(mBroadcastReceiver, filter7);

        IntentFilter filter8 = new IntentFilter();
        filter8.addAction(Collision_Settings.NEW_AVAILABILITY_05);
        LocalBroadcastManager bm8 = LocalBroadcastManager.getInstance(this);
        bm8.registerReceiver(mBroadcastReceiver, filter8);

        IntentFilter filter9 = new IntentFilter();
        filter9.addAction(Collision_Settings.NEW_AVAILABILITY_06);
        LocalBroadcastManager bm9 = LocalBroadcastManager.getInstance(this);
        bm9.registerReceiver(mBroadcastReceiver, filter9);



    }












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ImageButton backButton = (ImageButton) findViewById(R.id.BackArrow);

        View.OnClickListener click_listener_2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SettingsActivity.super.onBackPressed();
            }



        };

        backButton.setOnClickListener(click_listener_2);


        set_filters();
        set_frag_view();







    }








}
