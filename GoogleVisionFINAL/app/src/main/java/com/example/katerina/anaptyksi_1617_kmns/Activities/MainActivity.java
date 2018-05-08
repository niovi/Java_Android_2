package com.example.katerina.anaptyksi_1617_kmns.Activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivityHandler;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;
import com.example.katerina.anaptyksi_1617_kmns.GoogleVision.VisionService;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.AcceleratorFragment;
import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.GPSFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.GyroscopeFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.LightFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.LinearAcceleratorFragment;
import com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.ProximityFragment;
import com.example.katerina.anaptyksi_1617_kmns.R;
import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.SensorService;


import static android.os.SystemClock.sleep;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_AVAILABLE;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.VISION_AVAILABLE;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.VISION_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools.Accelerator_Sensor.flag_accel_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools.GPS_Sensor.flag_gps_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools.Gyroscope_Sensor.flag_gyro_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools.Light_Sensor.flag_light_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools.Linear_Accelerator_Sensor.flag_linear_available;
import static com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools.Proximity_Sensor.flag_proximity_available;


public class MainActivity extends AppCompatActivity {

    private ImageButton PopButton;
    private Switch SwitchButton, VisionSwitch;
    public Toast toast1,toast2;
    ConnectivitySettings consets;
    Collision_Settings prefs2;
    ConnectivityHandler conn_han;

    private static final String TAG = "Main Activity";


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

            if (intent.getAction().equals(CONNECTIVITY_STATUS)) {
                final String param = intent.getStringExtra(CONNECTIVITY_STATUS);
                if(param.equals("TRUE")){SwitchButton.setChecked(true);}
                else {SwitchButton.setChecked(false);}
            }

            if (intent.getAction().equals(CONNECTIVITY_AVAILABLE)) {
                final String param = intent.getStringExtra(CONNECTIVITY_AVAILABLE);
                if(param.equals("FALSE")){SwitchButton.setChecked(false);}

            }

