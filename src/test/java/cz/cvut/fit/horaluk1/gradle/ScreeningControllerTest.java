package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.ScreeningController;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
import cz.cvut.fit.horaluk1.gradle.service.AuditoriumService;
import cz.cvut.fit.horaluk1.gradle.service.ScreeningService;
import cz.cvut.fit.horaluk1.gradle.service.TicketSeatService;
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
class ScreeningControllerTest {
    @Autowired
    private ScreeningController screeningController;

    @MockBean
    private ScreeningService screeningService;

    @MockBean
    private AuditoriumService auditoriumService;

    @MockBean
    private TicketSeatService ticketSeatService;

    @Test
    void all() {
        ScreeningDTO screening1dto = new ScreeningDTO(1, new Date(0), false, 1, 1);
        ScreeningDTO screening2dto = new ScreeningDTO(2, new Date(0), false, 1, 1);
        ScreeningDTO screening3dto = new ScreeningDTO(3, new Date(0), false, 1, 1);
        List<ScreeningDTO> screeningsdto = new ArrayList<>();
        screeningsdto.add(screening1dto);
        screeningsdto.add(screening2dto);
        screeningsdto.add(screening3dto);
        BDDMockito.given(screeningService.findAll()).willReturn(screeningsdto);
        assertArrayEquals(screeningsdto.toArray(), screeningController.all().toArray());
        Mockito.verify(screeningService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() {
        ScreeningDTO screeningDTO = new ScreeningDTO(1, new Date(0), false, 1, 1);
        BDDMockito.given(screeningService.findByIdAsDTO(screeningDTO.getId())).willReturn(Optional.of(screeningDTO));
        assertEquals(screeningDTO, screeningController.byId(screeningDTO.getId()));
        Mockito.verify(screeningService, Mockito.atLeastOnce()).findByIdAsDTO(screeningDTO.getId());
    }

    @Test
    void allByMovieName() {
        ScreeningDTO screening1dto = new ScreeningDTO(1, new Date(0), false, 1, 1);
        ScreeningDTO screening2dto = new ScreeningDTO(2, new Date(0), false, 1, 1);
        ScreeningDTO screening3dto = new ScreeningDTO(3, new Date(0), false, 1, 1);
        List<ScreeningDTO> screeningsdto = new ArrayList<>();
        screeningsdto.add(screening1dto);
        screeningsdto.add(screening2dto);
        screeningsdto.add(screening3dto);
        BDDMockito.given(screeningService.findAllByMovieName("bla")).willReturn(screeningsdto);
        assertArrayEquals(screeningsdto.toArray(), screeningController.byMovieName("bla").toArray());
        Mockito.verify(screeningService, Mockito.atLeastOnce()).findAllByMovieName("bla");
    }

    @Test
    void save() {
        Auditorium auditorium = new Auditorium(200);
        BDDMockito.given(auditoriumService.findById(1)).willReturn(Optional.of(auditorium));
        ScreeningDTO screeningDTO = new ScreeningDTO(1, new Date(0), false, 1, 1);
        ScreeningCreateDTO screeningCreateDTO = new ScreeningCreateDTO(new Date(0), false, 1, 1);
        BDDMockito.given(screeningService.create(any(ScreeningCreateDTO.class))).willReturn(screeningDTO);
        ScreeningDTO returnedDTO = screeningController.save(screeningCreateDTO);
        ScreeningDTO expectedDTO = new ScreeningDTO(1, new Date(0), false, 1, 1);
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<ScreeningCreateDTO> argumentCaptor = ArgumentCaptor.forClass(ScreeningCreateDTO.class);
        Mockito.verify(screeningService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        ScreeningCreateDTO provided = argumentCaptor.getValue();
        assertEquals(new Date(0), provided.getTime());
        assertFalse(provided.is_3D());
        assertEquals(1,provided.getAuditoriumId());
        assertEquals(1, provided.getMovieId());
    }

    @Test
    void update() {
        Auditorium auditorium = new Auditorium(200);
        BDDMockito.given(auditoriumService.findById(1)).willReturn(Optional.of(auditorium));
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screeningToUpdate = new Screening(new Date(0), false, auditorium, movie);
        ReflectionTestUtils.setField(screeningToUpdate, "id", 1);
        ScreeningCreateDTO screeningCreateDTO = new ScreeningCreateDTO(new Date(0), false, 1, 1);
        ScreeningCreateDTO screeningCreateDTOnew = new ScreeningCreateDTO(new Date(0), true, 1, 1);
        ScreeningDTO returnDTO = new ScreeningDTO(1, new Date(0), true, 1, 1);
        BDDMockito.given(screeningService.findById(screeningToUpdate.getId())).willReturn(Optional.of(screeningToUpdate));
        BDDMockito.given(screeningService.update(screeningToUpdate.getId(), screeningCreateDTOnew)).willReturn(returnDTO);
        ScreeningDTO returnedDTO = screeningController.update(1, screeningCreateDTOnew);
        ScreeningDTO expectedDTO = new ScreeningDTO(1, new Date(0), true, 1, 1);
        assertEquals(expectedDTO, returnedDTO);
    }

    @Test
    void deleteById() {
        ScreeningDTO screeningDTO = new ScreeningDTO(1, new Date(0), false, 1, 1);
        BDDMockito.doNothing().when(screeningService).deleteById(screeningDTO.getId());
        screeningController.deleteById(screeningDTO.getId());
        Mockito.verify(screeningService, Mockito.atLeastOnce()).deleteById(screeningDTO.getId());
    }
}
