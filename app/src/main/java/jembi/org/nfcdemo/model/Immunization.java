package jembi.org.nfcdemo.model;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class Immunization {

    private long administrationDate;
    private String administrationLocation;
    private String vaccinationCode;
    private String vaccinationDose;
    private String vaccinationReason;

    public Immunization(long administrationDate, String administrationLocation, String vaccinationCode, String vaccinationDose, String vaccinationReason) {
        this.administrationDate = administrationDate;
        this.administrationLocation = administrationLocation;
        this.vaccinationCode = vaccinationCode;
        this.vaccinationDose = vaccinationDose;
        this.vaccinationReason = vaccinationReason;
    }


    public long getAdministrationDate() {
        return administrationDate;
    }

    public void setAdministrationDate(long administrationDate) {
        this.administrationDate = administrationDate;
    }

    public String getAdministrationLocation() {
        return administrationLocation;
    }

    public void setAdministrationLocation(String administrationLocation) {
        this.administrationLocation = administrationLocation;
    }

    public String getVaccinationCode() {
        return vaccinationCode;
    }

    public void setVaccinationCode(String vaccinationCode) {
        this.vaccinationCode = vaccinationCode;
    }

    public String getVaccinationDose() {
        return vaccinationDose;
    }

    public void setVaccinationDose(String vaccinationDose) {
        this.vaccinationDose = vaccinationDose;
    }

    public String getVaccinationReason() {
        return vaccinationReason;
    }

    public void setVaccinationReason(String vaccinationReason) {
        this.vaccinationReason = vaccinationReason;
    }
}
