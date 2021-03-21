package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatDTO;
import cz.cvut.fit.horaluk1.gradle.entity.*;
import cz.cvut.fit.horaluk1.gradle.repository.MovieGoerRepository;
import cz.cvut.fit.horaluk1.gradle.repository.ScreeningRepository;
import cz.cvut.fit.horaluk1.gradle.repository.TicketSeatRepository;
import cz.cvut.fit.horaluk1.gradle.service.TicketSeatService;
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
public class TicketSeatServiceTest {
    @Autowired
    private TicketSeatService ticketSeatService;

    @MockBean
    private TicketSeatRepository ticketSeatRepositoryMock;

    @MockBean
    private MovieGoerRepository movieGoerRepository;

    @MockBean
    private ScreeningRepository screeningRepository;

    @Test
    void findAll(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeatDTO ticketSeatDTO1 = new TicketSeatDTO(0, 1, false, null, 0);
        TicketSeatDTO ticketSeatDTO2 = new TicketSeatDTO(0, 2, false, null, 0);
        TicketSeatDTO ticketSeatDTO3 = new TicketSeatDTO(0, 3, false, null, 0);
        List<TicketSeatDTO> ticketseatsdto = new ArrayList<>();
        ticketseatsdto.add(ticketSeatDTO1);
        ticketseatsdto.add(ticketSeatDTO2);
        ticketseatsdto.add(ticketSeatDTO3);
        TicketSeat ticketSeat1 = new TicketSeat(1,false, null, screening);
        TicketSeat ticketSeat2 = new TicketSeat(2,false, null, screening);
        TicketSeat ticketSeat3 = new TicketSeat(3,false, null, screening);
        List<TicketSeat> ticketSeats = new ArrayList<>();
        ticketSeats.add(ticketSeat1);
        ticketSeats.add(ticketSeat2);
        ticketSeats.add(ticketSeat3);
        BDDMockito.given(ticketSeatRepositoryMock.findAll()).willReturn(ticketSeats);
        assertArrayEquals(ticketseatsdto.toArray(), ticketSeatService.findAll().toArray());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        BDDMockito.given(ticketSeatRepositoryMock.findById(ticketSeat.getId())).willReturn(Optional.of(ticketSeat));
        assertEquals(ticketSeat, ticketSeatService.findById(ticketSeat.getId()).get());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).findById(ticketSeat.getId());
    }
    @Test
    void findAllByOwnerEmail(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        MovieGoer movieGoer = new MovieGoer("blabla@bla.com", "123");
        TicketSeatDTO ticketSeatDTO1 = new TicketSeatDTO(0, 1, false, 0, 0);
        TicketSeatDTO ticketSeatDTO2 = new TicketSeatDTO(0, 2, false, 0, 0);
        TicketSeatDTO ticketSeatDTO3 = new TicketSeatDTO(0, 3, false, 0, 0);
        List<TicketSeatDTO> ticketseatsdto = new ArrayList<>();
        ticketseatsdto.add(ticketSeatDTO1);
        ticketseatsdto.add(ticketSeatDTO2);
        ticketseatsdto.add(ticketSeatDTO3);
        TicketSeat ticketSeat1 = new TicketSeat(1,false, movieGoer, screening);
        TicketSeat ticketSeat2 = new TicketSeat(2,false, movieGoer, screening);
        TicketSeat ticketSeat3 = new TicketSeat(3,false, movieGoer, screening);
        List<TicketSeat> ticketSeats = new ArrayList<>();
        ticketSeats.add(ticketSeat1);
        ticketSeats.add(ticketSeat2);
        ticketSeats.add(ticketSeat3);
        BDDMockito.given(ticketSeatRepositoryMock.findAllByOwnerEmail(movieGoer.getEmail())).willReturn(ticketSeats);
        assertArrayEquals(ticketseatsdto.toArray(), ticketSeatService.findAllByOwnerEmail(movieGoer.getEmail()).toArray());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).findAllByOwnerEmail(movieGoer.getEmail());
    }

    @Test
    void findAllByScreeningId(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        MovieGoer movieGoer = new MovieGoer("blabla@bla.com", "123");
        TicketSeatDTO ticketSeatDTO1 = new TicketSeatDTO(0, 1, false, 0, 0);
        TicketSeatDTO ticketSeatDTO2 = new TicketSeatDTO(0, 2, false, 0, 0);
        TicketSeatDTO ticketSeatDTO3 = new TicketSeatDTO(0, 3, false, 0, 0);
        List<TicketSeatDTO> ticketseatsdto = new ArrayList<>();
        ticketseatsdto.add(ticketSeatDTO1);
        ticketseatsdto.add(ticketSeatDTO2);
        ticketseatsdto.add(ticketSeatDTO3);
        TicketSeat ticketSeat1 = new TicketSeat(1,false, movieGoer, screening);
        TicketSeat ticketSeat2 = new TicketSeat(2,false, movieGoer, screening);
        TicketSeat ticketSeat3 = new TicketSeat(3,false, movieGoer, screening);
        List<TicketSeat> ticketSeats = new ArrayList<>();
        ticketSeats.add(ticketSeat1);
        ticketSeats.add(ticketSeat2);
        ticketSeats.add(ticketSeat3);
        BDDMockito.given(ticketSeatRepositoryMock.findAllByScreeningId(screening.getId())).willReturn(ticketSeats);
        assertArrayEquals(ticketseatsdto.toArray(), ticketSeatService.findAllByScreeningId(screening.getId()).toArray());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).findAllByScreeningId(screening.getId());
    }

    @Test
    void findByIdAsDTO(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        TicketSeatDTO ticketSeatDTO = new TicketSeatDTO(0,1,false,null,screening.getId());
        BDDMockito.given(ticketSeatRepositoryMock.findById(ticketSeat.getId())).willReturn(Optional.of(ticketSeat));
        assertEquals(ticketSeatDTO, ticketSeatService.findByIdAsDTO(ticketSeat.getId()).get());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).findById(ticketSeat.getId());
    }

    @Test
    void create() throws Exception{
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        ReflectionTestUtils.setField(screening, "id", 1);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        ReflectionTestUtils.setField(ticketSeat, "id", 1);
        TicketSeatCreateDTO ticketSeatCreateDTO = new TicketSeatCreateDTO(1, false, null, 1);
        BDDMockito.given(ticketSeatRepositoryMock.save(any(TicketSeat.class))).willReturn(ticketSeat);
        BDDMockito.given(screeningRepository.findById(ticketSeat.getScreening().getId())).willReturn(Optional.of(screening));
        TicketSeatDTO returnedTicketSeatDTO = ticketSeatService.create(ticketSeatCreateDTO);
        TicketSeatDTO expectedTicketSeatDTO = new TicketSeatDTO(1, 1, false, null, 1);
        assertEquals(expectedTicketSeatDTO, returnedTicketSeatDTO);
        ArgumentCaptor<TicketSeat> argumentCaptor = ArgumentCaptor.forClass(TicketSeat.class);
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        TicketSeat provided = argumentCaptor.getValue();
        assertEquals(1, provided.getNumber());
        assertFalse(provided.isTaken());
        assertNull(provided.getOwner());
        assertEquals(screening, provided.getScreening());
    }

    @Test
    void update() throws Exception {
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        ReflectionTestUtils.setField(screening, "id", 1);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        ReflectionTestUtils.setField(ticketSeat, "id", 1);
        TicketSeatCreateDTO ticketSeatCreateDTO = new TicketSeatCreateDTO(1, false, null, 1);
        BDDMockito.given(ticketSeatRepositoryMock.save(any(TicketSeat.class))).willReturn(ticketSeat);
        BDDMockito.given(screeningRepository.findById(ticketSeat.getScreening().getId())).willReturn(Optional.of(screening));
        BDDMockito.given(ticketSeatRepositoryMock.findById(ticketSeat.getId())).willReturn(Optional.of(ticketSeat));
        ticketSeatService.create(ticketSeatCreateDTO);
        TicketSeatDTO expectedTicketSeatDTO = new TicketSeatDTO(1, 1, false, null, 1);
        TicketSeatCreateDTO ticketSeatCreateDTO1 = new TicketSeatCreateDTO(1, false, null, 1);
        TicketSeatDTO returnedTicketSeatDTO = ticketSeatService.update(1, ticketSeatCreateDTO1);
        assertEquals(expectedTicketSeatDTO, returnedTicketSeatDTO);
    }

    @Test
    void delete(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        BDDMockito.doNothing().when(ticketSeatRepositoryMock).delete(ticketSeat);
        ticketSeatService.delete(ticketSeat);
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).delete(ticketSeat);
    }

    @Test
    void deleteById(){
        Auditorium auditorium = new Auditorium(200);
        Movie movie = new Movie("Inception", "Christopher Nolan", 150, "PG-13", new ArrayList<>());
        Screening screening = new Screening(new Date(), true, auditorium, movie);
        TicketSeat ticketSeat = new TicketSeat(1, false, null, screening);
        BDDMockito.doNothing().when(ticketSeatRepositoryMock).deleteById(ticketSeat.getId());
        ticketSeatService.deleteById(ticketSeat.getId());
        Mockito.verify(ticketSeatRepositoryMock, Mockito.atLeastOnce()).deleteById(ticketSeat.getId());
    }
}
