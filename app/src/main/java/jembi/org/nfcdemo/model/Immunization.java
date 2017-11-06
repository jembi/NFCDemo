package jembi.org.nfcdemo.model;

import java.util.Date;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class Immunization {

    private Date administrationDate;
    private String administrationLocation;
    private String vaccinationCode;
    private String vaccinationDose;
    private String vaccinationReason;

    public Immunization() {
    }

    public Immunization(Date administrationDate, String administrationLocation, String vaccinationCode, String vaccinationDose, String vaccinationReason) {
        this.administrationDate = administrationDate;
        this.administrationLocation = administrationLocation;
        this.vaccinationCode = vaccinationCode;
        this.vaccinationDose = vaccinationDose;
        this.vaccinationReason = vaccinationReason;
    }


    public Date getAdministrationDate() {
        return administrationDate;
    }

    public void setAdministrationDate(Date administrationDate) {
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

    @Override
    public String toString() {
        return "Immunization{" +
                "administrationDate=" + administrationDate +
                ", administrationLocation='" + administrationLocation + '\'' +
                ", vaccinationCode='" + vaccinationCode + '\'' +
                ", vaccinationDose='" + vaccinationDose + '\'' +
                ", vaccinationReason='" + vaccinationReason + '\'' +
                '}';
    }
}
