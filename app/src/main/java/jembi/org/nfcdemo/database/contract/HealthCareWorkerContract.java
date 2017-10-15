package jembi.org.nfcdemo.database.contract;

import android.provider.BaseColumns;

/**
 * Defines the HealthCareWorker table structure and SQL statements to create and drop the table
 */

public class HealthCareWorkerContract {

    public HealthCareWorkerContract() {
    }

    public static class HealthCareWorkerEntry implements BaseColumns {
        public static final String TABLE_NAME ="healthCareWorker";
        public static final String COLUMN_NAME_HCW_ID = "healthCareWorkerId";
        public static final String COLUMN_NAME_FIRST_NAME="firstName";
        public static final String COLUMN_NAME_SURNAME="surame";
        public static final String COLUMN_NAME_ROLE ="role";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + HealthCareWorkerEntry.TABLE_NAME;

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + HealthCareWorkerEntry.TABLE_NAME  + " (" +
                HealthCareWorkerEntry._ID + " INTEGER PRIMARY KEY, " +
                HealthCareWorkerEntry.COLUMN_NAME_HCW_ID + " NUMERIC, " +
                HealthCareWorkerEntry.COLUMN_NAME_FIRST_NAME + " TEXT, " +
                HealthCareWorkerEntry.COLUMN_NAME_SURNAME + " TEXT, " +
                HealthCareWorkerEntry.COLUMN_NAME_ROLE + " TEXT )";
    }
}

