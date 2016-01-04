package com.hh.ehh.database.tables;

/**
 * Created by mcpe on 21/12/2015.
 */
public class ProfileTable {
    public static final String TABLE_NAME = "profile";

    public static class Cols {
        public static final String ID = "_id";
        public static final String NAME = "_name";
        public static final String SURNAME = "_surname";
        public static final String EMAIL = "_email";
        public static final String PHONE = "_phone";
        public static final String ADDRESS = "_address";
        public static final String IMAGE = "_imagePath";

        public static String[] getAllCols() {
            return new String[]{ID,
                    NAME,
                    SURNAME,
                    EMAIL,
                    PHONE,
                    ADDRESS,
                    IMAGE};
        }
    }
}
