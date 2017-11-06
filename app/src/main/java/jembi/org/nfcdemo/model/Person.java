package jembi.org.nfcdemo.model;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class Person {

    private long id;
    private String firstName;
    private String surname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
