package cz.cvut.fit.horaluk1.gradle.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class MovieStar {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    public MovieStar() {
    }

    public MovieStar(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

}
