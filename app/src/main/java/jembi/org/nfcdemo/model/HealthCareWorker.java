package jembi.org.nfcdemo.model;

import java.util.List;

/**
 * Created by Jembi Health Systems on 2017/10/12.
 */

public class HealthCareWorker extends Person {

    private String role;

    public HealthCareWorker(long id, String firstName, String surname, String role) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setSurname(surname);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
