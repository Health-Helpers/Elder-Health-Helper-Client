package com.hh.ehh.patient;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hh.ehh.service.GeofenceService;
import com.hh.ehh.service.LocationService;


public class PatientMainActivity extends AppCompatActivity {


protected static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, LocationService.class));
       // startService(new Intent(this, BluetoothEhhService.class));
       startService(new Intent(this, GeofenceService.class));
        Toast.makeText(this.getApplicationContext(), "Services Started!", Toast.LENGTH_SHORT).show();
       finish();
    }







}
