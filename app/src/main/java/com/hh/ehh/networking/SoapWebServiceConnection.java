package com.hh.ehh.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by mpifa on 19/12/15.
 */
public class SoapWebServiceConnection {
    private static SoapWebServiceConnection mConnection = null;
    private Context context;
    private static String ADD_PATIENT_TO_RESPONSIBLE = "addPatientToResponsible";
    private static String ADD_RESPONSIBLE_TO_PATIENT = "addResponsibleToPatient";
    private static String CREATE_PATIENT = "createPatient";
    private static String CREATE_RESPONSIBLE = "createResponsible";
    private static String DELETE_PATIENT = "deletePatient";
    private static String DELETE_PATIENT_RESPONSIBLE = "deletePatientResponse";
    private static String DELETE_RESPONSIBLE = "deleteResponsible";
    private static String DELETE_RESPONSIBLE_FROM_PATIENT = "deleteResponsibleFromPatient";
    private static String GET_PATIENT_RESPONSIBLES = "getPatientResponsibles";
    private static String READ_PATIENT = "readPatient";
    private static String READ_RESPONSIBLE = "readResponsible";
    private static String UPDATE_PATIENT = "updatePatient";
    private static String UPDATE_RESPONSIBLE = "updateResponsible";
    private static String GET_RESPONSIBLE_PATIENTS = "getResponsiblePatients";

    private static String NAMESPACE = "http://ws.ehh.cat/";
    private static String SOAP_WS_CONTROLLER = "/SoapWSController";
    private static String SOAP_ACTION = NAMESPACE + SOAP_WS_CONTROLLER;
    private static String URL = "http://alumnes-grp04.udl.cat/EHHWeb" + SOAP_WS_CONTROLLER;

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public SoapWebServiceConnection(Context context) {
        this.context = context;
    }

    public static SoapWebServiceConnection getInstance(Context context) {
        if (mConnection == null) {
            mConnection = new SoapWebServiceConnection(context);
        }
        return mConnection;
    }

    /***********************************************************
     * GET
     ***********************************************************/
    public String getPatient(@NonNull String id) {
        String action = SOAP_ACTION + "/" + READ_PATIENT;
        SoapObject request = new SoapObject(NAMESPACE, READ_PATIENT);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("patientId", Integer.valueOf(id));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getResponsible(@NonNull String id) {
        String action = SOAP_ACTION + "/" + READ_RESPONSIBLE;
        SoapObject request = new SoapObject(NAMESPACE, READ_RESPONSIBLE);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(id));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    public String getResponsiblePatients(@NonNull String id) {
        String action = SOAP_ACTION + "/" + GET_RESPONSIBLE_PATIENTS;
        SoapObject request = new SoapObject(NAMESPACE, GET_RESPONSIBLE_PATIENTS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        request.addProperty("responsibleId", Integer.valueOf(id));
        envelope.setOutputSoapObject(request);
        return GET(action, envelope);
    }

    private String GET(String action, SoapSerializationEnvelope envelope) {
        HttpTransportSE transporte = new HttpTransportSE(URL);
        try {
            transporte.call(action, envelope);
            Object response = envelope.getResponse();
            return String.valueOf(response);
        } catch (Exception e) {
            return null;
        }
    }

}
