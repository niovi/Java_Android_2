package com.example.katerina.anaptyksi_1617_kmns.Sensors.ProximitySensorTools;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;
import com.example.katerina.anaptyksi_1617_kmns.R;



public class SetProximityFragment extends Fragment {

    // PROXIMITY SENSOR
    TextView parameter_02;
    SeekBar seek_parameter_02;
    double p2=0;

    TextView parameter_02_time;
    SeekBar seek_parameter_02_time;
    double p2t=0;


    public SetProximityFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_set_proximity, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());


        // PROXIMITY SENSOR
        parameter_02=(TextView) view.findViewById(R.id.parameter02);
        seek_parameter_02=(SeekBar) view.findViewById(R.id.seek_parameter02);

        parameter_02_time=(TextView) view.findViewById(R.id.parameter02_time);
        seek_parameter_02_time=(SeekBar) view.findViewById(R.id.seek_parameter02_time);



        // PROXIMITY SENSOR
        String thres02 = prefs2.getData("threshold_02");
        parameter_02.setText("Threshold for Proximity Sensor:"+thres02+"(cm)");
        int k2=(int)(Double.parseDouble(thres02)*10);
        seek_parameter_02.setProgress(k2);

        String time02 = prefs2.getData("time_interval_02");
        parameter_02_time.setText("Time interval for Proximity Sensor:"+time02+"(ms)");
        int k2t=(int)(Double.parseDouble(time02)/50);
        seek_parameter_02_time.setProgress(k2t);



        // PROXIMITY SENSOR
        seek_parameter_02.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                p2 = (progress/10);
                parameter_02.setText("Threshold for Proximity Sensor:"+String.valueOf(p2)+"(cm)");
                prefs2.saveData("threshold_02",String.valueOf(p2));

            }

        } );

        seek_parameter_02_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                p2t = (progress*50);
                parameter_02_time.setText("Time interval for Proximity Sensor:"+String.valueOf(p2t)+"(ms)");
                prefs2.saveData("time_interval_02",String.valueOf(p2t));

            }

        } );





        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
