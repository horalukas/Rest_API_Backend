package cz.cvut.fit.horaluk1.gradle.repository;

import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieStarRepository  extends JpaRepository<MovieStar, Integer> {

    Optional<MovieStar> findAllByFirstNameAndLastName(String firstName, String lastName);

}
