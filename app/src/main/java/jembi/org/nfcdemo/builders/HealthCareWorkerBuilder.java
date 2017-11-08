package jembi.org.nfcdemo.builders;

import jembi.org.nfcdemo.model.HealthCareWorker;

/**
 * Creates a new HealthCareWorker
 */

public class HealthCareWorkerBuilder {

    private long id;
    private String firstName;
    private String surname;
    private long dateOfBirth;
    private String role;

    public HealthCareWorkerBuilder withId(long id) {
        this.id = id;
        return this;
    }


    public HealthCareWorkerBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public HealthCareWorkerBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public HealthCareWorkerBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public HealthCareWorker build() {
        return new HealthCareWorker(id, firstName, surname, role);
    }
}
