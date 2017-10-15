package jembi.org.nfcdemo.database.contract;

import android.provider.BaseColumns;

import java.io.Serializable;

import jembi.org.nfcdemo.model.Patient;

/**
 * Defines the Patient table structure and SQL statements to create and drop the table
 */

public class PatientContract implements Serializable {

    public PatientContract() {
    }

    public static class PatientEntry implements BaseColumns {
        public static final String TABLE_NAME ="patient";
        public static final String COLUMN_NAME_PATIENT_ID = "patientId";
        public static final String COLUMN_NAME_FIRST_NAME = "firstName";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_DATE_OF_BIRTH ="dateOfBirth";
        public static final String COLUMN_NAME_GENDER="gender";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + PatientEntry.TABLE_NAME;

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + PatientEntry.TABLE_NAME  + " (" +
                PatientEntry._ID + " INTEGER PRIMARY KEY, " +
                PatientEntry.COLUMN_NAME_PATIENT_ID + " NUMERIC " +
                PatientEntry.COLUMN_NAME_FIRST_NAME + " TEXT, " +
                PatientEntry.COLUMN_NAME_SURNAME + " TEXT, " +
                PatientEntry.COLUMN_NAME_GENDER + " TEXT, " +
                PatientEntry.COLUMN_NAME_DATE_OF_BIRTH + " NUMERIC ) ";
    }
}
