package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.repository.MovieGoerRepository;
import cz.cvut.fit.horaluk1.gradle.repository.ScreeningRepository;
import cz.cvut.fit.horaluk1.gradle.repository.TicketSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketSeatService {

    private final TicketSeatRepository ticketSeatRepository;
    private final MovieGoerRepository movieGoerRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public TicketSeatService(TicketSeatRepository ticketSeatRepository, MovieGoerRepository movieGoerRepository, ScreeningRepository screeningRepository) {
        this.ticketSeatRepository = ticketSeatRepository;
        this.movieGoerRepository = movieGoerRepository;
        this.screeningRepository = screeningRepository;
    }

    public List<TicketSeatDTO> findAll(){
        return ticketSeatRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<TicketSeat> findById(long id){
        return ticketSeatRepository.findById(id);
    }

    public Optional<TicketSeatDTO> findByIdAsDTO(long id){
        return toDTO(ticketSeatRepository.findById(id));
    }

    public List<TicketSeatDTO> findAllByOwnerEmail(String email){
        return ticketSeatRepository.findAllByOwnerEmail(email).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<TicketSeatDTO> findAllByScreeningId(int id){
        return ticketSeatRepository.findAllByScreeningId(id).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public TicketSeatDTO create(TicketSeatCreateDTO ticketSeatCreateDTO) throws Exception {
        MovieGoer movieGoer = ticketSeatCreateDTO.getOwnerId() == null  ? null :
                movieGoerRepository.findById(ticketSeatCreateDTO.getOwnerId()).orElseThrow(() -> new Exception("User doesnt exist"));
        Optional<Screening> screening = screeningRepository.findById(ticketSeatCreateDTO.getScreeningId());
        if(screening.isEmpty())
            throw new IllegalArgumentException("Screening doesnt exist");
        return toDTO(ticketSeatRepository.save(new TicketSeat(ticketSeatCreateDTO.getNumber(), ticketSeatCreateDTO.isTaken(), movieGoer, screening.get())));
    }

    @Transactional
    public TicketSeatDTO update(long id, TicketSeatCreateDTO ticketSeatCreateDTO) throws Exception {
        Optional<TicketSeat> optionalTicketSeat = ticketSeatRepository.findById(id);
        if(optionalTicketSeat.isEmpty())
            throw new NotFoundException();
        Optional<Screening> screening = screeningRepository.findById(ticketSeatCreateDTO.getScreeningId());
        if(screening.isEmpty())
            throw new IllegalArgumentException("Screening doesnt exist");
        TicketSeat ticketSeat = optionalTicketSeat.get();
        ticketSeat.setNumber(ticketSeatCreateDTO.getNumber());
        ticketSeat.setTaken(ticketSeatCreateDTO.isTaken());
        ticketSeat.setScreening(screening.get());
        ticketSeat.setOwner(ticketSeatCreateDTO.getOwnerId() == null ?
                        null :
                        movieGoerRepository.findById(ticketSeatCreateDTO.getOwnerId()).orElseThrow(() -> new Exception("User doesnt exist")));
        return toDTO(ticketSeat);
    }
    @Transactional
    public void delete(TicketSeat ticketSeat){ticketSeatRepository.delete(ticketSeat);}

    @Transactional
    public void deleteById(long id){ticketSeatRepository.deleteById(id);}


    private TicketSeatDTO toDTO(TicketSeat ticketSeat){
        return new TicketSeatDTO(
                ticketSeat.getId(),
                ticketSeat.getNumber(),
                ticketSeat.isTaken(),
                ticketSeat.getOwner()!=null ? ticketSeat.getOwner().getId() : null,
                ticketSeat.getScreening().getId());
    }

    private Optional<TicketSeatDTO> toDTO(Optional<TicketSeat> ticketSeat){
        if(ticketSeat.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(ticketSeat.get()));
    }
}
