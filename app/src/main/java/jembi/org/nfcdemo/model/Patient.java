package jembi.org.nfcdemo.model;

import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jembi.org.nfcdemo.NFCDemoApplication;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class Patient extends Person {

    public static final String LANGUAGE_CODE = "en";
    public static final String ENCODING = "UTF-8";
    public static final char DELIMITER = '|';

    private Date dateOfBirth;
    private Gender gender;
    private HealthCareWorker healthCareWorker;
    private List<Immunization> immunizations;

    public Patient() {
    }

    public Patient(long id, String firstName, String surname, Gender gender, Date dateOfBirth, HealthCareWorker healthCareWorker, List<Immunization> immunizations) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setSurname(surname);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.healthCareWorker = healthCareWorker;
        this.immunizations = immunizations;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Immunization> getImmunizations() {
        return immunizations;
    }

    public void setImmunizations(List<Immunization> immunizations) {
        this.immunizations = immunizations;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public HealthCareWorker getHealthCareWorker() {
        return healthCareWorker;
    }

    public void setHealthCareWorker(HealthCareWorker healthCareWorker) {
        this.healthCareWorker = healthCareWorker;
    }

    public static Byte[] writeObject(Patient patient) throws RuntimeException {
        // convert data to bytes
        Byte[] bytes = null;
        try {
            bytes = convertPatientToBytes(patient);
        } catch (Exception e) {
            Log.e(NFCDemoApplication.LOG_TAG, "Could not convert Patient data to bytes", e);
            Log.d(NFCDemoApplication.LOG_TAG, "Patient data: " + patient.toString());
            new RuntimeException("Could not convert Patient data to bytes", e); // FIXME: use better exception
        }

        Log.i(NFCDemoApplication.LOG_TAG, patient.toString());
        Log.i(NFCDemoApplication.LOG_TAG, "Patient data is " + bytes.length + " bytes");

        return bytes;
    }

    public static Patient readObject(Byte[] bytes) throws RuntimeException {
        Patient patient = null;
        try {
            patient = convertBytesToPatient(bytes);
        } catch (Exception e) {
            Log.e(NFCDemoApplication.LOG_TAG, "Could not convert bytes to Patient", e);
            new RuntimeException("Could not create Patient from bytes", e); // FIXME: use better exception
        }
        return patient;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "dateOfBirth=" + new SimpleDateFormat("dd-MMM-yyyy").format(dateOfBirth) +
                ", gender=" + gender +
                ", healthCareWorker=" + healthCareWorker +
                ", " + immunizations +
                "}";
    }

    private static Byte[] convertPatientToBytes(Patient patient) throws Exception {
        List<Byte> bytes = new ArrayList<>();

        // first name
        addToBytes(bytes, patient.getFirstName().getBytes(ENCODING));
        bytes.add((Byte)(byte)DELIMITER);

        // last name
        addToBytes(bytes, patient.getSurname().getBytes(ENCODING));
        bytes.add((Byte)(byte)DELIMITER);

        // gender
        if (patient.getGender() == Gender.FEMALE) {
            addToBytes(bytes,"F".getBytes());
        } else {
            addToBytes(bytes,"M".getBytes());
        }

        // date of birth (yyyyMMdd)
        addToBytes(bytes, convertDateToBytes(patient.getDateOfBirth()));
        bytes.add((Byte)(byte)DELIMITER);

        Log.i(NFCDemoApplication.LOG_TAG, "bytes so far are: " + bytes.size());

        // immunization
        for (Immunization i : patient.getImmunizations()) {
            addToBytes(bytes, convertDateToBytes(i.getAdministrationDate())); // date (yyyyMMdd)
            bytes.add((Byte)(byte)DELIMITER);
            addToBytes(bytes, i.getAdministrationLocation().getBytes(ENCODING)); // clinic code
            bytes.add((Byte)(byte)DELIMITER);
            addToBytes(bytes, i.getVaccinationCode().getBytes(ENCODING)); // vaccination code
            bytes.add((Byte)(byte)DELIMITER);
            addToBytes(bytes, i.getVaccinationDose().getBytes(ENCODING)); // dose
            bytes.add((Byte)(byte)DELIMITER);
            addToBytes(bytes, i.getVaccinationReason().getBytes(ENCODING)); // reason
            bytes.add((Byte)(byte)DELIMITER);
            Log.i(NFCDemoApplication.LOG_TAG, "bytes after adding 1 vaccination: " + bytes.size());
        }

        return (Byte[])bytes.toArray(new Byte[bytes.size()]);
    }

    private static Patient convertBytesToPatient(Byte[] bytes) throws ParseException {
        Patient patient = new Patient();

        int fromPosition = 0;

        // first name
        int toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
        patient.setFirstName(getStringFromBytes(bytes, fromPosition, toPosition));
        Log.i(NFCDemoApplication.LOG_TAG, "firstName = " + patient.getFirstName());

        // last name
        fromPosition = toPosition + 1;
        toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
        patient.setSurname(getStringFromBytes(bytes, fromPosition, toPosition));
        Log.i(NFCDemoApplication.LOG_TAG, "surname = " + patient.getSurname());

        // gender
        fromPosition = toPosition + 1;
        toPosition = fromPosition;
        char gender = (char)(byte)bytes[fromPosition];
        Log.i(NFCDemoApplication.LOG_TAG, "gender = " + gender);
        if (gender == 'F') {
            patient.setGender(Gender.FEMALE);
        } else {
            patient.setGender(Gender.MALE);
        }

        // birth date
        fromPosition = toPosition + 1; // no delimeter but gender is 1 char
        toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
        patient.setDateOfBirth(getDateFromBytes(bytes, fromPosition, toPosition));
        Log.i(NFCDemoApplication.LOG_TAG, "date of birth = " + patient.getDateOfBirth());

        List<Immunization> immunizations = new ArrayList<>();
        while (toPosition+1 < bytes.length) {
            Immunization immunization = new Immunization();
            // immunization date:
            fromPosition = toPosition + 1;
            toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
            immunization.setAdministrationDate(getDateFromBytes(bytes, fromPosition, toPosition));
            Log.i(NFCDemoApplication.LOG_TAG, "Immunization administrationDate = " + immunization.getAdministrationDate());
            // clinic code
            fromPosition = toPosition + 1;
            toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
            immunization.setAdministrationLocation(getStringFromBytes(bytes, fromPosition, toPosition));
            Log.i(NFCDemoApplication.LOG_TAG, "Immunization administrationLocation = " + immunization.getAdministrationLocation());
            // vaccination code
            fromPosition = toPosition + 1;
            toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
            immunization.setVaccinationCode(getStringFromBytes(bytes, fromPosition, toPosition));
            Log.i(NFCDemoApplication.LOG_TAG, "Immunization vaccinationCode = " + immunization.getVaccinationCode());
            // dose
            fromPosition = toPosition + 1;
            toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
            immunization.setVaccinationDose(getStringFromBytes(bytes, fromPosition, toPosition));
            Log.i(NFCDemoApplication.LOG_TAG, "Immunization vaccinationDose = " + immunization.getVaccinationDose());
            // reason
            fromPosition = toPosition + 1;
            toPosition = getPositionOfNextDelimeter(bytes, fromPosition);
            immunization.setVaccinationReason(getStringFromBytes(bytes, fromPosition, toPosition));
            Log.i(NFCDemoApplication.LOG_TAG, "Immunization vaccinationReason = " + immunization.getVaccinationReason());

            immunizations.add(immunization);
        }
        patient.setImmunizations(immunizations);

        return patient;
    }

    private static List<Byte> addToBytes(List<Byte> bytes, byte[] data) {
        for (byte b : data) {
            bytes.add(b);
        }
        return bytes;
    }

    private static byte[] convertDateToBytes(Date date) {
        String dateString = new SimpleDateFormat("yyyyMMdd").format(date);
        Log.i(NFCDemoApplication.LOG_TAG, "dateString = " +  dateString);
        return new BigInteger(dateString).toByteArray();
    }

    private static int getPositionOfNextDelimeter(Byte[] bytes, int fromIndex) {
        int position = fromIndex;
        for (; position < bytes.length; position++) {
            byte b = bytes[position];
            if ((char)b == DELIMITER) {
                break; // got to the delimiter
            }
        }
        return position;
    }

    private static String getStringFromBytes(Byte[] bytes, int fromIndex, int toIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = fromIndex; i < toIndex; i++) {
            byte b = bytes[i];
            stringBuilder.append((char)b);
        }
        return stringBuilder.toString();
    }

    // ParseException is thrown if the number is not a date
    // NumberFormatException is thrown if the bytes can't be converted to a number (e.g. if the length is null)
    // FIXME: figure out how to deal with these exceptions ...
    private static Date getDateFromBytes(Byte[] bytes, int fromIndex, int toIndex) throws ParseException, NumberFormatException {
        Log.i(NFCDemoApplication.LOG_TAG, "Getting date from bytes. fromIndex="+fromIndex+" toIndex="+toIndex);
        byte[] dateBytes = new byte[toIndex - fromIndex];
        for (int i=0, j=fromIndex; j<toIndex; i++, j++) {
            dateBytes[i] = bytes[j];
        }
        BigInteger dateDigits = new BigInteger(dateBytes);
        return new SimpleDateFormat("yyyyMMdd").parse(dateDigits.toString());
    }
}
