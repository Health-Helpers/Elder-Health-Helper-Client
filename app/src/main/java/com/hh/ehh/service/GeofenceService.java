package com.hh.ehh.service;

import android.app.IntentService;

import android.app.PendingIntent;


import android.content.Context;

import android.content.Intent;

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


/**
 * Created by Ivan on 21/01/2016.
 */
public class GeofenceService extends IntentService implements
        ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<Status>{

    protected static final String TAG = "GeofenceTransitionsIS";
    private ReadGeofence readGeofences;
    public static final String ARG_PATIENT_NUMBER = "patient_number";
    Patient patient;
    protected ArrayList<com.google.android.gms.location.Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    protected GoogleApiClient mGoogleApiClient;

    public GeofenceService() {
        super(TAG);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
        this.mGeofenceList = new ArrayList<>();
        this.patient = new Patient();

        //FIXME: Recuperar el patientId correctamente
        this.patient.setId("1");


        readPatientGeofences(patient);

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

    }

    private void readPatientGeofences(Patient patient){
        readGeofences = new ReadGeofence();
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
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
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


        LatLng geofenceCenter = new LatLng(latitude, longitude);

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            Log.e(TAG, "unable to connect to google play services.");
        }

        mGeofenceList.add(new com.google.android.gms.location.Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(title)

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        latitude, longitude,
                        radius
                )

                        // Set the expiration duration of the geofence. This geofence gets automatically
                        // removed after this period of time.
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                        // Set the transition types of interest. Alerts are only generated for these
                        // transition. We track entry and exit transitions in this sample.
                .setTransitionTypes(com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER |
                        com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT)

                        // Create the geofence.
                .build());



        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                // The GeofenceRequest object.
                getGeofencingRequest(),
                // A pending intent that that is reused when calling removeGeofences(). This
                // pending intent is used to generate an intent when a matched geofence
                // transition is observed.
                getGeofencePendingIntent()
        );
    }

      //  Marker stopMarker = googleMap.addMarker(new MarkerOptions().draggable(true).position(geofenceCenter).title(title).infoWindowAnchor(10, 10).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ehh)));


        //googleMap.addCircle(new CircleOptions().center(geofenceCenter).radius(radius).strokeColor(Color.parseColor("#ffff00")).fillColor(Color.TRANSPARENT));


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        Log.i(TAG, "Connection suspended");

        // onConnected() will be called again automatically when the service reconnects
    }

    public void onResult(Status status) {

    }


    private class ReadGeofence extends AsyncTask<Patient, Void, List<Geofence>> {

        private SoapWebServiceConnection soapWebServiceConnection;
        private Geofence geofence;

        Patient patient;


        public ReadGeofence(){}

        public ReadGeofence(SoapWebServiceConnection soapWebServiceConnection,Patient patient) {
            this.soapWebServiceConnection = soapWebServiceConnection;
            this.patient = patient;
        }

        @Override
        protected List<Geofence> doInBackground(Patient... params) {
            Patient patient = params[0];
            SoapWebServiceConnection connection = SoapWebServiceConnection.getInstance(getApplicationContext());
            String rawXML = connection.getPatientGeofence(patient);
            List<Geofence> geofences = null;
            try {
                if (rawXML != null)
                    geofences = XMLHandler.getPatientGeofences(rawXML);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return geofences;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Geofence> geofences) {
            super.onPostExecute(geofences);
            if(geofences!=null) {



                for (Geofence geofence : geofences) {
                    createGeofence(Double.parseDouble(geofence.getLatitude()), Double.parseDouble(geofence.getLongitude()),Integer.parseInt(geofence.getRadius()), "CIRCLE", geofence.getGeofenceId());
                }
            }
        }
    }
}
