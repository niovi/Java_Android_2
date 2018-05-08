package com.example.katerina.anaptyksi_1617_kmns.Sensors.AcceleratorSensorTools;

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


public class SetAcceleratorFragment extends Fragment {


    // LINEAR ACCELERATOR SENSOR
    TextView parameter_05_X;
    SeekBar seek_parameter_05_X;
    double p5_X=0;

    TextView parameter_05_time;
    SeekBar seek_parameter_05_time;
    double p5t=0;

    TextView parameter_05_Y;
    SeekBar seek_parameter_05_Y;
    double p5_Y=0;


    TextView parameter_05_Z;
    SeekBar seek_parameter_05_Z;
    double p5_Z=0;



    public SetAcceleratorFragment() {
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

        View view =  lf.inflate(R.layout.fragment_set_accelerator, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());

        // LINEAR ACCELERATOR SENSOR
        parameter_05_X=(TextView) view.findViewById(R.id.parameter05_X);
        seek_parameter_05_X=(SeekBar) view.findViewById(R.id.seek_parameter05_X);

        parameter_05_time=(TextView) view.findViewById(R.id.parameter05_time);
        seek_parameter_05_time=(SeekBar) view.findViewById(R.id.seek_parameter05_time);

        parameter_05_Y=(TextView) view.findViewById(R.id.parameter05_Y);
        seek_parameter_05_Y=(SeekBar) view.findViewById(R.id.seek_parameter05_Y);

        parameter_05_Z=(TextView) view.findViewById(R.id.parameter05_Z);
        seek_parameter_05_Z=(SeekBar) view.findViewById(R.id.seek_parameter05_Z);





        // LINEAR ACCELERATOR SENSOR
        String thres05_X = prefs2.getData("threshold_05_X");
        parameter_05_X.setText("Threshold for Accelerator Sensor, X axis: |"+thres05_X+"| (m/s^2)");
        int k5_X=(int)(Double.parseDouble(thres05_X));
        seek_parameter_05_X.setProgress(k5_X);

        String time05 = prefs2.getData("time_interval_05");
        parameter_05_time.setText("Time interval for Accelerator Sensor: "+time05+" (ms)");
        int k5t=(int)(Double.parseDouble(time05)/50);
        seek_parameter_05_time.setProgress(k5t);

        String thres05_Y = prefs2.getData("threshold_05_Y");
        parameter_05_Y.setText("Threshold for Accelerator Sensor, Y axis: |"+thres05_Y+"| (m/s^2)");
        int k5_Y=(int)(Double.parseDouble(thres05_Y));
        seek_parameter_05_Y.setProgress(k5_Y);

        String thres05_Z = prefs2.getData("threshold_05_Z");
        parameter_05_Z.setText("Threshold for Accelerator Sensor, Z axis: |"+thres05_Z+"| (m/s^2)");
        int k5_Z=(int)(Double.parseDouble(thres05_Z));
        seek_parameter_05_Z.setProgress(k5_Z);





        // LINEAR ACCELERATOR SENSOR
        seek_parameter_05_X.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p5_X = (progress);
                parameter_05_X.setText("Threshold for Accelerator Sensor, X axis: |"+String.valueOf(p5_X)+"| (m/s^2)");
                prefs2.saveData("threshold_05_X",String.valueOf(p5_X));

            }

        } );

        seek_parameter_05_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                p5t = (progress*50);
                parameter_05_time.setText("Time interval for Accelerator Sensor: "+String.valueOf(p5t)+" (ms)");
                prefs2.saveData("time_interval_05",String.valueOf(p5t));

            }

        } );



        seek_parameter_05_Y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p5_Y = (progress);
                parameter_05_Y.setText("Threshold for Accelerator Sensor, Y axis: |"+String.valueOf(p5_Y)+"| (m/s^2)");
                prefs2.saveData("threshold_05_Y",String.valueOf(p5_Y));

            }

        } );



        seek_parameter_05_Z.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p5_Z = (progress);
                parameter_05_Z.setText("Threshold for Accelerator Sensor, Z axis: |"+String.valueOf(p5_Z)+"| (m/s^2)");
                prefs2.saveData("threshold_05_Z",String.valueOf(p5_Z));

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
