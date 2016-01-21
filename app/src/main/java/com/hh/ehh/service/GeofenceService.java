package com.hh.ehh.service;

import android.app.IntentService;

import android.app.PendingIntent;


import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.model.LatLng;

import com.hh.ehh.R;
import com.hh.ehh.model.Geofence;
import com.hh.ehh.model.Patient;
import com.hh.ehh.networking.SoapWebServiceConnection;

import com.hh.ehh.utils.Constants;
import com.hh.ehh.utils.GeofenceErrorMessages;
import com.hh.ehh.utils.xml.XMLHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;


/**
 * Created by Ivan on 21/01/2016.
 */
public class GeofenceService extends IntentService implements
        ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status>{


    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;

    /**
     * Used to persist application state about whether geofences were added.
     */
    private SharedPreferences mSharedPreferences;


    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;

    protected static final String TAG = "GeofenceTransitionsIS";
    private ReadGeofence readGeofences;
    public static final String ARG_PATIENT_NUMBER = "patient_number";
    Patient patient;
    protected ArrayList<com.google.android.gms.location.Geofence> mGeofenceList;

    protected GoogleApiClient mGoogleApiClient;
    List<com.hh.ehh.model.Geofence> geofencesList;


    public GeofenceService() {
        super(TAG);
    }

    public GeofenceService(GoogleApiClient mGoogleApiClient) {
        super(TAG);
        this.mGoogleApiClient = mGoogleApiClient;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected synchronized void buildGoogleApiClient() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            Log.e(TAG, "unable to connect to google play services.");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    geofenceTransition));
        }
    }


    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<com.google.android.gms.location.Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (com.google.android.gms.location.Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    private void sendNotification(String notificationDetails) {
        //TODO: Send a Push Notification
        String installationId =  ParseInstallation.getCurrentInstallation().getInstallationId();

        // Create our Installation query
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("installationId", installationId);

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query
        push.setMessage(notificationDetails);
        push.sendInBackground();
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }


    @Override
    public void onCreate() {
        // Gets data from the incoming Intent
        Log.d("Tag", "Geofencing Service Started!!!!!!!");
        super.onCreate();
        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<com.google.android.gms.location.Geofence>();

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;

        // Retrieve an instance of the SharedPreferences object.
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,MODE_PRIVATE);

        // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
        mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);

        geofencesList = new ArrayList<>();

        this.patient = new Patient();

        //FIXME: Recuperar el patientId correctamente
        this.patient.setId("1");

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();
    }

    private void readPatientGeofences(Patient patient, List<com.hh.ehh.model.Geofence> geofencesList){
        readGeofences = new ReadGeofence(geofencesList);
        try {
            readGeofences.execute(patient).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    private void createGeofence(double latitude, double longitude, int radius, String geofenceType, String title){
        mGeofenceList.add(new com.google.android.gms.location.Geofence.Builder()
                .setRequestId(title)
                .setCircularRegion(latitude, longitude,radius)
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER |
                        com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,getGeofencingRequest(),getGeofencePendingIntent()
        );
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        readPatientGeofences(patient, geofencesList);


        if(readGeofences!=null && readGeofences.geofencesList!=null && readGeofences.geofencesList.size()>0){
            for (com.hh.ehh.model.Geofence geofence : readGeofences.geofencesList) {
                createGeofence(Double.parseDouble(geofence.getLatitude()), Double.parseDouble(geofence.getLongitude()),Integer.parseInt(geofence.getRadius()), "CIRCLE", geofence.getGeofenceId());
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
    }

    public void onResult(Status status) {

    }


    private class ReadGeofence extends AsyncTask<Patient, Void, List<com.hh.ehh.model.Geofence>> {

        List<com.hh.ehh.model.Geofence> geofencesList;

        public ReadGeofence(List<com.hh.ehh.model.Geofence> geofencesList){
            this.geofencesList = geofencesList;
        }


        @Override
        protected List<com.hh.ehh.model.Geofence> doInBackground(Patient... params) {
            Patient patient = params[0];
            SoapWebServiceConnection connection = SoapWebServiceConnection.getInstance(getApplicationContext());
            String rawXML = connection.getPatientGeofence(patient);
            List<com.hh.ehh.model.Geofence> geofences = null;
            try {
                if (rawXML != null)
                    geofences = XMLHandler.getPatientGeofences(rawXML);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            geofencesList = geofences;
            return geofences;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<com.hh.ehh.model.Geofence> geofences) {
            super.onPostExecute(geofences);
        }
    }
}
