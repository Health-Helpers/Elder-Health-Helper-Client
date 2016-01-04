package com.hh.ehh.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;


public class Profile implements Parcelable {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String location;
    private String imagePath;
    private String phone;

    public Profile(String id, String name, String surname, String email, String location, String imagePath, String phone) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.location = location;
        this.imagePath = imagePath;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Drawable getImageAsDrawable() {
        return Drawable.createFromPath(imagePath);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (id != null ? !id.equals(profile.id) : profile.id != null) return false;
        if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
        if (surname != null ? !surname.equals(profile.surname) : profile.surname != null)
            return false;
        if (email != null ? !email.equals(profile.email) : profile.email != null) return false;
        if (location != null ? !location.equals(profile.location) : profile.location != null)
            return false;
        if (imagePath != null ? !imagePath.equals(profile.imagePath) : profile.imagePath != null)
            return false;
        return !(phone != null ? !phone.equals(profile.phone) : profile.phone != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public static final Creator CREATOR =
            new Creator() {
                public Profile createFromParcel(Parcel in) {
                    return new Profile(in);
                }

                public Profile[] newArray(int size) {
                    return new Profile[size];
                }
            };


    public Profile(Parcel in) {
        id = in.readString();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        location = in.readString();
        imagePath = in.readString();
        phone = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeString(location);
        dest.writeString(imagePath);
        dest.writeString(phone);
    }
}
