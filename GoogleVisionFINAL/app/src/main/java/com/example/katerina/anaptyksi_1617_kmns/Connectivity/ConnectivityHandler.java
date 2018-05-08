package com.example.katerina.anaptyksi_1617_kmns.Connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttException;


import static android.content.Context.LOCATION_SERVICE;
import static com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings.NEW_VALUE_06_X;
import static com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings.NEW_VALUE_06_Y;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_IP;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_PORT;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_AVAILABLE;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.GPS_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.TERMINAL_UUID;


// to find host: nmap -sn 192.168.1.0/24
//  mosquitto_sub -h 192.168.1.3 -p 1883 -t "#"

public class ConnectivityHandler {

    private static ConnectivityHandler connect_handler;

    private Mqtt_publisher pub;
    private Mqtt_subscriber sub;
    private static ConnectivitySettings prefs;
    private static final String TAG = "Connectivity Handler";


     public static ConnectivityHandler getInstance(Context context) {

        if (connect_handler == null) {
            connect_handler = new ConnectivityHandler(context);
            Log.i(TAG, "New collision settings created");
        }

        return connect_handler;
    }


    private ConnectivityHandler(Context context) {

        Log.i(TAG,"Set filters");
        set_filters(context);
        Log.i(TAG, "initialize");
        prefs = ConnectivitySettings.getInstance(context);
        Log.i(TAG, "Got prefs instance");

        get_uuid(context);
        Log.i(TAG, "Got uuid");
        isNetworkConnected(context);
        Log.i(TAG, "Got network status");

        pub = Mqtt_publisher.getInstance(context);
        Log.i(TAG, "Got publisher instance");
        sub = Mqtt_subscriber.getInstance(context);
        Log.i(TAG, "Got subscriber instance");

        new Thread(new Runnable() {
            @Override
            public void run() {

                switch_on();

            }
        }).start();

    }


    private static void get_uuid(Context context) {

        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String uuid;
        uuid = tManager.getDeviceId();
        prefs.saveData(TERMINAL_UUID, uuid);

    }

    public void publish_string(String key, String value) {
        Log.i(TAG,"Trying to publish string ");
        Log.i(TAG,"CONNECTIVITY_AVAILABLE "+prefs.getData(CONNECTIVITY_AVAILABLE));
        Log.i(TAG,"CONNECTIVITY_STATUS "+prefs.getData(CONNECTIVITY_STATUS));
        Log.i(TAG,"KEY "+key);
        Log.i(TAG,"key.contains(\"NEW_VALUE\") "+key.contains("NEW_VALUE"));
        Log.i(TAG,"key.equals(NEW_VALUE_06_X) "+key.equals(NEW_VALUE_06_X));

        if(prefs.getData(CONNECTIVITY_AVAILABLE).equals("TRUE")&&prefs.getData(CONNECTIVITY_STATUS).equals("TRUE")){
        if (key.contains("value") && !(key.equals(NEW_VALUE_06_X) || key.equals(NEW_VALUE_06_Y))) {
            String topic = prefs.getData(TERMINAL_UUID);
            String message = key + "," + value + "," + prefs.getData(NEW_VALUE_06_X) + "," + prefs.getData(NEW_VALUE_06_Y);
            Log.i(TAG,"Sending string to publisher");
            pub.publish(topic,message);
        }}



    }


    public void isNetworkConnected(Context context) {


        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.i(TAG, "GPS "+isGPS);

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.i(TAG, "Internet "+isConnected);

        if(isConnected &&isGPS)
            prefs.saveData(CONNECTIVITY_AVAILABLE,"TRUE");
        else prefs.saveData(CONNECTIVITY_AVAILABLE,"FALSE");


    }


    private void update_clients(){
        Log.i(TAG, "updating clients");

        pub.update_client();
        sub.update_client();

        new Thread(new Runnable() {
            @Override
            public void run() {

                switch_on();

            }
        }).start();

    }





    public void switch_off(){

        Log.i(TAG, "switch off");

        pub.publish("Availability",prefs.getData(TERMINAL_UUID) + ",OFF");
        pub.disconnect();
        sub.disconnect();
        sub.UnSubscribe(prefs.getData(TERMINAL_UUID));

    }

    public void switch_on(){
        Log.i(TAG, "switch on");

            pub.connect();
            sub.connect();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    sub.subscribe(prefs.getData(TERMINAL_UUID) + ",ON");
                    Log.i(TAG, "subscribe to danger info");
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    pub.publish("Availability", prefs.getData(TERMINAL_UUID) + ",ON");
                    Log.i(TAG, "publish availability");
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sub.subscribe("Availability");
                    Log.i(TAG, "subscribe to availability");
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sub.subscribe("Time_Intervals");
                    Log.i(TAG, "subscribe to time intervals");
                }
            }).start();


    }


    static void print_exception(MqttException me2)
    {
        System.out.println("reason	" + me2.getReasonCode());
        System.out.println("msg " + me2.getMessage());
        System.out.println("loc " + me2.getLocalizedMessage());
        System.out.println("cause	" + me2.getCause());
        System.out.println("exception " + me2);
        me2.printStackTrace();
    }




    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getAction().equals(CONNECTIVITY_STATUS)) {

                String status = prefs.getData(CONNECTIVITY_STATUS);
                if(status.equals("TRUE")){
                    switch_on();
                }
                else{
                    switch_off();
                }

            }

            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

                isNetworkConnected(context);

            }

            if (intent.getAction().equals(GPS_STATUS)) {

                isNetworkConnected(context);

            }



            if (intent.getAction().equals(BROKERS_IP)) {
               // prefs.saveData(READY,"FALSE");

                update_clients();

            }

            if (intent.getAction().equals(BROKERS_PORT)) {

                update_clients();

            }

        }};



    private void set_filters(Context context) {

        // Intent filters
        IntentFilter filter0 = new IntentFilter();
        filter0.addAction(ConnectivitySettings.CONNECTIVITY_STATUS);
        LocalBroadcastManager bm0 = LocalBroadcastManager.getInstance(context);
        bm0.registerReceiver(mBroadcastReceiver, filter0);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        LocalBroadcastManager bm1 = LocalBroadcastManager.getInstance(context);
        bm1.registerReceiver(mBroadcastReceiver, filter1);

        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(GPS_STATUS);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(context);
        bm2.registerReceiver(mBroadcastReceiver, filter2);

        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(BROKERS_IP);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);
        bm3.registerReceiver(mBroadcastReceiver, filter3);

        IntentFilter filter4 = new IntentFilter();
        filter4.addAction(BROKERS_PORT);
        LocalBroadcastManager bm4 = LocalBroadcastManager.getInstance(context);
        bm4.registerReceiver(mBroadcastReceiver, filter4);
    }


}
