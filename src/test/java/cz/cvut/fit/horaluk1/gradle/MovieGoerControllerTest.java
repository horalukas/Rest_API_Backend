package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.MovieGoerController;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.service.MovieGoerService;
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
class MovieGoerControllerTest {
    @Autowired
    private MovieGoerController movieGoerController;

    @MockBean
    private MovieGoerService movieGoerService;

    @Test
    void all(){
        MovieGoerDTO movieGoer1dto = new MovieGoerDTO(1, "bla", "bla");
        MovieGoerDTO movieGoer2dto = new MovieGoerDTO(2, "bla", "bla");
        MovieGoerDTO movieGoer3dto = new MovieGoerDTO(3, "bla", "bla");
        List<MovieGoerDTO> movieGoersdto = new ArrayList<>();
        movieGoersdto.add(movieGoer1dto);
        movieGoersdto.add(movieGoer2dto);
        movieGoersdto.add(movieGoer3dto);
        BDDMockito.given(movieGoerService.findAll()).willReturn(movieGoersdto);
        assertArrayEquals(movieGoersdto.toArray(), movieGoerController.all().toArray());
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId(){
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(1, "bla", "bla");
        BDDMockito.given(movieGoerService.findByIdAsDTO(movieGoerDTO.getId())).willReturn(Optional.of(movieGoerDTO));
        assertEquals(movieGoerDTO, movieGoerController.byId(movieGoerDTO.getId()));
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).findByIdAsDTO(movieGoerDTO.getId());
    }

    @Test
    void byEmail(){
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(1, "bla", "bla");
        BDDMockito.given(movieGoerService.findByEmail(movieGoerDTO.getEmail())).willReturn(Optional.of(movieGoerDTO));
        assertEquals(movieGoerDTO, movieGoerController.byEmail(movieGoerDTO.getEmail()));
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).findByEmail(movieGoerDTO.getEmail());
    }

    @Test
    void save(){
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(1, "bla", "bla");
        MovieGoerCreateDTO movieGoerCreateDTO = new MovieGoerCreateDTO("bla", "bla");
        BDDMockito.given(movieGoerService.create(any(MovieGoerCreateDTO.class))).willReturn(movieGoerDTO);
        MovieGoerDTO returnedDTO = movieGoerController.save(movieGoerCreateDTO);
        MovieGoerDTO expectedDTO = new MovieGoerDTO(1, "bla", "bla");
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<MovieGoerCreateDTO> argumentCaptor = ArgumentCaptor.forClass(MovieGoerCreateDTO.class);
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        MovieGoerCreateDTO provided = argumentCaptor.getValue();
        assertEquals("bla", provided.getEmail());
        assertEquals("bla", provided.getPassword());
    }

    @Test
    void update(){
        MovieGoer movieGoerToUpdate = new MovieGoer("bla", "bla");
        ReflectionTestUtils.setField(movieGoerToUpdate, "id", 1);
        MovieGoerCreateDTO movieGoerCreateDTO = new MovieGoerCreateDTO("bla", "bla");
        MovieGoerCreateDTO movieGoerCreateDTOnew = new MovieGoerCreateDTO("hello", "bla");
        MovieGoerDTO returnDTO = new MovieGoerDTO(1, "hello", "bla");
        BDDMockito.given(movieGoerService.findById(movieGoerToUpdate.getId())).willReturn(Optional.of(movieGoerToUpdate));
        BDDMockito.given(movieGoerService.update(movieGoerToUpdate.getId(), movieGoerCreateDTOnew)).willReturn(returnDTO);
        movieGoerController.save(movieGoerCreateDTO);
        MovieGoerDTO returnedDTO = movieGoerController.update(1, movieGoerCreateDTOnew);
        MovieGoerDTO expectedDTO = new MovieGoerDTO(1, "hello", "bla");
        assertEquals(expectedDTO, returnedDTO);
    }

    @Test
    void deleteById(){
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(1, "bla", "bla");
        BDDMockito.doNothing().when(movieGoerService).deleteById(movieGoerDTO.getId());
        movieGoerController.deleteById(movieGoerDTO.getId());
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).deleteById(movieGoerDTO.getId());
    }

    @Test
    void deleteByEmail(){
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(1, "bla", "bla");
        BDDMockito.doNothing().when(movieGoerService).deleteByEmail(movieGoerDTO.getEmail());
        movieGoerController.deleteByEmail(movieGoerDTO.getEmail());
        Mockito.verify(movieGoerService, Mockito.atLeastOnce()).deleteByEmail(movieGoerDTO.getEmail());
    }
}