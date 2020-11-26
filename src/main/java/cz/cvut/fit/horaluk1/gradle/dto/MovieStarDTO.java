package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;
import java.util.Objects;

public class MovieStarDTO {

    private final int id;
    private final String firstName;
    private final String lastName;

    public MovieStarDTO(int id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
    }

    public int getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieStarDTO)) return false;
        MovieStarDTO that = (MovieStarDTO) o;
        return id == that.id &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
