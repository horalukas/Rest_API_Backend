package cz.cvut.fit.horaluk1.gradle.repository;

import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