            if (intent.getAction().equals(VISION_AVAILABLE)) {
                final String param = intent.getStringExtra(VISION_AVAILABLE);
                if(param.equals("FALSE")){VisionSwitch.setChecked(false);}

            }


            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_01)) {
                final String param = intent.getStringExtra("availability_01");
                textLIGHT_available.setText(param);
                LIGHT_AVAIL = param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_02)) {
                final String param = intent.getStringExtra("availability_02");
                textPROXIMITY_available.setText(param);
                PROXIMITY_AVAIL = param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_03)) {
                final String param = intent.getStringExtra("availability_03");
                textGYROSCOPE_available.setText(param);
                GYROSCOPE_AVAIL = param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_04)) {
                final String param = intent.getStringExtra("availability_04");
                textLINEAR_available.setText(param);
                LINEAR_AVAIL = param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_05)) {
                final String param = intent.getStringExtra("availability_05");
                textACCEL_available.setText(param);
                ACCEL_AVAIL = param;
                set_frag_view();
            }

            if (intent.getAction().equals(Collision_Settings.NEW_AVAILABILITY_06)) {
                final String param = intent.getStringExtra("availability_06");
                textGPS_available.setText(param);
                GPS_AVAIL = param;
                set_frag_view();
            }
        }
    };


    private void set_filters(){


        // Intent filters
        IntentFilter filter0 = new IntentFilter();
        filter0.addAction(CONNECTIVITY_STATUS);
        LocalBroadcastManager bm0 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm0.registerReceiver(mBroadcastReceiver, filter0);

        IntentFilter filter0_1 = new IntentFilter();
        filter0_1.addAction(CONNECTIVITY_AVAILABLE);
        LocalBroadcastManager bm0_1 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm0_1.registerReceiver(mBroadcastReceiver, filter0_1);


        IntentFilter filter0_2 = new IntentFilter();
        filter0_2.addAction(VISION_AVAILABLE);
        LocalBroadcastManager bm0_2 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm0_2.registerReceiver(mBroadcastReceiver, filter0_2);


        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(Collision_Settings.NEW_AVAILABILITY_01);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm2.registerReceiver(mBroadcastReceiver, filter2);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(Collision_Settings.NEW_AVAILABILITY_02);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm4.registerReceiver(mBroadcastReceiver, filter4);

        IntentFilter filter6 = new IntentFilter();
        filter6.addAction(Collision_Settings.NEW_AVAILABILITY_03);
        LocalBroadcastManager bm6 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm6.registerReceiver(mBroadcastReceiver, filter6);

        IntentFilter filter7 = new IntentFilter();
        filter7.addAction(Collision_Settings.NEW_AVAILABILITY_04);
        LocalBroadcastManager bm7 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm7.registerReceiver(mBroadcastReceiver, filter7);

        IntentFilter filter8 = new IntentFilter();
        filter8.addAction(Collision_Settings.NEW_AVAILABILITY_05);
        LocalBroadcastManager bm8 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm8.registerReceiver(mBroadcastReceiver, filter8);

        IntentFilter filter9 = new IntentFilter();
        filter9.addAction(Collision_Settings.NEW_AVAILABILITY_06);
        LocalBroadcastManager bm9 = LocalBroadcastManager.getInstance(getApplicationContext());
        bm9.registerReceiver(mBroadcastReceiver, filter9);



    }


    private void set_frag_view(){





        String avail01 = prefs2.getData("availability_01");
        textLIGHT_available.setText(avail01);
        String avail02 = prefs2.getData("availability_02");
        textPROXIMITY_available.setText(avail02);
        String avail03 = prefs2.getData("availability_03");
        textGYROSCOPE_available.setText(avail03);
        String avail04 = prefs2.getData("availability_04");
        textLINEAR_available.setText(avail04);
        String avail05 = prefs2.getData("availability_05");
        textACCEL_available.setText(avail05);
        String avail06 = prefs2.getData("availability_06");
        textGPS_available.setText(avail06);



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();


        LightFragment lightS = new LightFragment();
        ProximityFragment proximityS = new ProximityFragment();
        GyroscopeFragment gyroscopeS = new GyroscopeFragment();
        LinearAcceleratorFragment linearS = new LinearAcceleratorFragment();
        AcceleratorFragment accelS = new AcceleratorFragment();
        GPSFragment gpsS = new GPSFragment();


        if((avail01.equals(flag_light_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_01, lightS, "LIGHT");
        }else fragmentTransaction.remove(lightS);


        if((avail02.equals(flag_proximity_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_02, proximityS, "PROXIMITY");
        }else fragmentTransaction.remove(proximityS);

        if((avail03.equals(flag_gyro_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_03, gyroscopeS, "GYROSCOPE");
        }else fragmentTransaction.remove(gyroscopeS);

        if((avail04.equals(flag_linear_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_04, linearS, "LINEAR ACCELERATOR");
        }else fragmentTransaction.remove(linearS);

        if((avail05.equals(flag_accel_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_05, accelS, "ACCELERATOR");
        }else fragmentTransaction.remove(accelS);


        if((avail06.equals(flag_gps_available)))
        {
            fragmentTransaction.replace(R.id.fragment_container_06, gpsS, "LOCATION");
        }else fragmentTransaction.remove(gpsS);


        fragmentTransaction.commitAllowingStateLoss();




    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        startService(new Intent(this, SensorService.class));
        startService(new Intent(this, VisionService.class));

        setContentView(R.layout.activity_main);
        ImageButton backButton = (ImageButton) findViewById(R.id.BackArrow);
        PopButton = (ImageButton) findViewById(R.id.PopButton);
        SwitchButton = (Switch) findViewById(R.id.status_switch);
        VisionSwitch = (Switch) findViewById(R.id.vision_switch);

        textLIGHT_available = (TextView) findViewById(R.id.LIGHT_available);
        textPROXIMITY_available = (TextView) findViewById(R.id.PROXIMITY_available);
        textGYROSCOPE_available = (TextView) findViewById(R.id.GYROSCOPE_available);
        textLINEAR_available = (TextView) findViewById(R.id.LINEAR_available);
        textACCEL_available = (TextView) findViewById(R.id.ACCEL_available);
        textGPS_available = (TextView) findViewById(R.id.GPS_available);

        consets=ConnectivitySettings.getInstance(getApplicationContext());
        prefs2=Collision_Settings.getInstance(getApplicationContext());
        conn_han= ConnectivityHandler.getInstance(getApplicationContext());



        String previous_switch = consets.getData(CONNECTIVITY_STATUS);
        if(previous_switch.equals("0"))
            SwitchButton.setChecked(false);
        else {
            boolean conn_on = previous_switch.equals("TRUE");
            SwitchButton.setChecked(conn_on);
        }


        String previous_vision = consets.getData(VISION_STATUS);
        if(previous_vision.equals("0"))
            VisionSwitch.setChecked(false);
        else {
            boolean conn_on = previous_vision.equals("TRUE");
            VisionSwitch.setChecked(conn_on);
        }

        set_frag_view();
        set_filters();

        ////////   Button Listeners

        View.OnClickListener click_listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, PopButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_settings:
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                                return true;
                            case R.id.action_exit:

                                consets.saveData("connectivity_status","FALSE");
                                consets.saveData("vision_status","FALSE");

                                stopService(new Intent(MainActivity.this, SensorService.class));
                                stopService(new Intent(MainActivity.this, VisionService.class));

                                finish();
                                System.exit(0);

                                return true;


                            default:
                                return false;
                        }
                    }
                });

                popup.show(); //showing popup menu
            }



        };

        PopButton.setOnClickListener(click_listener);

        View.OnClickListener click_listener_2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }



        };

        backButton.setOnClickListener(click_listener_2);







        SwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // check connectivity
                conn_han.isNetworkConnected(getApplicationContext());

                if(isChecked){
                    if(consets.getData(CONNECTIVITY_AVAILABLE).equals("TRUE")){
                        consets.saveData(CONNECTIVITY_STATUS,"TRUE");
                        toast1 = Toast.makeText((MainActivity.this),"Connectivity Status On",Toast.LENGTH_SHORT);
                        conn_han.switch_on();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                toast1.show();
                                sleep(1000);
                                toast1.cancel();
                            }
                        }).start();


                    }else {
                        consets.saveData(CONNECTIVITY_STATUS,"FALSE");
                        toast1 = Toast.makeText((MainActivity.this),"Connectivity not available",Toast.LENGTH_SHORT);
                        conn_han.switch_off();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                toast1.show();
                                sleep(1000);
                                toast1.cancel();
                            }
                        }).start();
                        Log.i(TAG,"connectivity not available");
                    }

                }else{
                    consets.saveData("connectivity_status","FALSE");
                    toast2=Toast.makeText((MainActivity.this),"Connectivity Status Off", LENGTH_SHORT);
                    conn_han.switch_off();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                toast2.show();
                                sleep(1000);
                            toast2.cancel();

                        }
                    }).start();


                }


            }
        });




        VisionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked){
                    if(consets.getData(VISION_AVAILABLE).equals("TRUE")){
                        consets.saveData(VISION_STATUS,"TRUE");
                        toast1 = Toast.makeText((MainActivity.this),"Google Vision Status On",Toast.LENGTH_SHORT);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                toast1.show();
                                sleep(1000);
                                toast1.cancel();
                            }
                        }).start();


                    }else {
                        consets.saveData(VISION_STATUS,"TRUE");

                        VisionSwitch.setChecked(false);

                        Log.i(TAG,"vision not available");
                    }

                }else{
                    consets.saveData(VISION_STATUS,"FALSE");

                    toast2=Toast.makeText((MainActivity.this),"Google Vision Status Off", LENGTH_SHORT);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            toast2.show();
                            sleep(1000);
                            toast2.cancel();

                        }
                    }).start();


                }


            }
        });


    }

    @Override
    protected void onDestroy() {
       LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        bm.unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        consets.saveData("connectivity_status","FALSE");
                        consets.saveData("vision_status","FALSE");

                        stopService(new Intent(MainActivity.this, SensorService.class));
                        stopService(new Intent(MainActivity.this, VisionService.class));

                        finish();
                        System.exit(0);
                    }
                }).create().show();

    }








}

