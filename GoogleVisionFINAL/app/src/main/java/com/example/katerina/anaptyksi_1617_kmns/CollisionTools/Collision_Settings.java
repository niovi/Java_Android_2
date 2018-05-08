package com.example.katerina.anaptyksi_1617_kmns.CollisionTools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivityHandler;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;
import com.example.katerina.anaptyksi_1617_kmns.R;


public class Collision_Settings {


    // light sensor
    public static final String NEW_VALUE_01 = "value_01";
    public static final String NEW_AVAILABILITY_01 = "availability_01";
    public static final String NEW_THRESHOLD_01 = "threshold_01";
    public static final String NEW_TIME_INTERVAL_01 = "time_interval_01";
    public static final String NEW_DANGER_01 = "danger_01";


    // proximity sensor
    public static final String NEW_VALUE_02 = "value_02";
    public static final String NEW_AVAILABILITY_02 = "availability_02";
    public static final String NEW_THRESHOLD_02 = "threshold_02";
    public static final String NEW_TIME_INTERVAL_02 = "time_interval_02";
    public static final String NEW_DANGER_02 = "danger_02";

    // gyroscope sensor
    public static final String NEW_AVAILABILITY_03 = "availability_03";
    public static final String NEW_TIME_INTERVAL_03 = "time_interval_03";
    public static final String NEW_VALUE_03_X = "value_03_X";
    public static final String NEW_THRESHOLD_03_X = "threshold_03_X";
    public static final String NEW_VALUE_03_Y = "value_03_Y";
    public static final String NEW_THRESHOLD_03_Y = "threshold_03_Y";
    public static final String NEW_VALUE_03_Z = "value_03_Z";
    public static final String NEW_THRESHOLD_03_Z = "threshold_03_Z";
    public static final String NEW_DANGER_03_X = "danger_03_X";
    public static final String NEW_DANGER_03_Y = "danger_03_Y";
    public static final String NEW_DANGER_03_Z = "danger_03_Z";


    // linear accelerator sensor
    public static final String NEW_AVAILABILITY_04 = "availability_04";
    public static final String NEW_TIME_INTERVAL_04= "time_interval_04";
    public static final String NEW_VALUE_04_X = "value_04_X";
    public static final String NEW_THRESHOLD_04_X = "threshold_04_X";
    public static final String NEW_VALUE_04_Y = "value_04_Y";
    public static final String NEW_THRESHOLD_04_Y = "threshold_04_Y";
    public static final String NEW_VALUE_04_Z = "value_04_Z";
    public static final String NEW_THRESHOLD_04_Z = "threshold_04_Z";
    public static final String NEW_DANGER_04_X = "danger_04_X";
    public static final String NEW_DANGER_04_Y = "danger_04_Y";
    public static final String NEW_DANGER_04_Z = "danger_04_Z";


    // accelerometer sensor
    public static final String NEW_AVAILABILITY_05 = "availability_05";
    public static final String NEW_TIME_INTERVAL_05= "time_interval_05";
    public static final String NEW_VALUE_05_X = "value_05_X";
    public static final String NEW_THRESHOLD_05_X = "threshold_05_X";
    public static final String NEW_VALUE_05_Y = "value_05_Y";
    public static final String NEW_THRESHOLD_05_Y = "threshold_05_Y";
    public static final String NEW_VALUE_05_Z = "value_05_Z";
    public static final String NEW_THRESHOLD_05_Z = "threshold_05_Z";
    public static final String NEW_DANGER_05_X = "danger_05_X";
    public static final String NEW_DANGER_05_Y = "danger_05_Y";
    public static final String NEW_DANGER_05_Z = "danger_05_Z";


    // Location Manager
    public static final String NEW_AVAILABILITY_06 = "availability_06";
    public static final String NEW_TIME_INTERVAL_06= "time_interval_06";
    public static final String NEW_VALUE_06_X = "value_06_X";
    public static final String NEW_THRESHOLD_06_X_A = "threshold_06_X_A";
    public static final String NEW_THRESHOLD_06_X_B = "threshold_06_X_B";
    public static final String NEW_VALUE_06_Y = "value_06_Y";
    public static final String NEW_THRESHOLD_06_Y_A = "threshold_06_Y_A";
    public static final String NEW_THRESHOLD_06_Y_B = "threshold_06_Y_B";
    public static final String NEW_DANGER_06_X = "danger_06_X";
    public static final String NEW_DANGER_06_Y = "danger_06_Y";


    // OFFLINE GLOBAL DANGER
    public static final String GLOBAL_DANGER = "danger_global";







    // VARIABLES
    private Context set_context;
    private static Collision_Settings Set_preferences;
    private SharedPreferences Stored_settings;
    private ConnectivitySettings connectivitySettings;
    private static final String TAG = "Shared Preferences";




    public static Collision_Settings getInstance(Context context) {

        if (Set_preferences == null) {
            Set_preferences = new Collision_Settings(context);
            Log.i(TAG,"New collision settings created");
        }
        return Set_preferences;
    }

    private Collision_Settings(Context context) {
        Stored_settings = context.getSharedPreferences(context.getString(R.string.App_Settings), Context.MODE_PRIVATE);
        set_context=context;
        connectivitySettings=ConnectivitySettings.getInstance(context);
        // default time intervals
        if (getData(NEW_TIME_INTERVAL_01).equals("0"))
            saveData(NEW_TIME_INTERVAL_01,"2500");
        if (getData(NEW_TIME_INTERVAL_02).equals("0"))
            saveData(NEW_TIME_INTERVAL_02,"2500");
        if (getData(NEW_TIME_INTERVAL_03).equals("0"))
            saveData(NEW_TIME_INTERVAL_03,"2500");
        if (getData(NEW_TIME_INTERVAL_04).equals("0"))
            saveData(NEW_TIME_INTERVAL_04,"2500");
        if (getData(NEW_TIME_INTERVAL_05).equals("0"))
            saveData(NEW_TIME_INTERVAL_05,"2500");
        if (getData(NEW_TIME_INTERVAL_06).equals("0"))
            saveData(NEW_TIME_INTERVAL_06,"2500");

    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = Stored_settings.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
        final LocalBroadcastManager bm = LocalBroadcastManager.getInstance(set_context);
        Intent intent = new Intent(key);
        intent.putExtra(key, getData(key));
        bm.sendBroadcast(intent);
        // if online, publish new values
        ConnectivityHandler chandler = ConnectivityHandler.getInstance(set_context);
        chandler.publish_string(key,value);



        Log.i(TAG,"New commit to settings "+key+" with value "+value);
    }

    public String getData(String key) {
        if (Stored_settings != null) {
            return Stored_settings.getString(key, "0");

        }
        else
        return "0";

    }





}





