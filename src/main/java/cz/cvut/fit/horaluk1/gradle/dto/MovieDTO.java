package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;

public class MovieDTO {

    private final int id;
    private final String name;
    private final String director;
    private final int minutes;
    private final String rating;
    private final List<Integer> starIds;

    public MovieDTO(int id, String name, String director, int minutes, String rating, List<Integer> starIds) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.minutes = minutes;
        this.rating = rating;
        this.starIds = starIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getRating() {
        return rating;
    }

    public List<Integer> getStarIds() { return starIds; }
}
