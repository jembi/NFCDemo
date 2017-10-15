package jembi.org.nfcdemo.database.contract;

import android.provider.BaseColumns;

/**
 * Defines the Immunization table structure and SQL statements to create and drop the table
 */

public class ImmunizationContract {

    public ImmunizationContract() {
    }

    public static class ImmunizationEntry implements BaseColumns {
        public static final String TABLE_NAME ="immunization";
        public static final String COLUMN_NAME_VACCINATION_CODE ="vaccinationCode";
        public static final String COLUMN_NAME_VACCINATION_DOSE = "vaccinationDose";
        public static final String COLUMN_NAME_VACCINATION_REASON = "vaccinationReason";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + ImmunizationEntry.TABLE_NAME;

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ImmunizationEntry.TABLE_NAME  + " (" +
                ImmunizationEntry._ID + " INTEGER PRIMARY KEY, " +
                ImmunizationEntry.COLUMN_NAME_VACCINATION_CODE + " NUMERIC, " +
                ImmunizationEntry.COLUMN_NAME_VACCINATION_REASON + " TEXT, " +
                ImmunizationEntry.COLUMN_NAME_VACCINATION_DOSE + " NUMERIC  )  " ;
    }
}
