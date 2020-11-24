package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;

public class MovieStarDTO {

    private final int id;
    private final String firstName;
    private final String secondName;
    private final List<Integer> movieIds;

    public MovieStarDTO(int id, String firstName, String secondName, List<Integer> movieIds) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.movieIds = movieIds;
    }

    public int getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<Integer> getMovieIds() { return movieIds; }
}
