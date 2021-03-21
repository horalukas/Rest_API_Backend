package cz.cvut.fit.horaluk1.gradle.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String director;

    @NotNull
    private int minutes;

    @NotNull
    private String rating;

    @ManyToMany
    @JoinTable(
            name = "has_stars",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_star_id"))
    private List<MovieStar> stars;

    public Movie() {
    }

    public Movie(String name, String director, int minutes, String rating, List<MovieStar> stars) {
        this.name = name;
        this.director = director;
        this.minutes = minutes;
        this.rating = rating;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<MovieStar> getStars() { return stars; }

    public void setStars(List<MovieStar> stars) { this.stars = stars; }
}
