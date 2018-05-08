package com.example.katerina.anaptyksi_1617_kmns.Sensors.GyroscopeSensorTools;

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


public class SetGyroscopeFragment extends Fragment {


    // GYROSCOPE SENSOR
    TextView parameter_03_X;
    SeekBar seek_parameter_03_X;
    double p3_X=0;

    TextView parameter_03_time;
    SeekBar seek_parameter_03_time;
    double p3t=0;

    TextView parameter_03_Y;
    SeekBar seek_parameter_03_Y;
    double p3_Y=0;

    TextView parameter_03_Z;
    SeekBar seek_parameter_03_Z;
    double p3_Z=0;

    public SetGyroscopeFragment() {
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

        View view =  lf.inflate(R.layout.fragment_set_gyroscope, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());



        // GYROSCOPE SENSOR
        parameter_03_X=(TextView) view.findViewById(R.id.parameter03_X);
        seek_parameter_03_X=(SeekBar) view.findViewById(R.id.seek_parameter03_X);

        parameter_03_time=(TextView) view.findViewById(R.id.parameter03_time);
        seek_parameter_03_time=(SeekBar) view.findViewById(R.id.seek_parameter03_time);

        parameter_03_Y=(TextView) view.findViewById(R.id.parameter03_Y);
        seek_parameter_03_Y=(SeekBar) view.findViewById(R.id.seek_parameter03_Y);

        parameter_03_Z=(TextView) view.findViewById(R.id.parameter03_Z);
        seek_parameter_03_Z=(SeekBar) view.findViewById(R.id.seek_parameter03_Z);

        // GYROSCOPE SENSOR
        String thres03_X = prefs2.getData("threshold_03_X");
        parameter_03_X.setText("Threshold for Gyroscope Sensor, X axis: |"+thres03_X+"| (rad/s)");
        int k3_X=(int)(Double.parseDouble(thres03_X));
        seek_parameter_03_X.setProgress(k3_X);

        String time03 = prefs2.getData("time_interval_03");
        parameter_03_time.setText("Time interval for Gyroscope Sensor: "+time03+" (ms)");
        int k3t=(int)(Double.parseDouble(time03)/50);
        seek_parameter_03_time.setProgress(k3t);

        String thres03_Y = prefs2.getData("threshold_03_Y");
        parameter_03_Y.setText("Threshold for Gyroscope Sensor, Y axis: |"+thres03_Y+"| (rad/s)");
        int k3_Y=(int)(Double.parseDouble(thres03_Y));
        seek_parameter_03_Y.setProgress(k3_Y);

        String thres03_Z = prefs2.getData("threshold_03_Z");
        parameter_03_Z.setText("Threshold for Gyroscope Sensor, Z axis: |"+thres03_Z+"| (rad/s)");
        int k3_Z=(int)(Double.parseDouble(thres03_Z));
        seek_parameter_03_Z.setProgress(k3_Z);


        // GYROSCOPE SENSOR
        seek_parameter_03_X.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p3_X = (progress);
                parameter_03_X.setText("Threshold for Gyroscope Sensor, X axis: |"+String.valueOf(p3_X)+"| (rad/s)");
                prefs2.saveData("threshold_03_X",String.valueOf(p3_X));

            }

        } );

        seek_parameter_03_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                p3t = (progress*50);
                parameter_03_time.setText("Time interval for Gyroscope Sensor, X axis:"+String.valueOf(p3t)+"(ms)");
                prefs2.saveData("time_interval_03",String.valueOf(p3t));

            }

        } );



        seek_parameter_03_Y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p3_Y = (progress);
                parameter_03_Y.setText("Threshold for Gyroscope Sensor, Y axis: |"+String.valueOf(p3_Y)+"| (rad/s)");
                prefs2.saveData("threshold_03_Y",String.valueOf(p3_Y));

            }

        } );


        seek_parameter_03_Z.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p3_Z = (progress);
                parameter_03_Z.setText("Threshold for Gyroscope Sensor, Z axis: |"+String.valueOf(p3_Z)+"| (rad/s)");
                prefs2.saveData("threshold_03_Z",String.valueOf(p3_Z));

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
