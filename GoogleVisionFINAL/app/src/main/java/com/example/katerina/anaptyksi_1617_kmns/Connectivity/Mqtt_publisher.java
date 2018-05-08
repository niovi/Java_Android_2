package com.example.katerina.anaptyksi_1617_kmns.Connectivity;


import android.content.Context;
import android.util.Log;


import	org.eclipse.paho.client.mqttv3.MqttClient;
import	org.eclipse.paho.client.mqttv3.MqttException;
import	org.eclipse.paho.client.mqttv3.MqttMessage;
import	org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_IP;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_PORT;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.TERMINAL_UUID;


public class Mqtt_publisher {


    private  static  Context pub_context;
    private static Mqtt_publisher publisher;
    private static final String TAG = "Mqtt_publisher";



    private int qos =	2;

    // to find host: nmap -sn 192.168.1.0/24
    private  ConnectivitySettings prefs = ConnectivitySettings.getInstance(pub_context);

    private MemoryPersistence persistence =	new	MemoryPersistence();
    private MqttClient sampleClient;

    private String broker_ip = prefs.getData(BROKERS_IP);

    private String port = prefs.getData(BROKERS_PORT);
    private String broker = "tcp://"+broker_ip+":"+port;
    private String clientId = prefs.getData(TERMINAL_UUID)+"_publisher";



    private Mqtt_publisher() {
        create_client();
    }



    public static Mqtt_publisher getInstance(Context context) {
        pub_context = context;

        if (publisher == null) {
            publisher = new Mqtt_publisher();
        }
        return publisher;
    }





    private void create_client(){

        Log.i(TAG,"creating client");
        try {
            //Connect	to	MQTT	Broker
            sampleClient = new MqttClient(broker, clientId, persistence);


        } catch (MqttException me2) {
            Log.i(TAG,"creating client -> EXCEPTION "+me2);
            ConnectivityHandler.print_exception(me2);
        }



    }

    public void update_client(){

        Log.i(TAG,"updating client");
        broker_ip = prefs.getData(BROKERS_IP);

        port = prefs.getData(BROKERS_PORT);
        broker = "tcp://"+broker_ip+":"+port;
        try {
            //Connect	to	MQTT	Broker
            sampleClient = null;
            sampleClient = new MqttClient(broker, clientId, persistence);
            if (!sampleClient.isConnected()) {
            Log.i(TAG,"Connecting	to	broker:	" + broker);
            sampleClient.connect();
            Log.i(TAG,"Connected");}

        } catch (MqttException me2) {
            Log.i(TAG,"updating client -> EXCEPTION"+me2);
            ConnectivityHandler.print_exception(me2);
        }
    }


    public void connect(){


        try {
            //Connect	to	MQTT	Broker

            if(!sampleClient.isConnected()){


            Log.i(TAG,"Connecting	to	broker:	" + broker);
            sampleClient.connect();
            Log.i(TAG,"Connected");}

        } catch (MqttException me2) {
            Log.i(TAG,"connect -> EXCEPTION  "+me2);
            ConnectivityHandler.print_exception(me2);
        }



    }



    public void publish(String topic, String content) {

        Log.i(TAG,"just entered mqtt publish ");

        try {
//Publish	message	to	MQTT	Broker
            System.out.println("Publishing	message:	" + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            if(sampleClient.isConnected()){
            sampleClient.publish(topic, message);
            Log.i(TAG, "Message	published. topic: "+topic+" message: "+message);}
            else Log.i(TAG, "Not ready");
        } catch (MqttException me2) {
            Log.i(TAG,"publish -> EXCEPTION "+me2);
            ConnectivityHandler.print_exception(me2);
        }
    }



    public void disconnect(){

        try {
            if (sampleClient.isConnected()){
            sampleClient.disconnect();
            Log.i(TAG,"Disconnected");}

        } catch (MqttException me2) {
            Log.i(TAG,"disconnect -> EXCEPTION"+me2);
            ConnectivityHandler.print_exception(me2);
        }

    }







}

