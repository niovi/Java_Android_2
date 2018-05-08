package com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.R;


public class GPSFragment extends Fragment {

    String GPS_READ_X;
    TextView textGPS_reading_X;
    String GPS_READ_Y;
    TextView textGPS_reading_Y;





    public GPSFragment() {
        // Required empty public constructor
    }



    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_06_X)) {
                final String param = intent.getStringExtra("value_05_X");
                textGPS_reading_X.setText("Longitude:"+param+"");
                GPS_READ_X=param;
            }
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_06_Y)) {
                final String param = intent.getStringExtra("value_05_Y");
                textGPS_reading_Y.setText("Latitude:"+param+"");
                GPS_READ_Y=param;
            }



        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_gps, container, false);
        textGPS_reading_X = (TextView) view.findViewById(R.id.GPS_reading_X);
        textGPS_reading_Y = (TextView) view.findViewById(R.id.GPS_reading_Y);





                final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());
                String read06x =prefs2.getData("value_06_X");
                textGPS_reading_X.setText("Longitude:"+read06x+"");
                String read06y =prefs2.getData("value_06_Y");
                textGPS_reading_Y.setText("Latitude:"+read06y+"");

                IntentFilter filter7x = new IntentFilter();
                filter7x.addAction(Collision_Settings.NEW_VALUE_06_X);
                LocalBroadcastManager bm7x = LocalBroadcastManager.getInstance(getActivity());
                bm7x.registerReceiver(mBroadcastReceiver, filter7x);

                IntentFilter filter7y = new IntentFilter();
                filter7y.addAction(Collision_Settings.NEW_VALUE_06_Y);
                LocalBroadcastManager bm7y = LocalBroadcastManager.getInstance(getActivity());
                bm7y.registerReceiver(mBroadcastReceiver, filter7y);




        return view;



    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(getActivity());
        bm.unregisterReceiver(mBroadcastReceiver);
        super.onDetach();
    }


}
