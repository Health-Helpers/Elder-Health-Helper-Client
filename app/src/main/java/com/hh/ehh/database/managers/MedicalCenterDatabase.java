package com.hh.ehh.database.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hh.ehh.database.tables.MedicalCentersTable;
import com.hh.ehh.model.MedicalCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcpe on 21/12/2015.
 */
public class MedicalCenterDatabase {
    private static ContentValues medicalCentersValues;

    public static void saveMedicalCenter(MedicalCenter medicalCenter, SQLiteDatabase db) {
        medicalCentersValues = new ContentValues();
        medicalCentersValues.put(MedicalCentersTable.Cols.ID, medicalCenter.getId());
        medicalCentersValues.put(MedicalCentersTable.Cols.NAME, medicalCenter.getId());
        medicalCentersValues.put(MedicalCentersTable.Cols.LATITUDE, medicalCenter.getId());
        medicalCentersValues.put(MedicalCentersTable.Cols.LONGITUDE, medicalCenter.getId());
        medicalCentersValues.put(MedicalCentersTable.Cols.ADDRESS, medicalCenter.getId());
        medicalCentersValues.put(MedicalCentersTable.Cols.PHONE, medicalCenter.getId());
        db.insert(MedicalCentersTable.TABLE_NAME, null, medicalCentersValues);
    }

    public static MedicalCenter getMedicalCenter(MedicalCenter target, SQLiteDatabase db) {
        MedicalCenter medicalCenter = null;
        Cursor cursor = db.query(MedicalCentersTable.TABLE_NAME, MedicalCentersTable.Cols.getAllCols(), MedicalCentersTable.Cols.NAME + "=?", new String[]{MedicalCentersTable.Cols.NAME + "= " + target.getName()}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            medicalCenter = new MedicalCenter(
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(MedicalCentersTable.Cols.ID))),
                    cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.PHONE))
            );

            cursor.close();
        }
        return medicalCenter;
    }

    public static List<MedicalCenter> getAllHospitals(SQLiteDatabase db) {
        MedicalCenter medicalCenter = null;
        List<MedicalCenter> medicalCenters = new ArrayList<>();
        Cursor cursor = db.query(MedicalCentersTable.TABLE_NAME, MedicalCentersTable.Cols.getAllCols(), null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                medicalCenter = new MedicalCenter(
                        String.valueOf(cursor.getInt(cursor.getColumnIndex(MedicalCentersTable.Cols.ID))),
                        cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.NAME)),
                        cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(MedicalCentersTable.Cols.PHONE))
                );
                medicalCenters.add(medicalCenter);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return medicalCenters;
    }

    public static void deleteHospitals(SQLiteDatabase database) {
        database.delete(MedicalCentersTable.TABLE_NAME,null,null);
    }
}
