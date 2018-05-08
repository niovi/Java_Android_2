package com.example.katerina.anaptyksi_1617_kmns.Sensors.LinearAcceleratorSensorTools;

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


public class SetLinearAcceleratorFragment extends Fragment {


    // LINEAR ACCELERATOR SENSOR
    TextView parameter_04_X;
    SeekBar seek_parameter_04_X;
    double p4_X=0;

    TextView parameter_04_time;
    SeekBar seek_parameter_04_time;
    double p4t=0;

    TextView parameter_04_Y;
    SeekBar seek_parameter_04_Y;
    double p4_Y=0;


    TextView parameter_04_Z;
    SeekBar seek_parameter_04_Z;
    double p4_Z=0;



    public SetLinearAcceleratorFragment() {
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

        View view =  lf.inflate(R.layout.fragment_set_linear_accelerator, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());

        // LINEAR ACCELERATOR SENSOR
        parameter_04_X=(TextView) view.findViewById(R.id.parameter04_X);
        seek_parameter_04_X=(SeekBar) view.findViewById(R.id.seek_parameter04_X);

        parameter_04_time=(TextView) view.findViewById(R.id.parameter04_time);
        seek_parameter_04_time=(SeekBar) view.findViewById(R.id.seek_parameter04_time);

        parameter_04_Y=(TextView) view.findViewById(R.id.parameter04_Y);
        seek_parameter_04_Y=(SeekBar) view.findViewById(R.id.seek_parameter04_Y);

        parameter_04_Z=(TextView) view.findViewById(R.id.parameter04_Z);
        seek_parameter_04_Z=(SeekBar) view.findViewById(R.id.seek_parameter04_Z);





        // LINEAR ACCELERATOR SENSOR
        String thres04_X = prefs2.getData("threshold_04_X");
        parameter_04_X.setText("Threshold for Linear Accelerator Sensor, X axis: |"+thres04_X+"| (m/s^2)");
        int k4_X=(int)(Double.parseDouble(thres04_X));
        seek_parameter_04_X.setProgress(k4_X);

        String time04 = prefs2.getData("time_interval_04");
        parameter_04_time.setText("Time interval for Linear Accelerator Sensor:"+time04+"(ms)");
        int k4t=(int)(Double.parseDouble(time04)/50);
        seek_parameter_04_time.setProgress(k4t);

        String thres04_Y = prefs2.getData("threshold_04_Y");
        parameter_04_Y.setText("Threshold for Linear Accelerator, Y axis: |"+thres04_Y+"| (m/s^2)");
        int k4_Y=(int)(Double.parseDouble(thres04_Y));
        seek_parameter_04_Y.setProgress(k4_Y);

        String thres04_Z = prefs2.getData("threshold_04_Z");
        parameter_04_Z.setText("Threshold for Linear Accelerator Sensor, Z axis: |"+thres04_Z+"| (m/s^2)");
        int k4_Z=(int)(Double.parseDouble(thres04_Z));
        seek_parameter_04_Z.setProgress(k4_Z);





        // LINEAR ACCELERATOR SENSOR
        seek_parameter_04_X.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p4_X = (progress);
                parameter_04_X.setText("Threshold for Linear Accelerator Sensor, X axis: |"+String.valueOf(p4_X)+"| (m/s^2)");
                prefs2.saveData("threshold_04_X",String.valueOf(p4_X));

            }

        } );

        seek_parameter_04_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                p4t = (progress*50);
                parameter_04_time.setText("Time interval for Linear Accelerator Sensor: "+String.valueOf(p4t)+" (ms)");
                prefs2.saveData("time_interval_04",String.valueOf(p4t));

            }

        } );



        seek_parameter_04_Y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p4_Y = (progress);
                parameter_04_Y.setText("Threshold for Linear Accelerator Sensor, Y axis: |"+String.valueOf(p4_Y)+"| (m/s^2)");
                prefs2.saveData("threshold_04_Y",String.valueOf(p4_Y));

            }

        } );



        seek_parameter_04_Z.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p4_Z = (progress);
                parameter_04_Z.setText("Threshold for Linear Accelerator Sensor, Z axis: |"+String.valueOf(p4_Z)+"| (m/s^2)");
                prefs2.saveData("threshold_04_Z",String.valueOf(p4_Z));

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
