package com.hh.ehh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mpifa on 19/12/15.
 */
public class MedicalCenter implements Parcelable{
    private String id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String phone;

    public MedicalCenter(String id, String name, String latitude, String longitude, String address, String phone) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "MedicalCenter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalCenter that = (MedicalCenter) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return !(phone != null ? !phone.equals(that.phone) : that.phone != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public static final Creator CREATOR =
            new Creator() {
                public MedicalCenter createFromParcel(Parcel in) {
                    return new MedicalCenter(in);
                }

                public MedicalCenter[] newArray(int size) {
                    return new MedicalCenter[size];
                }
            };


    public MedicalCenter(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(address);
        dest.writeString(phone);
    }
}
