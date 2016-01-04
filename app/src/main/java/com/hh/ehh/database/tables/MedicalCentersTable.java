package com.hh.ehh.database.tables;

/**
 * Created by mcpe on 21/12/2015.
 */
public class MedicalCentersTable {
    public static final String TABLE_NAME = "MedicalCenters";

    public static class Cols {
        public static final String ID = "_id";
        public static final String NAME = "_name";
        public static final String LATITUDE = "_surname";
        public static final String LONGITUDE = "_language";
        public static final String ADDRESS = "_address";
        public static final String PHONE = "_phone";


        public static String[] getAllCols() {
            return new String[]{ID,
                    NAME,
                    LATITUDE,
                    LONGITUDE,
                    ADDRESS,
                    PHONE
            };
        }
    }
}
