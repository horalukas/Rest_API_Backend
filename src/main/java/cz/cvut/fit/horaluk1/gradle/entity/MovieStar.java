package cz.cvut.fit.horaluk1.gradle.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class MovieStar {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "stars_in",
            joinColumns = @JoinColumn(name = "movie_star_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

    public MovieStar() {
    }

    public MovieStar(String firstName, String lastName, List<Movie> movies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies=movies;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public List<Movie> getMovies() { return movies; }

    public void setMovies(List<Movie> movies) { this.movies = movies; }
}
