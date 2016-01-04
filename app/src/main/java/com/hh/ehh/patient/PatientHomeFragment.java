package com.hh.ehh.patient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.Toast;

import com.hh.ehh.R;
import com.hh.ehh.bluetooth.BluetoothSPP;
import com.hh.ehh.bluetooth.BluetoothState;
import com.hh.ehh.bluetooth.DeviceList;


import java.util.ArrayList;

/**
 * Created by mpifa on 23/11/15.
 */
public class PatientHomeFragment extends Fragment {

    private Button btnBluetooth;
    private BluetoothSPP bt;
    private Button callButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.patient_activity_main, container, false);
        callButton = (Button) view.findViewById(R.id.callBtn);


        btnBluetooth = (Button) view.findViewById(R.id.btnBluetooth);
        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                bt = new BluetoothSPP(getActivity());


                if(!bt.isBluetoothEnabled())
                {
                    bt.enable();
                }else {
                    if(!bt.isServiceAvailable()) {
                        bt.setupService();
                        bt.startService(BluetoothState.DEVICE_ANDROID);
                    }
                }


                bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);
                Intent intent = new Intent(getActivity().getApplicationContext(), DeviceList.class);
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);



                if (!bt.isBluetoothAvailable()) {
                    Toast.makeText(getActivity().getApplicationContext()
                            , "Bluetooth is not available"
                            , Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }

                bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onDataReceived(byte[] data, String message) {
                        if (message.equals("1")) {
                            callButton.setBackground(getResources().getDrawable(R.drawable.custom_red_button));
                            Log.d("RECEIVED", "CALLING...");
                            //textRead.append("CALLING..." + "\n");
                        } else if (message.equals("0")) {
                            callButton.setBackground(getResources().getDrawable(R.drawable.custom_green_button));
                            Log.d("RECEIVED", "NO...");
                            //textRead.append("NO" + "\n");
                        }
                    }
                });

                bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
                    public void onDeviceDisconnected() {
                        Log.d("Status", "Not connect");
                        //textStatus.setText("Status : Not connect");
                        //menu.clear();
                        //getMenuInflater().inflate(R.menu.menu_connection, menu);
                    }

                    public void onDeviceConnectionFailed() {
                        Log.d("Status", "Connection failed");
                        //textStatus.setText("Status : Connection failed");
                    }

                    public void onDeviceConnected(String name, String address) {
                        Log.d("Status", "Connected to " + name);
                        Toast.makeText(getActivity().getApplicationContext()
                                , "Button Connected."
                                , Toast.LENGTH_SHORT).show();
                        //textStatus.setText("Status : Connected to " + name);
                        //menu.clear();
                        //getMenuInflater().inflate(R.menu.menu_disconnection, menu);
                    }
                });


            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK) {
                bt.connect(data);
            }
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                //setup();
            } else {
                Toast.makeText(getActivity().getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }


    /*@Override
    public void action(String action) {

        if (action.compareTo(ACTION_DISCOVERY_STARTED) == 0) {
            arrayAdapter = new ArrayList<String>();
            arrayAdapter.add("-Started Find");
        } else if (action.compareTo(ACTION_DISCOVERY_FINISHED) == 0) {
            arrayAdapter = bluetooth.preencherLista();
            arrayAdapter.add("-Finished Find");
        }
    }
    */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
