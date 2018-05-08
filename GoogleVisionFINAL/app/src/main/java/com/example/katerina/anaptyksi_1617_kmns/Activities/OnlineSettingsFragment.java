package com.example.katerina.anaptyksi_1617_kmns.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivityHandler;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings;
import com.example.katerina.anaptyksi_1617_kmns.Connectivity.Mqtt_publisher;
import com.example.katerina.anaptyksi_1617_kmns.R;


import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_IP;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_PORT;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_AVAILABLE;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.TERMINAL_UUID;


public class OnlineSettingsFragment extends Fragment {

    private EditText ip;

    private EditText port;

    private static final String TAG = "OnlineSettingsFragment";




    public OnlineSettingsFragment() {
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

        View view = lf.inflate(R.layout.fragment_online_settings, container, false);

        final ConnectivitySettings prefs = ConnectivitySettings.getInstance(getActivity());

        ip = (EditText)view.findViewById(R.id.ip_first);
        port = (EditText)view.findViewById(R.id.editText5);

        Button available = (Button) view.findViewById(R.id.button);
        Button ok1 = (Button) view.findViewById(R.id.button2);
        Button ok2 = (Button) view.findViewById(R.id.button3);




        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (String split : splits) {
                            if (Integer.valueOf(split) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
        ip.setFilters(filters);


        View.OnClickListener click_listener_0 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String ip_address = ip.getText().toString();
                if(!ip_address.equals("")){
                prefs.saveData(BROKERS_IP,ip_address);}

            }


        };

        ok1.setOnClickListener(click_listener_0);



        View.OnClickListener click_listener_1 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String port_num = port.getText().toString();
                if(!port_num.equals("")){
                prefs.saveData(BROKERS_PORT,port_num);}

            }


        };

        ok2.setOnClickListener(click_listener_1);


        View.OnClickListener click_listener_2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ConnectivityHandler handler = ConnectivityHandler.getInstance(getActivity());
                handler.isNetworkConnected(getActivity());
                    if(prefs.getData(CONNECTIVITY_STATUS).equals("TRUE") &&prefs.getData(CONNECTIVITY_AVAILABLE).equals("TRUE")){
                        Log.i(TAG,"trying to publish availability");

                        Mqtt_publisher publisher = Mqtt_publisher.getInstance(getActivity());

                        publisher.publish("Availability",prefs.getData(TERMINAL_UUID)+",ON");

                    }
                else Log.i(TAG,"Connectivity handler not ready");
                Log.i(TAG,"STATUS: "+prefs.getData(CONNECTIVITY_STATUS));
                Log.i(TAG,"AVAILABLE: "+prefs.getData(CONNECTIVITY_AVAILABLE));


            }


        };

        available.setOnClickListener(click_listener_2);


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