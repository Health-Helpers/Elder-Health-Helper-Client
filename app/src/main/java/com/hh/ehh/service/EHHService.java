package com.hh.ehh.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ivanjosa on 15/1/16.
 */
public class EHHService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public EHHService(String name) {
        super(name);
    }

    public EHHService() {
        super("EHHService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();
        Log.d("Tag","Service Started!!!!!!!");
    }



}
