package com.example.katerina.anaptyksi_1617_kmns.Sensors.GPSTools;

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


public class SetGPSFragment extends Fragment {

    // LINEAR ACCELERATOR SENSOR
    TextView parameter_06_X_A;
    SeekBar seek_parameter_06_X_A;
    double p6_X_A=0;

    TextView parameter_06_X_B;
    SeekBar seek_parameter_06_X_B;
    double p6_X_B=0;

    TextView parameter_06_time;
    SeekBar seek_parameter_06_time;
    double p6t=0;

    TextView parameter_06_Y_A;
    SeekBar seek_parameter_06_Y_A;
    double p6_Y_A=0;

    TextView parameter_06_Y_B;
    SeekBar seek_parameter_06_Y_B;
    double p6_Y_B=0;

    public SetGPSFragment() {
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

        View view =  lf.inflate(R.layout.fragment_set_gps, container, false);
        final Collision_Settings prefs2=Collision_Settings.getInstance(getActivity());

        // Location Manager
        parameter_06_X_A=(TextView) view.findViewById(R.id.parameter06_X_A);
        seek_parameter_06_X_A=(SeekBar) view.findViewById(R.id.seek_parameter06_X_A);

        parameter_06_X_B=(TextView) view.findViewById(R.id.parameter06_X_B);
        seek_parameter_06_X_B=(SeekBar) view.findViewById(R.id.seek_parameter06_X_B);

        parameter_06_time=(TextView) view.findViewById(R.id.parameter06_time);
        seek_parameter_06_time=(SeekBar) view.findViewById(R.id.seek_parameter06_time);

        parameter_06_Y_A=(TextView) view.findViewById(R.id.parameter06_Y_A);
        seek_parameter_06_Y_A=(SeekBar) view.findViewById(R.id.seek_parameter06_Y_A);

        parameter_06_Y_B=(TextView) view.findViewById(R.id.parameter06_Y_B);
        seek_parameter_06_Y_B=(SeekBar) view.findViewById(R.id.seek_parameter06_Y_B);



        // Location Manager
        String thres06_X_B = prefs2.getData("threshold_06_X_B");
        parameter_06_X_B.setText("Threshold B for Longitude:"+thres06_X_B+"");
        int k6_X_B=(int)(Double.parseDouble(thres06_X_B));
        seek_parameter_06_X_B.setProgress((int)((k6_X_B+180)/3.6));

        String thres06_X_A = prefs2.getData("threshold_06_X_A");
        parameter_06_X_A.setText("Threshold A for Longitude:"+thres06_X_A+"");
        int k6_X_A=(int)(Double.parseDouble(thres06_X_A));
        seek_parameter_06_X_A.setProgress((int)((k6_X_A+180)/3.6));

        String time06 = prefs2.getData("time_interval_06");
        parameter_06_time.setText("Time interval for Location Manager:"+time06+"");
        int k6t=(int)(Double.parseDouble(time06)/50);
        seek_parameter_06_time.setProgress(k6t);

        String thres06_Y_A = prefs2.getData("threshold_06_Y_A");
        parameter_06_Y_A.setText("Threshold A for Latitude:"+thres06_Y_A+"");
        int k6_Y_A=(int)(Double.parseDouble(thres06_Y_A));
        seek_parameter_06_Y_A.setProgress((int)((k6_Y_A+90)/1.8));

        String thres06_Y_B = prefs2.getData("threshold_06_Y_B");
        parameter_06_Y_B.setText("Threshold B for Latitude:"+thres06_Y_B+"");
        int k6_Y_B=(int)(Double.parseDouble(thres06_Y_B));
        seek_parameter_06_Y_B.setProgress((int)((k6_Y_B+90)/1.8));


        // LINEAR ACCELERATOR SENSOR
        seek_parameter_06_X_A.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p6_X_A = ((progress*3.6)-180);
                parameter_06_X_A.setText("Threshold A for Longitude:"+String.valueOf(p6_X_A)+"");
                prefs2.saveData("threshold_06_X_A",String.valueOf(p6_X_A));

            }

        } );

        // LINEAR ACCELERATOR SENSOR
        seek_parameter_06_X_B.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p6_X_B = ((progress*3.6)-180);
                parameter_06_X_B.setText("Threshold B for Longitude:"+String.valueOf(p6_X_B)+"");
                prefs2.saveData("threshold_06_X_B",String.valueOf(p6_X_B));

            }

        } );

        seek_parameter_06_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                p6t = (progress*50);
                parameter_06_time.setText("Time interval for Location Manager:"+String.valueOf(p6t)+"");
                prefs2.saveData("time_interval_06",String.valueOf(p6t));

            }

        } );



        seek_parameter_06_Y_A.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p6_Y_A = ((progress*1.8)-90);
                parameter_06_Y_A.setText("Threshold A for Latitude:"+String.valueOf(p6_Y_A)+"");
                prefs2.saveData("threshold_06_Y_A",String.valueOf(p6_Y_A));

            }

        } );

        seek_parameter_06_Y_B.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                p6_Y_B = ((progress*1.8)-90);
                parameter_06_Y_B.setText("Threshold B for Latitude:"+String.valueOf(p6_Y_B)+"");
                prefs2.saveData("threshold_06_Y_B",String.valueOf(p6_Y_B));

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
