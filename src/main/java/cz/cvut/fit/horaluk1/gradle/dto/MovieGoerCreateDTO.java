package cz.cvut.fit.horaluk1.gradle.dto;

public class MovieGoerCreateDTO {

    private String email;
    private String password;

    public MovieGoerCreateDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MovieGoerCreateDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
