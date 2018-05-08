package com.example.katerina.anaptyksi_1617_kmns.Connectivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.katerina.anaptyksi_1617_kmns.R;




public class ConnectivitySettings {

    // CONNECTIVITY
    public static final String REMOTE_DANGER = "danger_remote";
    public static final String GPS_STATUS = "gps_status";
    public static final String CONNECTIVITY_STATUS = "connectivity_status";
    public static final String VISION_STATUS = "vision_status";

    public static final String CONNECTIVITY_AVAILABLE = "connectivity_available";
    public static final String VISION_AVAILABLE = "vision_available";

    public static final String TERMINAL_UUID = "terminal_uuid";
    public static final String BROKERS_IP = "brokers_ip";
    public static final String BROKERS_PORT = "brokers_port";
    public static final String SDK_VERSION = "sdk version";
    public static final String PHOTO_URI = "photo_uri";







    // VARIABLES
    private Context set_context;
    private static ConnectivitySettings ON_preferences;
    private SharedPreferences Stored_settings;

    private static final String TAG = "Online Preferences";




    public static ConnectivitySettings getInstance(Context context) {

        if (ON_preferences == null) {
            ON_preferences = new ConnectivitySettings(context);
            Log.i(TAG,"New collision settings created");
        }
        return ON_preferences;
    }

    private ConnectivitySettings(Context context) {
        Stored_settings = context.getSharedPreferences(context.getString(R.string.App_Settings), Context.MODE_PRIVATE);
        set_context=context;
        if (getData(BROKERS_IP).equals("0"))
            saveData(BROKERS_IP,"192.168.1.1");
        if (getData(BROKERS_PORT).equals("0"))
            saveData(BROKERS_PORT,"1883");


    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = Stored_settings.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
        final LocalBroadcastManager bm = LocalBroadcastManager.getInstance(set_context);
        Intent intent = new Intent(key);
        intent.putExtra(key, getData(key));
        bm.sendBroadcast(intent);

        Log.i(TAG,"New commit to online settings "+key+" with value "+value);
    }

    public String getData(String key) {
        if (Stored_settings != null) {
            return Stored_settings.getString(key, "0");

        }
        else
            return "0";

    }



}
