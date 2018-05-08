package com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools;

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


public class GyroscopeFragment extends Fragment {

    String GYROSCOPE_READ_X;
    String GYROSCOPE_READ_Y;
    String GYROSCOPE_READ_Z;
    TextView textGYROSCOPE_reading_X;
    TextView textGYROSCOPE_reading_Y;
    TextView textGYROSCOPE_reading_Z;


    public GyroscopeFragment() {
        // Required empty public constructor
    }


    // handler for received data from service
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_03_X)) {
                final String param = intent.getStringExtra("value_03_X");
                textGYROSCOPE_reading_X.setText("X axis:"+param+"(rad/s)");
                GYROSCOPE_READ_X=param;
            }
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_03_Y)) {
                final String param = intent.getStringExtra("value_03_Y");
                textGYROSCOPE_reading_Y.setText("Y axis:"+param+"(rad/s)");
                GYROSCOPE_READ_Y=param;
            }
            if (intent.getAction().equals(Collision_Settings.NEW_VALUE_03_Z)) {
                final String param = intent.getStringExtra("value_03_Z");
                textGYROSCOPE_reading_Z.setText("Z axis:"+param+"(rad/s)");
                GYROSCOPE_READ_Z=param;
            }

        }
    };






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_gyroscope, container, false);
        textGYROSCOPE_reading_X = (TextView) view.findViewById(R.id.GYROSCOPE_reading_X);
        textGYROSCOPE_reading_Y = (TextView) view.findViewById(R.id.GYROSCOPE_reading_Y);
        textGYROSCOPE_reading_Z = (TextView) view.findViewById(R.id.GYROSCOPE_reading_Z);









        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());
        String read03x =prefs2.getData("value_03_X");
        textGYROSCOPE_reading_X.setText("X axis:"+read03x+"(rad/s)");
        String read03y =prefs2.getData("value_03_Y");
        textGYROSCOPE_reading_Y.setText("Y axis:"+read03y+"(rad/s)");
        String read03z =prefs2.getData("value_03_Z");
        textGYROSCOPE_reading_Z.setText("Z axis:"+read03z+"(rad/s)");

        IntentFilter filter5x = new IntentFilter();
        filter5x.addAction(Collision_Settings.NEW_VALUE_03_X);
        LocalBroadcastManager bm5x = LocalBroadcastManager.getInstance(getActivity());
        bm5x.registerReceiver(mBroadcastReceiver, filter5x);

        IntentFilter filter5y = new IntentFilter();
        filter5y.addAction(Collision_Settings.NEW_VALUE_03_Y);
        LocalBroadcastManager bm5y = LocalBroadcastManager.getInstance(getActivity());
        bm5y.registerReceiver(mBroadcastReceiver, filter5y);

        IntentFilter filter5z = new IntentFilter();
        filter5z.addAction(Collision_Settings.NEW_VALUE_03_Z);
        LocalBroadcastManager bm5z = LocalBroadcastManager.getInstance(getActivity());
        bm5z.registerReceiver(mBroadcastReceiver, filter5z);







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
