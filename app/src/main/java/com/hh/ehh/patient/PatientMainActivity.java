package com.hh.ehh.patient;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hh.ehh.bluetooth.BluetoothSPP;
import com.hh.ehh.service.EHHService;
import com.hh.ehh.service.LocationService;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.FragmentStackManager;

public class PatientMainActivity extends Activity {

    private FragmentStackManager fragmentStackManager;
    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.patient_home_fragment);
        startService(new Intent(this, EHHService.class));
        startService(new Intent(this, LocationService.class));
        Toast.makeText(this.getApplicationContext(), "Service Started!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (fragmentStackManager.popBackStatFragment()) {
        } else {
            CustomDialogs.closeDialog(PatientMainActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

            bt.stopService();
        }
    }
}
