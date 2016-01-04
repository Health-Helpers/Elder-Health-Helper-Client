package com.hh.ehh.database.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hh.ehh.database.tables.PatientTable;
import com.hh.ehh.model.Patient;
import com.hh.ehh.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcpe on 21/12/2015.
 */
public class PatientDatabaseManager {
    private static ContentValues patientsValues;

    public static void savePatient(Patient patient, SQLiteDatabase db) {
        patientsValues = new ContentValues();
        patientsValues.put(PatientTable.Cols.ID, patient.getId());
        patientsValues.put(PatientTable.Cols.ID_DOC, patient.getIdDoc());
        patientsValues.put(PatientTable.Cols.NAME, patient.getName());
        patientsValues.put(PatientTable.Cols.SURNAME, patient.getSurname());
        patientsValues.put(PatientTable.Cols.LANGUAGE, patient.getLanguage());
        patientsValues.put(PatientTable.Cols.PHONE, patient.getId());
        patientsValues.put(PatientTable.Cols.ADDRESS, patient.getAddress());
        patientsValues.put(PatientTable.Cols.DISEASE, patient.getDiseases());
        patientsValues.put(PatientTable.Cols.DEPENDENCY, patient.getDependencyGrade());
        db.insert(PatientTable.TABLE_NAME, null, patientsValues);
    }

    public static Patient getPatient(Patient target, SQLiteDatabase db) {
        Patient patient = null;
        User.UserBuilder builder;
        Cursor cursor = db.query(PatientTable.TABLE_NAME, PatientTable.Cols.getAllCols(), PatientTable.Cols.NAME + "=?", new String[]{PatientTable.Cols.NAME + "= " + target.getName()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            builder = new User.UserBuilder(String.valueOf(cursor.getInt(cursor.getColumnIndex(PatientTable.Cols.ID))))
                    .setIdDoc(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.ID_DOC)))
                    .setName(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.NAME)))
                    .setSurname(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.SURNAME)))
                    .setLanguage(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.LANGUAGE)))
                    .setPhone(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.PHONE)))
                    .setAddress(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.ADDRESS)));
            patient = new Patient(builder.build());
            patient.setDiseases(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.DISEASE)));
            patient.setDependencyGrade(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.DEPENDENCY)));
            cursor.close();
        }
        return patient;
    }

    public static List<Patient> getAllPatients(SQLiteDatabase db) {
        Patient patient = null;
        List<Patient> patientsList = new ArrayList<>();
        User.UserBuilder builder;
        Cursor cursor = db.query(PatientTable.TABLE_NAME, PatientTable.Cols.getAllCols(), null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                builder = new User.UserBuilder(String.valueOf(cursor.getInt(cursor.getColumnIndex(PatientTable.Cols.ID))))
                        .setIdDoc(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.ID_DOC)))
                        .setName(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.NAME)))
                        .setSurname(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.SURNAME)))
                        .setLanguage(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.LANGUAGE)))
                        .setPhone(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.PHONE)))
                        .setAddress(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.ADDRESS)));
                patient = new Patient(builder.build());
                patient.setDiseases(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.DISEASE)));
                patient.setDependencyGrade(cursor.getString(cursor.getColumnIndex(PatientTable.Cols.DEPENDENCY)));
                patientsList.add(patient);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return patientsList;
    }

    public static void deletePatient(SQLiteDatabase database) {
        database.delete(PatientTable.TABLE_NAME,null,null);
    }
}
