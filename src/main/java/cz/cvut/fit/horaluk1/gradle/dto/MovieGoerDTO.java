package cz.cvut.fit.horaluk1.gradle.dto;

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
}
