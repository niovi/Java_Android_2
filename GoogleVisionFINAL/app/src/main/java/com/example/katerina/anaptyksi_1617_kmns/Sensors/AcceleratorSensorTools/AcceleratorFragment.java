package com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools;

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


public class AcceleratorFragment extends Fragment {

    String ACCEL_READ_X;
    TextView textACCEL_reading_X;
    String ACCEL_READ_Y;
    TextView textACCEL_reading_Y;
    String ACCEL_READ_Z;
    TextView textACCEL_reading_Z;


    public AcceleratorFragment() {
        // Required empty public constructor
    }


    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_05_X)) {
                final String param = intent.getStringExtra("value_05_X");
                textACCEL_reading_X.setText("X axis:"+param+"(m/s^2)");
                ACCEL_READ_X=param;
            }
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_05_Y)) {
                final String param = intent.getStringExtra("value_05_Y");
                textACCEL_reading_Y.setText("Y axis:"+param+"(m/s^2)");
                ACCEL_READ_Y=param;
            }
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_05_Z)) {
                final String param = intent.getStringExtra("value_05_Z");
                textACCEL_reading_Z.setText("Z axis:"+param+"(m/s^2)");
                ACCEL_READ_Z=param;
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
        View view =  lf.inflate(R.layout.fragment_accelerator, container, false);
        textACCEL_reading_X = (TextView) view.findViewById(R.id.ACCEL_reading_X);
        textACCEL_reading_Y = (TextView) view.findViewById(R.id.ACCEL_reading_Y);
        textACCEL_reading_Z = (TextView) view.findViewById(R.id.ACCEL_reading_Z);





        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());
        String read05x =prefs2.getData("value_05_X");
        textACCEL_reading_X.setText("X axis:"+read05x+"(m/s^2)");
        String read05y =prefs2.getData("value_05_Y");
        textACCEL_reading_Y.setText("Y axis:"+read05y+"(m/s^2)");
        String read05z =prefs2.getData("value_05_Z");
        textACCEL_reading_Z.setText("Z axis:"+read05z+"(m/s^2)");

        IntentFilter filter7x = new IntentFilter();
        filter7x.addAction(Collision_Settings.NEW_VALUE_05_X);
        LocalBroadcastManager bm7x = LocalBroadcastManager.getInstance(getActivity());
        bm7x.registerReceiver(mBroadcastReceiver, filter7x);

        IntentFilter filter7y = new IntentFilter();
        filter7y.addAction(Collision_Settings.NEW_VALUE_05_Y);
        LocalBroadcastManager bm7y = LocalBroadcastManager.getInstance(getActivity());
        bm7y.registerReceiver(mBroadcastReceiver, filter7y);

        IntentFilter filter7z = new IntentFilter();
        filter7z.addAction(Collision_Settings.NEW_VALUE_05_Z);
        LocalBroadcastManager bm7z = LocalBroadcastManager.getInstance(getActivity());
        bm7z.registerReceiver(mBroadcastReceiver, filter7z);








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
