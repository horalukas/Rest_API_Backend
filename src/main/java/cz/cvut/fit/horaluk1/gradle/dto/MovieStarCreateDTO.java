package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;

public class MovieStarCreateDTO {

    private String firstName;
    private String secondName;
    private List<Integer> movieIds;

    public MovieStarCreateDTO() {
    }

    public MovieStarCreateDTO(String firstName, String secondName, List<Integer> movieIds) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.movieIds = movieIds;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<Integer> getMovieIds() { return movieIds; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setMovieIds(List<Integer> movieIds) {
        this.movieIds = movieIds;
    }
}
