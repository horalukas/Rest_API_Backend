package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDTO)) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return id == movieDTO.id &&
                minutes == movieDTO.minutes &&
                name.equals(movieDTO.name) &&
                director.equals(movieDTO.director) &&
                rating.equals(movieDTO.rating) &&
                Objects.equals(starIds, movieDTO.starIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, director, minutes, rating, starIds);
    }
}
