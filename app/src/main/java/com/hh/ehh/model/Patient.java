package com.hh.ehh.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mpifa on 19/12/15.
 */
public class Patient extends User implements Parcelable {

    private String dependencyGrade;
    private String disease;

    public Patient(User user) {
        super(user);
        dependencyGrade = null;
    }

    public String getDependencyGrade() {
        return dependencyGrade;
    }

    public void setDependencyGrade(String dependencyGrade) {
        this.dependencyGrade = dependencyGrade;
    }

    public String getDiseases() {
        return disease;
    }

    public void setDiseases(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "dependencyGrade='" + dependencyGrade + '\'' +
                ", diseases=" + disease +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Patient patient = (Patient) o;

        if (dependencyGrade != null ? !dependencyGrade.equals(patient.dependencyGrade) : patient.dependencyGrade != null)
            return false;
        return !(disease != null ? !disease.equals(patient.disease) : patient.disease != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dependencyGrade != null ? dependencyGrade.hashCode() : 0);
        result = 31 * result + (disease != null ? disease.hashCode() : 0);
        return result;
    }

    public static final Creator CREATOR =
            new Creator() {
                public Patient createFromParcel(Parcel in) {
                    return new Patient(in);
                }

                public Patient[] newArray(int size) {
                    return new Patient[size];
                }
            };


    public Patient(Parcel in) {
        super.id = in.readString();
        super.name = in.readString();
        super.surname = in.readString();
        super.birthDate = in.readString();
        super.phone = in.readString();
        super.address = in.readString();
        super.language = in.readString();
        super.idDoc = in.readString();
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
        dest.writeString(birthDate);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(language);
        dest.writeString(idDoc);
    }
}
