package jembi.org.nfcdemo.database.contract;

import android.provider.BaseColumns;

/**
 * Defines the PatientImmunization  bridging table structure and SQL statements to create and drop the table
 */

public class PatientImmunizationContract {

    public PatientImmunizationContract() {
    }

    public static class PatientImmunizationEntry implements BaseColumns {
        public static final String TABLE_NAME ="patientImmunization";
        public static final String COLUMN_NAME_PATIENT_ID = "patientId";
        public static final String COLUMN_NAME_HCW_ID = "healthCareWorkerId";
        public static final String COLUMN_NAME_VACCINATION_CODE = "vaccinationId";
        public static final String COLUMN_NAME_DATE_OF_ADMINISTRATION = "administrationDate";
        public static final String COLUMN_NAME_PLACE_OF_ADMINISTRATION ="placeOfAdministration";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + PatientImmunizationEntry.TABLE_NAME;

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + PatientImmunizationEntry.TABLE_NAME  + " (" +
                PatientImmunizationEntry._ID + " INTEGER PRIMARY KEY, " +
                PatientImmunizationEntry.COLUMN_NAME_PATIENT_ID + " NUMERIC, " +
                PatientImmunizationEntry.COLUMN_NAME_HCW_ID + " NUMERIC, " +
                PatientImmunizationEntry.COLUMN_NAME_VACCINATION_CODE + " NUMERIC, " +
                PatientImmunizationEntry.COLUMN_NAME_DATE_OF_ADMINISTRATION + " NUMERIC, " +
                PatientImmunizationEntry.COLUMN_NAME_PLACE_OF_ADMINISTRATION + " TEXT ) " ;
    }
}
