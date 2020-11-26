package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.Objects;

public class MovieGoerDTO {

    private final int id;
    private final String email;
    private final String password;

    public MovieGoerDTO(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieGoerDTO)) return false;
        MovieGoerDTO that = (MovieGoerDTO) o;
        return id == that.id &&
                email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
