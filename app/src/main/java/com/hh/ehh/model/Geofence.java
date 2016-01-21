package com.hh.ehh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan on 20/01/2016.
 */
public class Geofence implements Parcelable {
    private String geofenceId;
    private String patientId;
    private String radius;
    private String latitude;
    private String longitude;

    public Geofence() {
    }

    public Geofence(String patientId, String geofenceId, String radius, String latitude, String longitude) {
        this.geofenceId = geofenceId;
        this.patientId = patientId;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static final Creator CREATOR =
            new Creator() {
                public Geofence createFromParcel(Parcel in) {
                    return new Geofence(in);
                }

                public Geofence[] newArray(int size) {
                    return new Geofence[size];
                }
            };


    public Geofence(Parcel in) {
        geofenceId = in.readString();
        patientId = in.readString();
        radius = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    public String getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(String geofenceId) {
        this.geofenceId = geofenceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(geofenceId);
        dest.writeString(patientId);
        dest.writeString(radius);
        dest.writeString(latitude);
        dest.writeString(longitude);

    }
}