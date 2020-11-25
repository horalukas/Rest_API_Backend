package cz.cvut.fit.horaluk1.gradle.repository;

import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGoerRepository  extends JpaRepository<MovieGoer, Integer> {

    Optional<MovieGoer> findByEmail(String email);

}
