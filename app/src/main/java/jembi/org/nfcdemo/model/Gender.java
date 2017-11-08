package jembi.org.nfcdemo.model;

/**
 * Created by Jembi Health Systems on 2017/10/14.
 */

public enum Gender {

    FEMALE("Female"), MALE("Male"), UNKNOWN("Unknown");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    private String getGender(){
        return gender;
    }
}
