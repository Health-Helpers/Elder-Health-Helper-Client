package com.hh.ehh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ivanjosa on 15/1/16.
 */
public class LocationEHH implements Parcelable {
    private String patientId;
    private String date;
    private String latitude;
    private String longitude;

    public LocationEHH( String patientId,String date, String latitude, String longitude) {
        this.patientId = patientId;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static final Creator CREATOR =
            new Creator() {
                public LocationEHH createFromParcel(Parcel in) {
                    return new LocationEHH(in);
                }

                public LocationEHH[] newArray(int size) {
                    return new LocationEHH[size];
                }
            };


    public LocationEHH(Parcel in) {
        patientId = in.readString();
        date = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(latitude);
        dest.writeString(longitude);

    }
}
