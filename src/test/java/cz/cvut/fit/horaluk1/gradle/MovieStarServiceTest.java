package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.MovieStarCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
import cz.cvut.fit.horaluk1.gradle.repository.MovieStarRepository;
import cz.cvut.fit.horaluk1.gradle.service.MovieStarService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class MovieStarServiceTest {
    @Autowired
    private MovieStarService movieStarService;

    @MockBean
    private MovieStarRepository movieStarRepositoryMock;

    @Test
    void findAll(){
        MovieStarDTO movieStarDTO1 = new MovieStarDTO(0, "Brad", "Pitt");
        MovieStarDTO movieStarDTO2 = new MovieStarDTO(0, "Brad", "Pitt");
        List<MovieStarDTO> movieStarDTOList = new ArrayList<>();
        movieStarDTOList.add(movieStarDTO1);
        movieStarDTOList.add(movieStarDTO2);
        MovieStar movieStar1 = new MovieStar("Brad", "Pitt");
        MovieStar movieStar2 = new MovieStar("Brad", "Pitt");
        List<MovieStar> movieStarList = new ArrayList<>();
        movieStarList.add(movieStar1);
        movieStarList.add(movieStar2);
        BDDMockito.given(movieStarRepositoryMock.findAll()).willReturn(movieStarList);
        assertArrayEquals(movieStarDTOList.toArray(), movieStarService.findAll().toArray());
    }

    @Test
    void findByIds(){
        MovieStarDTO movieStarDTO1 = new MovieStarDTO(0, "Brad", "Pitt");
        MovieStarDTO movieStarDTO2 = new MovieStarDTO(0, "Brad", "Pitt");
        List<MovieStarDTO> movieStarDTOList = new ArrayList<>();
        movieStarDTOList.add(movieStarDTO1);
        movieStarDTOList.add(movieStarDTO2);
        MovieStar movieStar1 = new MovieStar("Brad", "Pitt");
        MovieStar movieStar2 = new MovieStar("Brad", "Pitt");
        List<MovieStar> movieStarList = new ArrayList<>();
        movieStarList.add(movieStar1);
        movieStarList.add(movieStar2);
        List<Integer> ids = new ArrayList<>();
        BDDMockito.given(movieStarRepositoryMock.findAllById(ids)).willReturn(movieStarList);
        assertArrayEquals(movieStarDTOList.toArray(), movieStarService.findByIds(ids).toArray());
    }

    @Test
    void findById(){
        MovieStar movieStar = new MovieStar("Angelina", "Jolie");
        BDDMockito.given(movieStarRepositoryMock.findById(movieStar.getId())).willReturn(Optional.of(movieStar));
        assertEquals(movieStar, movieStarService.findById(movieStar.getId()).get());
        Mockito.verify(movieStarRepositoryMock, Mockito.atLeastOnce()).findById(movieStar.getId());
    }

    @Test
    void findByIdAsDTO(){
        MovieStar movieStar = new MovieStar("Hugh", "Jackman");
        MovieStarDTO movieStarDTO = new MovieStarDTO(0, "Hugh", "Jackman");
        BDDMockito.given(movieStarRepositoryMock.findById(movieStar.getId())).willReturn(Optional.of(movieStar));
        MovieStarDTO returnDTO = movieStarService.findByIdAsDTO(movieStar.getId()).get();
        assertEquals(movieStarDTO, movieStarService.findByIdAsDTO(movieStar.getId()).get());
        Mockito.verify(movieStarRepositoryMock, Mockito.atLeastOnce()).findById(movieStar.getId());
    }

    @Test
    void findAllByFirstNameAndLastName(){
        MovieStar movieStar = new MovieStar("Hugh", "Jackman");
        MovieStarDTO movieStarDTO = new MovieStarDTO(0, "Hugh", "Jackman");
        BDDMockito.given(movieStarRepositoryMock.findAllByFirstNameAndLastName(movieStar.getFirstName(), movieStar.getLastName())).willReturn(Optional.of(movieStar));
        MovieStarDTO returnDTO = movieStarService.findAllByFirstNameAndLastName(movieStar.getFirstName(), movieStar.getLastName()).get();
        assertEquals(movieStarDTO, movieStarService.findAllByFirstNameAndLastName(movieStar.getFirstName(), movieStar.getLastName()).get());
        Mockito.verify(movieStarRepositoryMock, Mockito.atLeastOnce()).findAllByFirstNameAndLastName(movieStar.getFirstName(), movieStar.getLastName());
    }

    @Test
    void create() throws Exception {
        MovieStar movieStar = new MovieStar("hello", "world");
        ReflectionTestUtils.setField(movieStar, "id", 1);
        MovieStarCreateDTO movieStarCreateDTO = new MovieStarCreateDTO("hello", "world");
        BDDMockito.given(movieStarRepositoryMock.save(any(MovieStar.class))).willReturn(movieStar);
        MovieStarDTO returnedDTO = movieStarService.create(movieStarCreateDTO);
        MovieStarDTO expectedDTO = new MovieStarDTO(1, "hello", "world");
        assertEquals(expectedDTO, returnedDTO);
        ArgumentCaptor<MovieStar> argumentCaptor = ArgumentCaptor.forClass(MovieStar.class);
        Mockito.verify(movieStarRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        MovieStar provided = argumentCaptor.getValue();
        assertEquals("hello", provided.getFirstName());
        assertEquals("world", provided.getLastName());
    }

    @Test
    void update() throws Exception {
        MovieStar movieStar = new MovieStar("hello", "world");
        ReflectionTestUtils.setField(movieStar, "id", 1);
        MovieStarCreateDTO movieStarCreateDTO = new MovieStarCreateDTO("hello", "world");
        BDDMockito.given(movieStarRepositoryMock.save(any(MovieStar.class))).willReturn(movieStar);
        BDDMockito.given(movieStarRepositoryMock.findById(movieStar.getId())).willReturn(Optional.of(movieStar));
        movieStarService.create(movieStarCreateDTO);
        MovieStarDTO expectedDTO = new MovieStarDTO(1, "good", "bye");
        MovieStarCreateDTO movieStarCreateDTO1 = new MovieStarCreateDTO("good", "bye");
        MovieStarDTO returnedDTO = movieStarService.update(1, movieStarCreateDTO1);
        assertEquals(expectedDTO, returnedDTO);
    }
}
