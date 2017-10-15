package jembi.org.nfcdemo.builders;

import jembi.org.nfcdemo.model.Immunization;

/**
 * Class that creates new immunizations
 */

public class ImmunizationBuilder {

    private long administrationDate;
    private String administrationLocation;
    private String vaccinationCode;
    private String vaccinationDose;
    private String vaccinationReason;

    public ImmunizationBuilder withAdministrationDate(long administrationDate) {
        this.administrationDate = administrationDate;
        return this;
    }

    public ImmunizationBuilder withAdministrationLocation(String administrationLocation) {
        this.administrationLocation = administrationLocation;
        return this;
    }

    public ImmunizationBuilder withVaccinationCode(String vaccinationCode) {
        this.vaccinationCode = vaccinationCode;
        return this;
    }

    public ImmunizationBuilder withVaccinationDose(String vaccinationDose) {
        this.vaccinationDose = vaccinationDose;
        return this;
    }

    public ImmunizationBuilder withVaccinationReason(String vaccinationReason) {
        this.vaccinationReason = vaccinationReason;
        return this;
    }

    public Immunization build() {
        return new Immunization(administrationDate, administrationLocation, vaccinationCode, vaccinationDose, vaccinationReason);
    }
}
