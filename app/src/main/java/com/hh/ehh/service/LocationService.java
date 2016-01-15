package com.hh.ehh.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.hh.ehh.model.LocationEHH;
import com.hh.ehh.networking.SoapWebServiceConnection;
import com.hh.ehh.utils.DateUtil;

import java.util.Date;


/**
 * Created by mpifa on 3/1/16.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "LocationService";
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private PostLocation postLocation;
    private boolean currentlyProcessingLocation = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FATEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
        stopLocationUpdates();
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "GoogleApiClient connection has been suspend");
    }


    private class PostLocation extends AsyncTask<LocationEHH, Void, Void> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private LocationEHH location;
        private ProgressDialog dialog;


        public PostLocation(SoapWebServiceConnection soapWebServiceConnection, LocationEHH location) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.location = location;
            this.location.setLatitude(location.getLatitude());
            this.location.setLongitude(location.getLongitude());
            this.location.setPatientId(location.getPatientId());
        }



        @Override
        protected Void doInBackground(LocationEHH... params) {
            LocationEHH location = params[0];
            soapWebServiceConnection.sendPatientLocation(location.getPatientId(),location.getDate(),location.getLatitude(),location.getLongitude());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /* dialog = new ProgressDialog(getApplicationContext());
            dialog.setMessage(getApplicationContext().getResources().getString(R.string.loading));
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    postLocation.cancel(true);
                }
            });
            dialog.show();*/

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null)
                dialog.cancel();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e(TAG, "position: " + location.getLatitude() + ", " + location.getLongitude() + " accuracy: " + location.getAccuracy());
            // we have our desired accuracy of 10 meters so lets quit this service,
            // onDestroy will be called and stop our location uodates
           // if (location.getAccuracy() <= 10) {
                stopLocationUpdates();
                sendLocationDataToWebsite(location);
            //}
        }
    }

    private void sendLocationDataToWebsite(Location location) {
        if (SoapWebServiceConnection.checkInternetConnection(getApplicationContext())) {
            SoapWebServiceConnection soapWebServiceConnection = SoapWebServiceConnection.getInstance(getApplicationContext());

            //TODO: Get the correct patientId
            LocationEHH locationEhh = new LocationEHH("1", DateUtil.getStringFromDate(new Date()),String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            PostLocation postLocation = new PostLocation(soapWebServiceConnection,locationEhh);
            postLocation.execute(locationEhh);
            //soapWebServiceConnection.sendPatientLocation(locationEhh.getPatientId(), locationEhh.getDate(), locationEhh.getLatitude(), locationEhh.getLongitude());
        }
    }

    private void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // if we are currently trying to get a location and the alarm manager has called this again,
        // no need to start processing a new location.
        if (!currentlyProcessingLocation) {
            currentlyProcessingLocation = true;
            startTracking();
        }

        return START_NOT_STICKY;
    }

    private void startTracking() {
        Log.d(TAG, "startTracking");

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        } else {
            Log.e(TAG, "unable to connect to google play services.");
        }
    }
}
