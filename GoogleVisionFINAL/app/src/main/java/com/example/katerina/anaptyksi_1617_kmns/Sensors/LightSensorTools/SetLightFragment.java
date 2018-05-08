package com.example.katerina.anaptyksi_1617_kmns.Sensors.LightSensorTools;

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



public class SetLightFragment extends Fragment {


    // LIGHT SENSOR
    TextView parameter_01;
    SeekBar seek_parameter_01;
    double p1=0;

    TextView parameter_01_time;
    SeekBar seek_parameter_01_time;
    double p1t=0;





    public SetLightFragment() {
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

        View view =  lf.inflate(R.layout.fragment_set_light, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());


        // LIGHT SENSOR
        parameter_01=(TextView) view.findViewById(R.id.parameter01);
        seek_parameter_01=(SeekBar) view.findViewById(R.id.seek_parameter01);

        parameter_01_time=(TextView) view.findViewById(R.id.parameter01_time);
        seek_parameter_01_time=(SeekBar) view.findViewById(R.id.seek_parameter01_time);


        // LIGHT SENSOR
        String thres01 = prefs2.getData("threshold_01");
        parameter_01.setText("Threshold for Light Sensor:"+thres01+"(lux)");
        int k1=(int)(Double.parseDouble(thres01)/400);
        seek_parameter_01.setProgress(k1);

        String time01 = prefs2.getData("time_interval_01");
        parameter_01_time.setText("Time interval for Light Sensor:"+time01+"(ms)");
        int k1t=(int)(Double.parseDouble(time01)/50);
        seek_parameter_01_time.setProgress(k1t);



        // LIGHT SENSOR
        seek_parameter_01.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                p1 = (progress*400);
                parameter_01.setText("Threshold for Light Sensor:"+String.valueOf(p1)+"(lux)");
                prefs2.saveData("threshold_01",String.valueOf(p1));

            }

        } );

        seek_parameter_01_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p1t = (progress*50);
                parameter_01_time.setText("Time interval for Light Sensor:"+String.valueOf(p1t)+"(ms)");
                prefs2.saveData("time_interval_01",String.valueOf(p1t));

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
