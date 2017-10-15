package jembi.org.nfcdemo.model;

import java.util.List;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class Patient extends Person {

    private long dateOfBirth;
    private Gender gender;
    private HealthCareWorker healthCareWorker;
    private List<Immunization> immunizations;

    public Patient(long id, String firstName, String surname, Gender gender, long dateOfBirth, HealthCareWorker healthCareWorker, List<Immunization> immunizations) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setSurname(surname);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.healthCareWorker = healthCareWorker;
        this.immunizations = immunizations;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
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
}
