package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.MovieCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
import cz.cvut.fit.horaluk1.gradle.repository.MovieRepository;
import cz.cvut.fit.horaluk1.gradle.repository.MovieStarRepository;
import cz.cvut.fit.horaluk1.gradle.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
public class MovieServiceTest {
    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepositoryMock;

    @MockBean
    private MovieStarRepository movieStarRepository;

    @Test
    void findAll(){
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        MovieDTO movieDTO1 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        MovieDTO movieDTO2 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movieDTOList.add(movieDTO1);
        movieDTOList.add(movieDTO2);
        Movie movie1 = new Movie("bla", "123", 100, "PG-13", stars1);
        Movie movie2 = new Movie("bla", "123", 100, "PG-13", stars1);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        BDDMockito.given(movieRepositoryMock.findAll()).willReturn(movieList);
        assertArrayEquals(movieDTOList.toArray(), movieService.findAll().toArray());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findAll();
    }


    @Test
    void findById(){
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movie = new Movie("bla", "123", 100, "PG-13", stars1);
        BDDMockito.given(movieRepositoryMock.findById(movie.getId())).willReturn(Optional.of(movie));
        assertEquals(movie, movieService.findById(movie.getId()).get());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findById(movie.getId());
    }

    @Test
    void findByIdAsDTO(){
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movie = new Movie("bla", "123", 100, "PG-13", stars1);
        MovieDTO movieDTO = new MovieDTO(0, "bla", "123", 100, "PG-13", stars);
        BDDMockito.given(movieRepositoryMock.findById(movie.getId())).willReturn(Optional.of(movie));
        MovieDTO returns = movieService.findByIdAsDTO(movie.getId()).get();
        assertEquals(movieDTO, movieService.findByIdAsDTO(movie.getId()).get());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findById(movie.getId());
    }

    @Test
    void findByName(){
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movie = new Movie("bla", "123", 100, "PG-13", stars1);
        MovieDTO movieDTO = new MovieDTO(0, "bla", "123", 100, "PG-13", stars);
        BDDMockito.given(movieRepositoryMock.findByName(movie.getName())).willReturn(Optional.of(movie));
        MovieDTO returns = movieService.findByName(movie.getName()).get();
        assertEquals(movieDTO, movieService.findByName(movie.getName()).get());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findByName(movie.getName());
    }

    @Test
    void findAllByDirector(){
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        MovieDTO movieDTO1 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        MovieDTO movieDTO2 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movieDTOList.add(movieDTO1);
        movieDTOList.add(movieDTO2);
        Movie movie1 = new Movie("bla", "123", 100, "PG-13", stars1);
        Movie movie2 = new Movie("bla", "123", 100, "PG-13", stars1);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        BDDMockito.given(movieRepositoryMock.findAllByDirector(movie1.getDirector())).willReturn(movieList);
        assertArrayEquals(movieDTOList.toArray(), movieService.findAllByDirector(movie1.getDirector()).toArray());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findAllByDirector(movie1.getDirector());
    }

    @Test
    void findAllByRating(){
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        MovieDTO movieDTO1 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        MovieDTO movieDTO2 = new MovieDTO(0,"bla", "123", 100, "PG-13", stars);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movieDTOList.add(movieDTO1);
        movieDTOList.add(movieDTO2);
        Movie movie1 = new Movie("bla", "123", 100, "PG-13", stars1);
        Movie movie2 = new Movie("bla", "123", 100, "PG-13", stars1);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        BDDMockito.given(movieRepositoryMock.findAllByRating(movie1.getRating())).willReturn(movieList);
        assertArrayEquals(movieDTOList.toArray(), movieService.findAllByRating(movie1.getRating()).toArray());
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).findAllByRating(movie1.getRating());
    }

    @Test
    void create() throws Exception {
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movie = new Movie("bla", "123", 100, "PG-13", stars1);
        ReflectionTestUtils.setField(movie, "id", 1);
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO("bla", "123", 100, "PG-13", stars);
        BDDMockito.given(movieRepositoryMock.save(any(Movie.class))).willReturn(movie);
        MovieDTO returnedDTO = movieService.create(movieCreateDTO);
        MovieDTO expectedDTO = new MovieDTO(1, "bla", "123", 100, "PG-13", stars);
        assertEquals(expectedDTO, returnedDTO);
        ArgumentCaptor<Movie> argumentCaptor = ArgumentCaptor.forClass(Movie.class);
        Mockito.verify(movieRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Movie provided = argumentCaptor.getValue();
        assertEquals("bla", provided.getName());
        assertEquals("123", provided.getDirector());
        assertEquals(100, provided.getMinutes());
        assertEquals("PG-13", provided.getRating());
        assertEquals(stars1, provided.getStars());
    }

    @Test
    void update() throws Exception {
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movie = new Movie("bla", "123", 100, "PG-13", stars1);
        ReflectionTestUtils.setField(movie, "id", 1);
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO("bla", "123", 100, "PG-13", stars);
        BDDMockito.given(movieRepositoryMock.save(any(Movie.class))).willReturn(movie);
        BDDMockito.given(movieRepositoryMock.findById(movie.getId())).willReturn(Optional.of(movie));
        movieService.create(movieCreateDTO);
        MovieDTO expectedDTO = new MovieDTO(1, "bla", "123", 100, "PG-13", stars);
        MovieCreateDTO movieCreateDTO1 = new MovieCreateDTO("bla", "123", 100, "PG-13", stars);
        MovieDTO returnedDTO = movieService.update(1, movieCreateDTO1);
        assertEquals(expectedDTO, returnedDTO);
    }
}
