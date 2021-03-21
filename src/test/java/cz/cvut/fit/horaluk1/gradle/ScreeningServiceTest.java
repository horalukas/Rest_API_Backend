package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.repository.AuditoriumRepository;
import cz.cvut.fit.horaluk1.gradle.repository.MovieRepository;
import cz.cvut.fit.horaluk1.gradle.repository.ScreeningRepository;
import cz.cvut.fit.horaluk1.gradle.service.AuditoriumService;
import cz.cvut.fit.horaluk1.gradle.service.MovieService;
import cz.cvut.fit.horaluk1.gradle.service.ScreeningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ScreeningServiceTest {
    @Autowired
    private ScreeningService screeningService;

    @MockBean
    private ScreeningRepository screeningRepositoryMock;

    @MockBean
    private AuditoriumRepository auditoriumRepository;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void findAll(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        ScreeningDTO screeningDTO1 = new ScreeningDTO(0, new Date(), true, auditorium.getId(), movie.getId());
        ScreeningDTO screeningDTO2 = new ScreeningDTO(0, new Date(), true, auditorium.getId(), movie.getId());
        List<ScreeningDTO> screeningsdto = new ArrayList<>();
        screeningsdto.add(screeningDTO1);
        screeningsdto.add(screeningDTO2);
        Screening screening1 = new Screening(new Date(), true, auditorium, movie);
        Screening screening2 = new Screening(new Date(), true, auditorium, movie);
        List<Screening> screenings = new ArrayList<>();
        screenings.add(screening1);
        screenings.add(screening2);
        BDDMockito.given(screeningRepositoryMock.findAll()).willReturn(screenings);
        assertArrayEquals(screeningsdto.toArray(), screeningService.findAll().toArray());
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        BDDMockito.given(screeningRepositoryMock.findById(screening.getId())).willReturn(Optional.of(screening));
        assertEquals(screening, screeningService.findById(screening.getId()).get());
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).findById(screening.getId());
    }

    @Test
    void findByIdAsDTO(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        ScreeningDTO screeningDTO = new ScreeningDTO(0,new Date(), true, auditorium.getId(), movie.getId());
        BDDMockito.given(screeningRepositoryMock.findById(screening.getId())).willReturn(Optional.of(screening));
        assertEquals(screeningDTO, screeningService.findByIdAsDTO(screening.getId()).get());
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).findById(screening.getId());
    }

    @Test
    void findAllByMovieName(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        ScreeningDTO screeningDTO1 = new ScreeningDTO(0, new Date(), true, auditorium.getId(), movie.getId());
        ScreeningDTO screeningDTO2 = new ScreeningDTO(0, new Date(), true, auditorium.getId(), movie.getId());
        List<ScreeningDTO> screeningsdto = new ArrayList<>();
        screeningsdto.add(screeningDTO1);
        screeningsdto.add(screeningDTO2);
        Screening screening1 = new Screening(new Date(), true, auditorium, movie);
        Screening screening2 = new Screening(new Date(), true, auditorium, movie);
        List<Screening> screenings = new ArrayList<>();
        screenings.add(screening1);
        screenings.add(screening2);
        BDDMockito.given(screeningRepositoryMock.findAllByMovieName(movie.getName())).willReturn(screenings);
        assertArrayEquals(screeningsdto.toArray(), screeningService.findAllByMovieName(movie.getName()).toArray());
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).findAllByMovieName(movie.getName());
    }

    @Test
    void create() throws Exception {
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(1), true, auditorium, movie);
        ReflectionTestUtils.setField(auditorium, "id", 1);
        ReflectionTestUtils.setField(movie, "id", 1);
        ReflectionTestUtils.setField(screening, "id", 1);
        BDDMockito.given(screeningRepositoryMock.save(any(Screening.class))).willReturn(screening);
        BDDMockito.given(auditoriumRepository.findById(screening.getAuditorium().getId())).willReturn(Optional.of(auditorium));
        BDDMockito.given(movieRepository.findById(screening.getMovie().getId())).willReturn(Optional.of(movie));
        ScreeningCreateDTO screeningCreateDTO = new ScreeningCreateDTO(new Date(1), true, auditorium.getId(), movie.getId());
        ScreeningDTO returnedScreeningDTO = screeningService.create(screeningCreateDTO);
        ScreeningDTO expectedScreeningDTO = new ScreeningDTO(1, new Date(1), true, auditorium.getId(), movie.getId());
        assertEquals(expectedScreeningDTO, returnedScreeningDTO);
        ArgumentCaptor<Screening> argumentCaptor = ArgumentCaptor.forClass(Screening.class);
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Screening provided = argumentCaptor.getValue();
        assertEquals(new Date(1), provided.getTime());
        assertTrue(provided.is_3D());
        assertEquals(auditorium,provided.getAuditorium());
        assertEquals(movie, provided.getMovie());
    }

    @Test
    void update() throws Exception {
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(1), true, auditorium, movie);
        ReflectionTestUtils.setField(auditorium, "id", 1);
        ReflectionTestUtils.setField(movie, "id", 1);
        ReflectionTestUtils.setField(screening, "id", 1);
        BDDMockito.given(screeningRepositoryMock.save(any(Screening.class))).willReturn(screening);
        BDDMockito.given(auditoriumRepository.findById(screening.getAuditorium().getId())).willReturn(Optional.of(auditorium));
        BDDMockito.given(movieRepository.findById(screening.getMovie().getId())).willReturn(Optional.of(movie));
        BDDMockito.given(screeningRepositoryMock.findById(screening.getId())).willReturn(Optional.of(screening));
        ScreeningCreateDTO screeningCreateDTO = new ScreeningCreateDTO(new Date(1), true, auditorium.getId(), movie.getId());
        screeningService.create(screeningCreateDTO);
        ScreeningDTO expectedScreeningDTO = new ScreeningDTO(1, new Date(150), false, auditorium.getId(), movie.getId());
        ScreeningCreateDTO screeningCreateDTO1 = new ScreeningCreateDTO(new Date(150), false, auditorium.getId(), movie.getId());
        ScreeningDTO returnedScreeningDTO = screeningService.update(1, screeningCreateDTO1);
        assertEquals(expectedScreeningDTO, returnedScreeningDTO);
    }

    @Test
    void delete(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        BDDMockito.doNothing().when(screeningRepositoryMock).delete(screening);
        screeningService.delete(screening);
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).delete(screening);
    }

    @Test
    void deleteById(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        BDDMockito.doNothing().when(screeningRepositoryMock).deleteById(screening.getId());
        screeningService.deleteById(screening.getId());
        Mockito.verify(screeningRepositoryMock, Mockito.atLeastOnce()).deleteById(screening.getId());
    }
}
