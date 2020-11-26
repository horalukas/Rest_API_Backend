package cz.cvut.fit.horaluk1.gradle.dto;

public class MovieStarCreateDTO {

    private String firstName;
    private String lastName;

    public MovieStarCreateDTO() {
    }

    public MovieStarCreateDTO(String firstName, String secondName) {
        this.firstName = firstName;
        this.lastName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

}
