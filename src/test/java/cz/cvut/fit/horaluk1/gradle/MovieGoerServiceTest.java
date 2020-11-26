package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.repository.MovieGoerRepository;
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
public class MovieGoerServiceTest {
    @Autowired
    private MovieGoerService movieGoerService;

    @MockBean
    private MovieGoerRepository movieGoerRepositoryMock;

    @Test
    void findAll(){
        MovieGoerDTO movieGoerDTO1 = new MovieGoerDTO(0,"bla", "123");
        MovieGoerDTO movieGoerDTO2 = new MovieGoerDTO(0,"bla", "123");
        List<MovieGoerDTO> movieGoerDTOList = new ArrayList<>();
        movieGoerDTOList.add(movieGoerDTO1);
        movieGoerDTOList.add(movieGoerDTO2);
        MovieGoer movieGoer1 = new MovieGoer("bla", "123");
        MovieGoer movieGoer2 = new MovieGoer("bla", "123");
        List<MovieGoer> movieGoerList = new ArrayList<>();
        movieGoerList.add(movieGoer1);
        movieGoerList.add(movieGoer2);
        BDDMockito.given(movieGoerRepositoryMock.findAll()).willReturn(movieGoerList);
        assertArrayEquals(movieGoerDTOList.toArray(), movieGoerService.findAll().toArray());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById(){
        MovieGoer movieGoer = new MovieGoer("bla", "fdfd");
        BDDMockito.given(movieGoerRepositoryMock.findById(movieGoer.getId())).willReturn(Optional.of(movieGoer));
        assertEquals(movieGoer, movieGoerService.findById(movieGoer.getId()).get());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).findById(movieGoer.getId());
    }

    @Test
    void findByIdAsDTO(){
        MovieGoer movieGoer = new MovieGoer("bla", "fdfd");
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(0, "bla", "fdfd");
        BDDMockito.given(movieGoerRepositoryMock.findById(movieGoer.getId())).willReturn(Optional.of(movieGoer));
        MovieGoerDTO returnsgoer = movieGoerService.findByIdAsDTO(movieGoer.getId()).get();
        assertEquals(movieGoerDTO, movieGoerService.findByIdAsDTO(movieGoer.getId()).get());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).findById(movieGoer.getId());
    }

    @Test
    void findByEmail(){
        MovieGoer movieGoer = new MovieGoer("bla", "fdfd");
        MovieGoerDTO movieGoerDTO = new MovieGoerDTO(0, "bla", "fdfd");
        BDDMockito.given(movieGoerRepositoryMock.findByEmail(movieGoer.getEmail())).willReturn(Optional.of(movieGoer));
        MovieGoerDTO returnsgoer = movieGoerService.findByEmail(movieGoer.getEmail()).get();
        assertEquals(movieGoerDTO, movieGoerService.findByEmail(movieGoer.getEmail()).get());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).findByEmail(movieGoer.getEmail());
    }

    @Test
    void create(){
        MovieGoer movieGoer = new MovieGoer("hello", "world");
        ReflectionTestUtils.setField(movieGoer, "id", 1);
        MovieGoerCreateDTO movieGoerCreateDTO = new MovieGoerCreateDTO("hello", "world");
        BDDMockito.given(movieGoerRepositoryMock.save(any(MovieGoer.class))).willReturn(movieGoer);
        MovieGoerDTO returnedDTO = movieGoerService.create(movieGoerCreateDTO);
        MovieGoerDTO expectedDTO = new MovieGoerDTO(1, "hello", "world");
        assertEquals(expectedDTO, returnedDTO);
        ArgumentCaptor<MovieGoer> argumentCaptor = ArgumentCaptor.forClass(MovieGoer.class);
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        MovieGoer provided = argumentCaptor.getValue();
        assertEquals("hello", provided.getEmail());
        assertEquals("world", provided.getPassword());
    }

    @Test
    void update() throws Exception {
        MovieGoer movieGoer = new MovieGoer("hello", "world");
        ReflectionTestUtils.setField(movieGoer, "id", 1);
        MovieGoerCreateDTO movieGoerCreateDTO = new MovieGoerCreateDTO("hello", "world");
        BDDMockito.given(movieGoerRepositoryMock.save(any(MovieGoer.class))).willReturn(movieGoer);
        BDDMockito.given(movieGoerRepositoryMock.findById(movieGoer.getId())).willReturn(Optional.of(movieGoer));
        movieGoerService.create(movieGoerCreateDTO);
        MovieGoerDTO expectedDTO = new MovieGoerDTO(1, "good", "bye");
        MovieGoerCreateDTO movieGoerCreateDTO1 = new MovieGoerCreateDTO("good", "bye");
        MovieGoerDTO returnedDTO = movieGoerService.update(1, movieGoerCreateDTO1);
        assertEquals(expectedDTO, returnedDTO);
    }

    @Test
    void delete(){
        MovieGoer movieGoer = new MovieGoer("bla", "123");
        BDDMockito.doNothing().when(movieGoerRepositoryMock).delete(movieGoer);
        movieGoerService.delete(movieGoer);
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).delete(movieGoer);
    }

    @Test
    void deleteById(){
        MovieGoer movieGoer = new MovieGoer("bla", "123");
        BDDMockito.doNothing().when(movieGoerRepositoryMock).deleteById(movieGoer.getId());
        movieGoerService.deleteById(movieGoer.getId());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).deleteById(movieGoer.getId());
    }

    @Test
    void deleteByEmail(){
        MovieGoer movieGoer = new MovieGoer("bla", "123");
        BDDMockito.doNothing().when(movieGoerRepositoryMock).deleteByEmail(movieGoer.getEmail());
        movieGoerService.deleteByEmail(movieGoer.getEmail());
        Mockito.verify(movieGoerRepositoryMock, Mockito.atLeastOnce()).deleteByEmail(movieGoer.getEmail());
    }
}
