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
import static com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings.GLOBAL_DANGER;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;


class OfflineNotify {
    private Boolean danger, off_mode;
    public Toast toast_off_notify;
    private static final String TAG = "OfflineNotify";
    private ConnectivitySettings set_con;
    private Collision_Settings set_notify;

    private void post_alert(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (danger&&off_mode) {
                        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 150);
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        toast_off_notify.show();
                        Log.i(TAG, "beep beep beep beep");
                        sleep(150);
                        tg.release();
                        toast_off_notify.cancel();

                    }

                } catch (Exception e) {
                    Log.i(TAG,"Exception: ",e);
                }

            }
        }).start();


    }


  public OfflineNotify(final Context context) {
        Log.i(TAG,"OfflineNotify created");
      set_notify = Collision_Settings.getInstance(context);
       set_con = ConnectivitySettings.getInstance(context);
        danger=set_notify.getData(GLOBAL_DANGER).equals("TRUE");
      off_mode=set_con.getData(CONNECTIVITY_STATUS).equals("FALSE");

      Handler handler = new Handler(Looper.getMainLooper());
        Runnable show_run = new Runnable(){
            public  void run(){
                toast_off_notify = Toast.makeText(context, "Local Collision Alert!!!", Toast.LENGTH_SHORT);
                Log.i(TAG,"Inside runnable for toast.makeText");
            }
        };
        handler.post(show_run);
        post_alert();







        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(Collision_Settings.GLOBAL_DANGER);
        LocalBroadcastManager bm2 = LocalBroadcastManager.getInstance(context);

      IntentFilter filter3 = new IntentFilter();
      filter3.addAction(ConnectivitySettings.CONNECTIVITY_STATUS);
      LocalBroadcastManager bm3 = LocalBroadcastManager.getInstance(context);


        BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Collision_Settings.GLOBAL_DANGER)) {
                    danger = set_notify.getData(GLOBAL_DANGER).equals("TRUE");
                    off_mode=set_con.getData(CONNECTIVITY_STATUS).equals("FALSE");
                    Log.i(TAG,"Notified for new danger "+danger);
                    Log.i(TAG,"Notified for new Off_mode_ok_to_beep "+off_mode);

                    post_alert();
                }

                if (intent.getAction().equals(CONNECTIVITY_STATUS)) {
                    off_mode=set_con.getData(CONNECTIVITY_STATUS).equals("FALSE");
                    danger = set_notify.getData(GLOBAL_DANGER).equals("TRUE");


                    Log.i(TAG,"Notified for new Off_mode_ok_to_beep "+off_mode);
                    Log.i(TAG,"Danger is "+danger);

                    post_alert();
                }

            }
        };


      bm2.registerReceiver(mBroadcastReceiver, filter2);
      bm3.registerReceiver(mBroadcastReceiver, filter3);


    }




}

