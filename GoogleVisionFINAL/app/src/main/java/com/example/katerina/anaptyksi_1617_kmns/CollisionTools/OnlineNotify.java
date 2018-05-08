package com.example.katerina.anaptyksi_1617_kmns.CollisionTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;

import static android.os.SystemClock.sleep;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.REMOTE_DANGER;


class OnlineNotify {

    private Boolean danger,v_danger,on_mode;
    private Toast toast_on_simple,toast_on_verified;
    private static final String TAG = "OnlineNotify";
    private ConnectivitySettings set_notify;

    private void post_alert(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (danger&&on_mode ) {
                        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 150);
                        tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
                        toast_on_simple.show();
                        Log.i(TAG, "beeeeeeep beeeeeeeep ");
                        sleep(150);
                        tg.release();
                        toast_on_simple.cancel();

                    }

                } catch (Exception e) {
                    Log.i(TAG,"Exception: ",e);
                }

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (v_danger&&on_mode ) {
                        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 150);
                        tg.startTone(ToneGenerator.TONE_CDMA_ANSWER);
                        toast_on_verified.show();
                        Log.i(TAG, "beeeeeeep beeeeeeeep ");
                        sleep(150);
                        tg.release();
                        toast_on_verified.cancel();

                    }

                } catch (Exception e) {
                    Log.i(TAG,"Exception: ",e);
                }

            }
        }).start();


    }


    OnlineNotify(final Context context) {
        Log.i(TAG,"OnlineNotify created");
        set_notify= ConnectivitySettings.getInstance(context);
        danger=(set_notify.getData("danger_remote").equals("SIMPLE"));
        v_danger = (set_notify.getData("danger_remote").equals("VERIFIED"));
        on_mode=Boolean.getBoolean(set_notify.getData(CONNECTIVITY_STATUS));

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable show_run = new Runnable(){
            public  void run(){
                toast_on_simple = Toast.makeText(context, "Remote SIMPLE Collision Alert!!!", Toast.LENGTH_SHORT);
                toast_on_verified = Toast.makeText(context, "Remote VERIFIED Collision Alert!!!", Toast.LENGTH_SHORT);

                Log.i(TAG,"Inside runnable for toast.makeText");
            }
        };
        handler.post(show_run);
        post_alert();







        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(REMOTE_DANGER);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(context);
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction(CONNECTIVITY_STATUS);
        LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);


        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(REMOTE_DANGER)) {
                    danger=(set_notify.getData("danger_remote").equals("SIMPLE"));
                    v_danger = (set_notify.getData("danger_remote").equals("VERIFIED"));
                    Log.i(TAG,"Notified for new danger");
                    post_alert();
                }
                if (intent.getAction().equals(CONNECTIVITY_STATUS)) {
                    final String param = intent.getStringExtra(CONNECTIVITY_STATUS);
                    on_mode = param.equals("TRUE");
                    Log.i(TAG,"Notified for new Online");
                    post_alert();
                }

            }
        };
        bm2.registerReceiver(mBroadcastReceiver, filter2);
        bm3.registerReceiver(mBroadcastReceiver, filter3);


    }





}
