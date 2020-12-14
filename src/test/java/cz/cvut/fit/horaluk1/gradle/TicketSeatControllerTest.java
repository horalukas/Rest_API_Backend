package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.TicketSeatController;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class TicketSeatControllerTest {
    @Autowired
    private TicketSeatController ticketSeatController;

    @MockBean
    private TicketSeatService ticketSeatService;

    @Test
    void all() {
        TicketSeatDTO ticketSeat1dto = new TicketSeatDTO(1, 1, false, null, 1);
        TicketSeatDTO ticketSeat2dto = new TicketSeatDTO(2, 1, false, null, 1);
        TicketSeatDTO ticketSeat3dto = new TicketSeatDTO(3, 1, false, null, 1);
        List<TicketSeatDTO> ticketSeatsdto = new ArrayList<>();
        ticketSeatsdto.add(ticketSeat1dto);
        ticketSeatsdto.add(ticketSeat2dto);
        ticketSeatsdto.add(ticketSeat3dto);
        BDDMockito.given(ticketSeatService.findAll()).willReturn(ticketSeatsdto);
        assertArrayEquals(ticketSeatsdto.toArray(), ticketSeatController.all().toArray());
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() {
        TicketSeatDTO ticketSeatDTO = new TicketSeatDTO(1, 1, false, null, 1);
        BDDMockito.given(ticketSeatService.findByIdAsDTO(ticketSeatDTO.getId())).willReturn(Optional.of(ticketSeatDTO));
        assertEquals(ticketSeatDTO, ticketSeatController.byId(ticketSeatDTO.getId()));
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).findByIdAsDTO(ticketSeatDTO.getId());
    }

    @Test
    void allByOwnerEmail() {
        TicketSeatDTO ticketSeat1dto = new TicketSeatDTO(1, 1, false, null, 1);
        TicketSeatDTO ticketSeat2dto = new TicketSeatDTO(2, 1, false, null, 1);
        TicketSeatDTO ticketSeat3dto = new TicketSeatDTO(3, 1, false, null, 1);
        List<TicketSeatDTO> ticketSeatsdto = new ArrayList<>();
        ticketSeatsdto.add(ticketSeat1dto);
        ticketSeatsdto.add(ticketSeat2dto);
        ticketSeatsdto.add(ticketSeat3dto);
        BDDMockito.given(ticketSeatService.findAllByOwnerEmail("bla")).willReturn(ticketSeatsdto);
        assertArrayEquals(ticketSeatsdto.toArray(), ticketSeatController.byOwnerEmail("bla").toArray());
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).findAllByOwnerEmail("bla");
    }

    @Test
    void allByScreeningId() {
        TicketSeatDTO ticketSeat1dto = new TicketSeatDTO(1, 1, false, null, 1);
        TicketSeatDTO ticketSeat2dto = new TicketSeatDTO(2, 1, false, null, 1);
        TicketSeatDTO ticketSeat3dto = new TicketSeatDTO(3, 1, false, null, 1);
        List<TicketSeatDTO> ticketSeatsdto = new ArrayList<>();
        ticketSeatsdto.add(ticketSeat1dto);
        ticketSeatsdto.add(ticketSeat2dto);
        ticketSeatsdto.add(ticketSeat3dto);
        BDDMockito.given(ticketSeatService.findAllByScreeningId(1)).willReturn(ticketSeatsdto);
        assertArrayEquals(ticketSeatsdto.toArray(), ticketSeatController.byScreeningId(1).toArray());
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).findAllByScreeningId(1);
    }

    @Test
    void save() {
        TicketSeatDTO ticketSeatDTO = new TicketSeatDTO(1, 1, false, null, 1);
        TicketSeatCreateDTO ticketSeatCreateDTO = new TicketSeatCreateDTO(1, false, null, 1);
        BDDMockito.given(ticketSeatService.create(any(TicketSeatCreateDTO.class))).willReturn(ticketSeatDTO);
        TicketSeatDTO returnedDTO = ticketSeatController.save(ticketSeatCreateDTO);
        TicketSeatDTO expectedDTO = new TicketSeatDTO(1, 1, false, null, 1);
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<TicketSeatCreateDTO> argumentCaptor = ArgumentCaptor.forClass(TicketSeatCreateDTO.class);
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        TicketSeatCreateDTO provided = argumentCaptor.getValue();
        assertEquals(1, provided.getNumber());
        assertFalse(provided.isTaken());
        assertNull(provided.getOwnerId());
        assertEquals(1, provided.getScreeningId());
    }

    @Test
    void update() {
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeat ticketSeatToUpdate = new TicketSeat(1, false, null, screening);
        ReflectionTestUtils.setField(ticketSeatToUpdate, "id", 1);
        TicketSeatCreateDTO ticketSeatCreateDTO = new TicketSeatCreateDTO(1, false, null, 1);
        TicketSeatCreateDTO ticketSeatCreateDTOnew = new TicketSeatCreateDTO(2, false, null, 1);
        TicketSeatDTO returnDTO = new TicketSeatDTO(1, 2, false, null, 1);
        BDDMockito.given(ticketSeatService.findById(ticketSeatToUpdate.getId())).willReturn(Optional.of(ticketSeatToUpdate));
        BDDMockito.given(ticketSeatService.update(ticketSeatToUpdate.getId(), ticketSeatCreateDTOnew)).willReturn(returnDTO);
        ticketSeatController.save(ticketSeatCreateDTO);
        TicketSeatDTO returnedDTO = ticketSeatController.update(1, ticketSeatCreateDTOnew);
        TicketSeatDTO expectedDTO = new TicketSeatDTO(1, 2, false, null, 1);
        assertEquals(expectedDTO, returnedDTO);
    }

    @Test
    void deleteById() {
        TicketSeatDTO ticketSeatDTO = new TicketSeatDTO(1, 1, false, null, 1);
        BDDMockito.doNothing().when(ticketSeatService).deleteById(ticketSeatDTO.getId());
        ticketSeatController.deleteById(ticketSeatDTO.getId());
        Mockito.verify(ticketSeatService, Mockito.atLeastOnce()).deleteById(ticketSeatDTO.getId());
    }
}
