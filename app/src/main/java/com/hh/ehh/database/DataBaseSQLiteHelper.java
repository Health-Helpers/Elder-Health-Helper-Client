package com.hh.ehh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hh.ehh.database.tables.MedicalCentersTable;
import com.hh.ehh.database.tables.PatientTable;
import com.hh.ehh.database.tables.ProfileTable;

import java.text.MessageFormat;


public class DataBaseSQLiteHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "ehh.db";
    private static final int CURRENT_DB_VERSION = 1;
    private static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    private static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";
    private static DataBaseSQLiteHelper instance;

    private DataBaseSQLiteHelper(Context context) {
//        super(context, Environment.getExternalStorageDirectory() + "/" + DB_NAME, null, CURRENT_DB_VERSION);
        super(context, DB_NAME, null, CURRENT_DB_VERSION);

    }

    public synchronized static DataBaseSQLiteHelper newInstance(Context context) {
        if (instance == null) {
            return new DataBaseSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProfileTable(db);
        createPatientsTable(db);
        createMedicalCentersDatabase(db);

    }

    private void createPatientsTable(SQLiteDatabase db) {
        StringBuilder patientsTables = new StringBuilder();
        patientsTables
                .append(PatientTable.Cols.ID).append(" INTEGER NOT NULL ON CONFLICT IGNORE PRIMARY KEY AUTOINCREMENT, ")
                .append(PatientTable.Cols.ID_DOC).append(" TEXT NOT NULL ON CONFLICT IGNORE, ")
                .append(PatientTable.Cols.NAME).append(" TEXT, ")
                .append(PatientTable.Cols.SURNAME).append(" TEXT, ")
                .append(PatientTable.Cols.LANGUAGE).append(" TEXT, ")
                .append(PatientTable.Cols.PHONE).append(" TEXT, ")
                .append(PatientTable.Cols.ADDRESS).append(" TEXT, ")
                .append(PatientTable.Cols.DISEASE).append(" TEXT, ")
                .append(PatientTable.Cols.DEPENDENCY).append(" TEXT ");
        String index = "CREATE UNIQUE INDEX [index" + PatientTable.TABLE_NAME + "] ON [" + PatientTable.TABLE_NAME + "] ([" + PatientTable.Cols.ID + "]);";
        createTable(db, PatientTable.TABLE_NAME, patientsTables.toString(), index);
    }

    private void createProfileTable(SQLiteDatabase db) {
        StringBuilder profileTables = new StringBuilder();
        profileTables
                .append(ProfileTable.Cols.ID).append(" INTEGER NOT NULL ON CONFLICT IGNORE PRIMARY KEY AUTOINCREMENT, ")
                .append(ProfileTable.Cols.NAME).append(" TEXT NOT NULL ON CONFLICT IGNORE, ")
                .append(ProfileTable.Cols.SURNAME).append(" TEXT, ")
                .append(ProfileTable.Cols.EMAIL).append(" TEXT, ")
                .append(ProfileTable.Cols.PHONE).append(" TEXT, ")
                .append(ProfileTable.Cols.ADDRESS).append(" TEXT, ")
                .append(ProfileTable.Cols.IMAGE).append(" TEXT ");
        String index = "CREATE UNIQUE INDEX [index" + ProfileTable.TABLE_NAME + "] ON [" + ProfileTable.TABLE_NAME + "] ([" + ProfileTable.Cols.ID + "]);";
        createTable(db, ProfileTable.TABLE_NAME, profileTables.toString(), index);
    }

    private void createMedicalCentersDatabase(SQLiteDatabase db) {
        StringBuilder medicalCentersTables = new StringBuilder();
        medicalCentersTables
                .append(MedicalCentersTable.Cols.ID).append(" INTEGER NOT NULL ON CONFLICT IGNORE PRIMARY KEY AUTOINCREMENT, ")
                .append(MedicalCentersTable.Cols.NAME).append(" TEXT NOT NULL ON CONFLICT IGNORE, ")
                .append(MedicalCentersTable.Cols.LATITUDE).append(" TEXT, ")
                .append(MedicalCentersTable.Cols.LONGITUDE).append(" TEXT, ")
                .append(MedicalCentersTable.Cols.ADDRESS).append(" TEXT, ")
                .append(MedicalCentersTable.Cols.PHONE).append(" TEXT ");
        String index = "CREATE UNIQUE INDEX [index" + MedicalCentersTable.TABLE_NAME + "] ON [" + MedicalCentersTable.TABLE_NAME + "] ([" + MedicalCentersTable.Cols.ID + "]);";
        createTable(db, MedicalCentersTable.TABLE_NAME, medicalCentersTables.toString(), index);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, ProfileTable.TABLE_NAME);
        dropTable(db, MedicalCentersTable.TABLE_NAME);
        dropTable(db, PatientTable.TABLE_NAME);
        onCreate(db);
    }

    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat.format(DataBaseSQLiteHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }


    public void createTable(SQLiteDatabase db, String name, String fields, String index) {
        String query = MessageFormat.format(DataBaseSQLiteHelper.KEY_CREATE_TABLE, name, fields);
        System.out.println(query + ";\n" + index);
        db.execSQL(query + ";\n" + index);
    }
}