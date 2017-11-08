package jembi.org.nfcdemo.builders;

import android.location.GnssMeasurementsEvent;

import java.util.Date;
import java.util.List;

import jembi.org.nfcdemo.model.Gender;
import jembi.org.nfcdemo.model.HealthCareWorker;
import jembi.org.nfcdemo.model.Immunization;
import jembi.org.nfcdemo.model.Patient;

/**
 * Class that creates a new patient
 */

public class PatientBuilder {

    private long id;
    private String firstName;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;
    private HealthCareWorker healthCareWorker;
    private List<Immunization> immunizations;

    public PatientBuilder withId(long id){
        this.id = id;
        return this;
    }

    public PatientBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public PatientBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public PatientBuilder withDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PatientBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PatientBuilder withHealthCareWorker(HealthCareWorker healthCarWorker) {
        this.healthCareWorker = healthCarWorker;
        return this;
    }

    public PatientBuilder withImmunizations(List<Immunization> immunizations) {
        this.immunizations = immunizations;
        return this;
    }

    public Patient build() {
        return new Patient(id, firstName, surname, gender, dateOfBirth, healthCareWorker, immunizations);

    }
}
