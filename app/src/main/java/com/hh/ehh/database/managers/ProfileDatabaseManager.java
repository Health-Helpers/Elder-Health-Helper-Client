package com.hh.ehh.database.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hh.ehh.database.tables.ProfileTable;
import com.hh.ehh.model.Profile;

/**
 * Created by mcpe on 21/12/2015.
 */
public class ProfileDatabaseManager {

    private static ContentValues profileValues;

    public static void saveProfile(Profile profile, SQLiteDatabase sqLiteDatabase) {
        profileValues = new ContentValues();
        profileValues.put(ProfileTable.Cols.ID, profile.getId());
        profileValues.put(ProfileTable.Cols.NAME, profile.getName());
        profileValues.put(ProfileTable.Cols.SURNAME, profile.getSurname());
        profileValues.put(ProfileTable.Cols.EMAIL, profile.getEmail());
        profileValues.put(ProfileTable.Cols.PHONE, profile.getPhone());
        profileValues.put(ProfileTable.Cols.ADDRESS, profile.getLocation());
        profileValues.put(ProfileTable.Cols.IMAGE, profile.getImagePath());
        sqLiteDatabase.insert(ProfileTable.TABLE_NAME, null, profileValues);
    }


    public static void updateProfile(Profile profile, SQLiteDatabase sqLiteDatabase) {
        Profile dbPRofile = getProfile(sqLiteDatabase);
        if (dbPRofile != null) {
            if (!dbPRofile.getName().equals(profile.getName())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.NAME, profile.getName());
                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
            if (!dbPRofile.getSurname().equals(profile.getSurname())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.SURNAME, profile.getSurname());
                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
            if (!dbPRofile.getEmail().equals(profile.getEmail())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.EMAIL, profile.getEmail());

                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
            if (!dbPRofile.getLocation().equals(profile.getLocation())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.EMAIL, profile.getLocation());

                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
            if (!dbPRofile.getPhone().equals(profile.getPhone())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.PHONE, profile.getPhone());
                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
            if (!dbPRofile.getImagePath().equals(profile.getImagePath())) {
                profileValues = new ContentValues();
                profileValues.put(ProfileTable.Cols.IMAGE, profile.getImagePath());

                sqLiteDatabase.update(
                        ProfileTable.TABLE_NAME,
                        profileValues,
                        ProfileTable.Cols.ID + "=?",
                        new String[]{dbPRofile.getId()}
                );
            }
        } else {
            saveProfile(profile, sqLiteDatabase);
        }
    }


    public static Profile getProfile(SQLiteDatabase sqLiteDatabase) {
        Profile profile = null;
        Cursor cursor = sqLiteDatabase.query(ProfileTable.TABLE_NAME, ProfileTable.Cols.getAllCols(), null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            profile = new Profile(
                    String.valueOf(cursor.getInt(cursor.getColumnIndex(ProfileTable.Cols.ID))),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.SURNAME)),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.EMAIL)),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.IMAGE)),
                    cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.PHONE)));
            cursor.close();
        }
        return profile;
    }

    public static void deleteAllProfiles(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE FROM " + ProfileTable.TABLE_NAME);
    }
}