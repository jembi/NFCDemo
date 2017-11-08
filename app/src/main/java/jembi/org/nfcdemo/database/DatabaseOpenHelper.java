package jembi.org.nfcdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jembi.org.nfcdemo.builders.HealthCareWorkerBuilder;
import jembi.org.nfcdemo.builders.ImmunizationBuilder;
import jembi.org.nfcdemo.builders.PatientBuilder;
import jembi.org.nfcdemo.database.contract.HealthCareWorkerContract;
import jembi.org.nfcdemo.database.contract.ImmunizationContract;
import jembi.org.nfcdemo.database.contract.PatientContract;
import jembi.org.nfcdemo.database.contract.PatientImmunizationContract;
import jembi.org.nfcdemo.model.Gender;
import jembi.org.nfcdemo.model.HealthCareWorker;
import jembi.org.nfcdemo.model.Immunization;
import jembi.org.nfcdemo.model.Patient;

import static jembi.org.nfcdemo.database.contract.HealthCareWorkerContract.*;
import static jembi.org.nfcdemo.database.contract.ImmunizationContract.*;
import static jembi.org.nfcdemo.database.contract.PatientContract.*;
import static jembi.org.nfcdemo.database.contract.PatientImmunizationContract.*;

/**
 * Class that handles the database interactions. It is used to manage creating, upgrading,
 * downgrading of the database. Also opening of the database connection, and running specific operations
 * to insert and query data.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Immunization.db";
    private SQLiteDatabase database;
    private DatabaseResult databaseResult;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PatientEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(ImmunizationEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(HealthCareWorkerEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(PatientImmunizationEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PatientEntry.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(ImmunizationEntry.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(HealthCareWorkerEntry.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(PatientImmunizationEntry.SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertTestData(DatabaseResult databaseResult) {
        new InsertTestDataTask(databaseResult).execute();
    }


    /* Class that generates test data
    *
     */

    private class InsertTestDataTask extends AsyncTask<Void, Void, Boolean> {

        private DatabaseResult callback;

        public InsertTestDataTask(DatabaseResult callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDatabaseConnection();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return save();

        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            callback.processResult(success);
        }
    }

    private Boolean save() {
        try {
            database.insert(PatientEntry.TABLE_NAME, null, createTestPatientContentValues());
            database.insert(HealthCareWorkerEntry.TABLE_NAME, null, createTestHealthWorkerContentValues());
            database.insert(ImmunizationEntry.TABLE_NAME, null, createTestImmunizationContentValues());
            database.insert(PatientImmunizationEntry.TABLE_NAME, null, createTestPatientImmunizationContentValues());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private ContentValues createTestPatientContentValues() {
        Immunization immunization = buildImmunization();
        HealthCareWorker healthCareWorker = buildHealthCareWorker();
        Patient patient = buildPatient(Arrays.asList(immunization), healthCareWorker);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PatientEntry.COLUMN_NAME_PATIENT_ID, patient.getId());
        contentValues.put(PatientEntry.COLUMN_NAME_FIRST_NAME, patient.getFirstName());
        contentValues.put(PatientEntry.COLUMN_NAME_SURNAME, patient.getSurname());
        contentValues.put(PatientEntry.COLUMN_NAME_DATE_OF_BIRTH, patient.getDateOfBirth().getTime());
        contentValues.put(PatientEntry.COLUMN_NAME_GENDER, patient.getGender().name());
        return contentValues;
    }

    private ContentValues createTestHealthWorkerContentValues() {
        HealthCareWorker healthCareWorker = buildHealthCareWorker();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HealthCareWorkerEntry.COLUMN_NAME_HCW_ID, healthCareWorker.getId());
        contentValues.put(HealthCareWorkerEntry.COLUMN_NAME_FIRST_NAME, healthCareWorker.getFirstName());
        contentValues.put(HealthCareWorkerEntry.COLUMN_NAME_SURNAME, healthCareWorker.getSurname());
        contentValues.put(HealthCareWorkerEntry.COLUMN_NAME_ROLE, healthCareWorker.getRole());;
        return contentValues;

    }

    private ContentValues createTestImmunizationContentValues() {
        Immunization immunization = buildImmunization();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ImmunizationEntry.COLUMN_NAME_VACCINATION_CODE, immunization.getVaccinationCode());
        contentValues.put(ImmunizationEntry.COLUMN_NAME_VACCINATION_DOSE, immunization.getVaccinationDose());
        contentValues.put(ImmunizationEntry.COLUMN_NAME_VACCINATION_REASON, immunization.getVaccinationReason());
        return contentValues;
    }

    private ContentValues createTestPatientImmunizationContentValues() {
        Immunization immunization = buildImmunization();
        HealthCareWorker healthCareWorker = buildHealthCareWorker();
        Patient patient = buildPatient(Arrays.asList(immunization), healthCareWorker);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_DATE_OF_ADMINISTRATION, immunization.getAdministrationDate().getTime());
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_PLACE_OF_ADMINISTRATION, immunization.getAdministrationLocation());
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_VACCINATION_CODE, immunization.getVaccinationCode());
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_VACCINATION_CODE, immunization.getVaccinationCode());
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_HCW_ID, healthCareWorker.getId());
        contentValues.put(PatientImmunizationEntry.COLUMN_NAME_PATIENT_ID, patient.getId());
        return contentValues;

    }

    private Immunization buildImmunization() {
        return new ImmunizationBuilder()
                    .withAdministrationDate(new Date())
                    .withAdministrationLocation("Tokai")
                    .withVaccinationCode("TEST")
                    .withVaccinationDose("Dose")
                    .withVaccinationReason("Test")
                    .build();
    }

    private HealthCareWorker buildHealthCareWorker() {
        return new HealthCareWorkerBuilder()
                    .withFirstName("TestHCW")
                    .withSurname("Worker")
                    .withId(Long.MIN_VALUE)
                    .withRole("Nurse")
                    .build();
    }

    private Patient buildPatient(List<Immunization> immunizations, HealthCareWorker healthCareWorker) {
        return new PatientBuilder()
                .withDateOfBirth(new Date())
                .withFirstName("Test")
                .withSurname("Patient")
                .withGender(Gender.FEMALE)
                .withId(Long.MIN_VALUE)
                .withImmunizations(immunizations)
                .withHealthCareWorker(healthCareWorker)
                .build();
    }


    private void openDatabaseConnection() {

        if(database == null) {
            database = this.getWritableDatabase();
        }
    }
}
