package com.example.katerina.anaptyksi_1617_kmns.Connectivity;


import android.content.Context;
import android.util.Log;

import com.example.katerina.anaptyksi_1617_kmns.CollisionTools.Collision_Settings;

import	org.eclipse.paho.client.mqttv3.*;
import	org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_IP;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.BROKERS_PORT;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.CONNECTIVITY_STATUS;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.REMOTE_DANGER;
import static com.example.katerina.anaptyksi_1617_kmns.Connectivity.ConnectivitySettings.TERMINAL_UUID;


public class Mqtt_subscriber implements MqttCallback {

    private static Mqtt_subscriber subscriber;
    private static final String TAG = "Mqtt_subscriber";

    private int qos = 2;

    private static ConnectivitySettings prefs;
    private static Collision_Settings off_prefs;
    private MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient sampleClient;

    private String broker_ip = prefs.getData(BROKERS_IP);
    private String port = prefs.getData(BROKERS_PORT);
    private String broker = "tcp://"+broker_ip+":"+port;
    private String clientId = prefs.getData(TERMINAL_UUID)+"_subscriber";




    Mqtt_subscriber() {
        create_client();
    }



    public static Mqtt_subscriber getInstance(Context context) {
        prefs=ConnectivitySettings.getInstance(context);
        off_prefs = Collision_Settings.getInstance(context);

        if (subscriber == null) {
            subscriber = new Mqtt_subscriber();
        }
        return subscriber;
    }

    private void create_client(){
        Log.i(TAG,"creating client");
        try {
//Connect	client	to	MQTT	Broker
            sampleClient = new MqttClient(broker, clientId, persistence);
//Set	callback
            sampleClient.setCallback(this);

        } catch (MqttException me) {
            Log.i(TAG,"creating client -> EXCEPTION"+me);
            ConnectivityHandler.print_exception(me);
        }

    }


    public void update_client(){


        broker_ip = prefs.getData(BROKERS_IP);
        port = prefs.getData(BROKERS_PORT);
        broker = "tcp://"+broker_ip+":"+port;

        Log.i(TAG,"updating client");
        try {
//Connect	client	to	MQTT	Broker
            sampleClient = null;
            sampleClient = new MqttClient(broker, clientId, persistence);

//Set	callback
            sampleClient.setCallback(this);
            if (!sampleClient.isConnected()) {
            System.out.println("Connecting	to	broker:	" + broker);
            sampleClient.connect();
            System.out.println("Connected");}

        } catch (MqttException me) {
            Log.i(TAG,"updating client -> EXCEPTION"+me);
            ConnectivityHandler.print_exception(me);
        }


    }

    public void connect(){

        try {
//Connect	client	to	MQTT	Broker
            if (!sampleClient.isConnected()) {


            sampleClient.setCallback(this);
            System.out.println("Connecting	to	broker:	" + broker);
            sampleClient.connect();
            System.out.println("Connected");}

        } catch (MqttException me) {
            Log.i(TAG,"connect -> EXCEPTION"+me);
            ConnectivityHandler.print_exception(me);
        }

    }





    public void subscribe(String topic) {
        Log.i(TAG,"just entered mqtt subscribe ");
        if (!sampleClient.isConnected()) connect();

        try {
            Log.i(TAG,"trying to subscribe ");

            //Subscribe	to	a	topic
            sampleClient.setCallback(this);

            sampleClient.subscribe(topic,qos);
            Log.i(TAG,"Subscribing	to	topic	\"" + topic + "\"	qos " + qos );

        } catch (MqttException me) {
            Log.i(TAG,"subscribe -> EXCEPTION"+me);
            ConnectivityHandler.print_exception(me);
        }

    }

    public void UnSubscribe(String topic) {


        try {

//Subscribe	to	a	topic

            sampleClient.unsubscribe(topic);
            System.out.println("UnSubscribing	from	topic	\"" + topic  );


            System.out.println("Subscribing	to	topic	\"" + topic + "\"	qos " + qos);
            sampleClient.subscribe(topic, qos);
        } catch (MqttException me) {
            Log.i(TAG,"UnSubscribe -> EXCEPTION");
            ConnectivityHandler.print_exception(me);
        }

    }


    public void disconnect(){

        try {
            if(sampleClient.isConnected()){
            sampleClient.disconnect();
            System.out.println("Disconnected");}

        } catch (MqttException me2) {
            Log.i(TAG,"disconnect -> EXCEPTION"+me2);
            ConnectivityHandler.print_exception(me2);
        }

    }



    /**
     * @see    MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
//	This	method	is	called	when	the	connection	to	the	server	is	lost.
        System.out.println("Connection	lost!" + cause);

    }
    /**
     * @see    MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
//Called	when	delivery	for	a	message	has	been	completed,	and	all	acknowledgments	have	been	received
    }

    /**
     * @see    MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {


        String time = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" + time +
                "		Topic:\t" + topic +
                "		Message:\t" + new String(message.getPayload()) +
                "		QoS:\t" + message.getQos());


        String in_mess = new String(message.getPayload());
        Log.i(TAG,"all data: " + in_mess);



        if (topic.equals(prefs.getData(TERMINAL_UUID) + ",ON")) {

            switch (in_mess) {
                case "SAFE":
                    prefs.saveData(REMOTE_DANGER, "SAFE");
                    break;
                case "SIMPLE":
                    prefs.saveData(REMOTE_DANGER, "SIMPLE");
                    break;
                case "VERIFIED":
                    prefs.saveData(REMOTE_DANGER, "VERIFIED");
                    break;
            }
        }else if (topic.equals("Availability")) {

            if (in_mess.equals(prefs.getData(TERMINAL_UUID) + ",OFF")) {
                prefs.saveData(REMOTE_DANGER, "SAFE");
                prefs.saveData(CONNECTIVITY_STATUS,"FALSE");
            }
        }

        else if (topic.equals("Time_Intervals")){
            Log.i(TAG, "new time interval");
            String[] fieldsArray = in_mess.split(",");
            Log.i(TAG,fieldsArray[0]+" "+fieldsArray[1]);
            String key = fieldsArray[0];
            String value = fieldsArray[1];
            int millisecs =(int) Double.parseDouble(value);
            value = String.valueOf(millisecs);
            Log.i(TAG,key+" "+value);
            off_prefs.saveData(key,value);

        }
    }



}




