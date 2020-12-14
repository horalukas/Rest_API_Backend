package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.MovieController;
import cz.cvut.fit.horaluk1.gradle.dto.MovieCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
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
class MovieControllerTest {
    @Autowired
    private MovieController movieController;

    @MockBean
    private MovieService movieService;

    @Test
    void all() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movie1dto = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        MovieDTO movie2dto = new MovieDTO(2, "bla", "bla", 100, "bla", stars);
        MovieDTO movie3dto = new MovieDTO(3, "bla", "bla", 100, "bla", stars);
        List<MovieDTO> moviesdto = new ArrayList<>();
        moviesdto.add(movie1dto);
        moviesdto.add(movie2dto);
        moviesdto.add(movie3dto);
        BDDMockito.given(movieService.findAll()).willReturn(moviesdto);
        assertArrayEquals(moviesdto.toArray(), movieController.all().toArray());
        Mockito.verify(movieService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movieDTO = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        BDDMockito.given(movieService.findByIdAsDTO(movieDTO.getId())).willReturn(Optional.of(movieDTO));
        assertEquals(movieDTO, movieController.byId(movieDTO.getId()));
        Mockito.verify(movieService, Mockito.atLeastOnce()).findByIdAsDTO(movieDTO.getId());
    }

    @Test
    void byName() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movieDTO = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        BDDMockito.given(movieService.findByName(movieDTO.getName())).willReturn(Optional.of(movieDTO));
        assertEquals(movieDTO, movieController.byName(movieDTO.getName()));
        Mockito.verify(movieService, Mockito.atLeastOnce()).findByName(movieDTO.getName());
    }

    @Test
    void allByDirector() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movie1dto = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        MovieDTO movie2dto = new MovieDTO(2, "bla", "bla", 100, "bla", stars);
        MovieDTO movie3dto = new MovieDTO(3, "bla", "bla", 100, "bla", stars);
        List<MovieDTO> moviesdto = new ArrayList<>();
        moviesdto.add(movie1dto);
        moviesdto.add(movie2dto);
        moviesdto.add(movie3dto);
        BDDMockito.given(movieService.findAllByDirector(movie1dto.getDirector())).willReturn(moviesdto);
        assertArrayEquals(moviesdto.toArray(), movieController.allByDirector(movie1dto.getDirector()).toArray());
        Mockito.verify(movieService, Mockito.atLeastOnce()).findAllByDirector(movie1dto.getDirector());
    }

    @Test
    void allByRating() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movie1dto = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        MovieDTO movie2dto = new MovieDTO(2, "bla", "bla", 100, "bla", stars);
        MovieDTO movie3dto = new MovieDTO(3, "bla", "bla", 100, "bla", stars);
        List<MovieDTO> moviesdto = new ArrayList<>();
        moviesdto.add(movie1dto);
        moviesdto.add(movie2dto);
        moviesdto.add(movie3dto);
        BDDMockito.given(movieService.findAllByRating(movie1dto.getRating())).willReturn(moviesdto);
        assertArrayEquals(moviesdto.toArray(), movieController.allByRating(movie1dto.getRating()).toArray());
        Mockito.verify(movieService, Mockito.atLeastOnce()).findAllByRating(movie1dto.getRating());
    }

    @Test
    void save() {
        List<Integer> stars = new ArrayList<>();
        MovieDTO movieDTO = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO( "bla", "bla", 100, "bla", stars);
        BDDMockito.given(movieService.create(any(MovieCreateDTO.class))).willReturn(movieDTO);
        MovieDTO returnedDTO = movieController.save(movieCreateDTO);
        MovieDTO expectedDTO = new MovieDTO(1, "bla", "bla", 100, "bla", stars);
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<MovieCreateDTO> argumentCaptor = ArgumentCaptor.forClass(MovieCreateDTO.class);
        Mockito.verify(movieService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        MovieCreateDTO provided = argumentCaptor.getValue();
        assertEquals("bla", provided.getName());
        assertEquals("bla", provided.getDirector());
        assertEquals(100, provided.getMinutes());
        assertEquals("bla", provided.getRating());
        assertEquals(stars, provided.getStarIds());
    }

    @Test
    void update() {
        List<Integer> stars = new ArrayList<>();
        List<MovieStar> stars1 = new ArrayList<>();
        Movie movieToUpdate = new Movie("bla", "bla", 100, "bla", stars1);
        ReflectionTestUtils.setField(movieToUpdate, "id", 1);
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO("bla", "bla", 100, "bla", stars);
        MovieCreateDTO movieCreateDTOnew = new MovieCreateDTO( "hello", "bla", 100, "bla", stars);
        MovieDTO returnDTO = new MovieDTO(1, "hello", "bla", 100, "bla", stars);
        BDDMockito.given(movieService.findById(movieToUpdate.getId())).willReturn(Optional.of(movieToUpdate));
        BDDMockito.given(movieService.update(movieToUpdate.getId(), movieCreateDTOnew)).willReturn(returnDTO);
        movieController.save(movieCreateDTO);
        MovieDTO returnedDTO = movieController.update(1, movieCreateDTOnew);
        MovieDTO expectedDTO = new MovieDTO(1, "hello", "bla", 100, "bla", stars);
        assertEquals(expectedDTO, returnedDTO);
    }
}
