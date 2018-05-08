package com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools;

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


public class ProximityFragment extends Fragment {

    String PROXIMITY_READ;
    TextView textPROXIMITY_reading;


    public ProximityFragment() {
        // Required empty public constructor
    }


    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_02)) {
                final String param = intent.getStringExtra("value_02");
                textPROXIMITY_reading.setText("Current value:"+param+"(cm)");
                PROXIMITY_READ=param;
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
        View view =  lf.inflate(R.layout.fragment_proximity, container, false);
        textPROXIMITY_reading = (TextView) view.findViewById(R.id.PROXIMITY_reading);




        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());
        String read02 =prefs2.getData("value_02");
        textPROXIMITY_reading.setText("Current value:"+read02+"(cm)");

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(Collision_Settings.NEW_VALUE_02);
        LocalBroadcastManager bm1 = LocalBroadcastManager.getInstance(getActivity());
        bm1.registerReceiver(mBroadcastReceiver, filter1);




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
