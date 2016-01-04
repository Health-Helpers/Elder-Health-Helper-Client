package com.hh.ehh.database.tables;

/**
 * Created by mcpe on 21/12/2015.
 */
public class PatientTable {
    public static final String TABLE_NAME = "patients";

    public static class Cols {
        public static final String ID = "_id";
        public static final String ID_DOC = "_idDoc";
        public static final String NAME = "_name";
        public static final String SURNAME = "_surname";
        public static final String LANGUAGE = "_language";
        public static final String PHONE = "_phone";
        public static final String ADDRESS = "_address";
        public static final String DISEASE = "_disease";
        public static final String DEPENDENCY = "_dependency";


        public static String[] getAllCols() {
            return new String[]{ID,
                    ID_DOC,
                    NAME,
                    SURNAME,
                    LANGUAGE,
                    PHONE,
                    ADDRESS,
                    DISEASE,
                    DEPENDENCY
                    };
        }
    }
}
