package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;

public class MovieCreateDTO {

    private String name;
    private String director;
    private int minutes;
    private String rating;
    private List<Integer> starIds;

    public MovieCreateDTO(String name, String director, int minutes, String rating, List<Integer> starIds) {
        this.name = name;
        this.director = director;
        this.minutes = minutes;
        this.rating = rating;
        this.starIds = starIds;
    }

    public MovieCreateDTO() {
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
