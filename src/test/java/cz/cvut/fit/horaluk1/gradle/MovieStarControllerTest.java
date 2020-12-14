package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.MovieStarController;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MovieStarControllerTest {
    @Autowired
    private MovieStarController movieStarController;

    @MockBean
    private MovieStarService movieStarService;

    @Test
    void all() {
        MovieStarDTO movieStar1dto = new MovieStarDTO(1, "bla", "bla");
        MovieStarDTO movieStar2dto = new MovieStarDTO(2, "bla", "bla");
        MovieStarDTO movieStar3dto = new MovieStarDTO(3, "bla", "bla");
        List<MovieStarDTO> movieStarsdto = new ArrayList<>();
        movieStarsdto.add(movieStar1dto);
        movieStarsdto.add(movieStar2dto);
        movieStarsdto.add(movieStar3dto);
        BDDMockito.given(movieStarService.findAll()).willReturn(movieStarsdto);
        assertArrayEquals(movieStarsdto.toArray(), movieStarController.all().toArray());
        Mockito.verify(movieStarService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() {
        MovieStarDTO movieStarDTO = new MovieStarDTO(1, "bla", "bla");
        BDDMockito.given(movieStarService.findByIdAsDTO(movieStarDTO.getId())).willReturn(Optional.of(movieStarDTO));
        assertEquals(movieStarDTO, movieStarController.byId(movieStarDTO.getId()));
        Mockito.verify(movieStarService, Mockito.atLeastOnce()).findByIdAsDTO(movieStarDTO.getId());
    }

    @Test
    void allById() {
        MovieStarDTO movieStar1dto = new MovieStarDTO(1, "bla", "bla");
        MovieStarDTO movieStar2dto = new MovieStarDTO(2, "bla", "bla");
        MovieStarDTO movieStar3dto = new MovieStarDTO(3, "bla", "bla");
        List<MovieStarDTO> movieStarsdto = new ArrayList<>();
        movieStarsdto.add(movieStar1dto);
        movieStarsdto.add(movieStar2dto);
        movieStarsdto.add(movieStar3dto);
        List<Integer> ids = new ArrayList<>();
        BDDMockito.given(movieStarService.findByIds(ids)).willReturn(movieStarsdto);
        assertArrayEquals(movieStarsdto.toArray(), movieStarController.allById(ids).toArray());
        Mockito.verify(movieStarService, Mockito.atLeastOnce()).findByIds(ids);
    }

    @Test
    void allByName() {
        MovieStarDTO movieStarDTO = new MovieStarDTO(1, "bla", "bla");
        BDDMockito.given(movieStarService.findAllByFirstNameAndLastName(movieStarDTO.getFirstName(), movieStarDTO.getLastName())).willReturn(Optional.of(movieStarDTO));
        assertEquals(movieStarDTO, movieStarController.allByName(movieStarDTO.getFirstName(), movieStarDTO.getLastName()));
        Mockito.verify(movieStarService, Mockito.atLeastOnce()).findAllByFirstNameAndLastName(movieStarDTO.getFirstName(), movieStarDTO.getLastName());
    }

    @Test
    void save() {
        MovieStarDTO movieStarDTO = new MovieStarDTO(1, "bla", "bla");
        MovieStarCreateDTO movieStarCreateDTO = new MovieStarCreateDTO("bla", "bla");
        BDDMockito.given(movieStarService.create(any(MovieStarCreateDTO.class))).willReturn(movieStarDTO);
        MovieStarDTO returnedDTO = movieStarController.save(movieStarCreateDTO);
        MovieStarDTO expectedDTO = new MovieStarDTO(1, "bla", "bla");
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<MovieStarCreateDTO> argumentCaptor = ArgumentCaptor.forClass(MovieStarCreateDTO.class);
        Mockito.verify(movieStarService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        MovieStarCreateDTO provided = argumentCaptor.getValue();
        assertEquals("bla", provided.getFirstName());
        assertEquals("bla", provided.getLastName());
    }

    @Test
    void update() {
        MovieStar movieStarToUpdate = new MovieStar("bla", "bla");
        ReflectionTestUtils.setField(movieStarToUpdate, "id", 1);
        MovieStarCreateDTO movieStarCreateDTO = new MovieStarCreateDTO("bla", "bla");
        MovieStarCreateDTO movieStarCreateDTOnew = new MovieStarCreateDTO("hello", "bla");
        MovieStarDTO returnDTO = new MovieStarDTO(1, "hello", "bla");
        BDDMockito.given(movieStarService.findById(movieStarToUpdate.getId())).willReturn(Optional.of(movieStarToUpdate));
        BDDMockito.given(movieStarService.update(movieStarToUpdate.getId(), movieStarCreateDTOnew)).willReturn(returnDTO);
        movieStarController.save(movieStarCreateDTO);
        MovieStarDTO returnedDTO = movieStarController.update(1, movieStarCreateDTOnew);
        MovieStarDTO expectedDTO = new MovieStarDTO(1, "hello", "bla");
        assertEquals(expectedDTO, returnedDTO);
    }
}
