package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.entity.TicketSeat;
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
    private final MovieGoerService movieGoerService;
    private final ScreeningService screeningService;

    @Autowired
    public TicketSeatService(TicketSeatRepository ticketSeatRepository, MovieGoerService movieGoerService, ScreeningService screeningService) {
        this.ticketSeatRepository = ticketSeatRepository;
        this.movieGoerService = movieGoerService;
        this.screeningService = screeningService;
    }

    public List<TicketSeatDTO> findAll(){
        return ticketSeatRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<TicketSeat> findByIds(List<Long> ids){
        return ticketSeatRepository.findAllById(ids);
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

    @Transactional
    public TicketSeatDTO create(TicketSeatCreateDTO ticketSeatCreateDTO) throws Exception {
        MovieGoer movieGoer = movieGoerService.findById(ticketSeatCreateDTO.getOwnerId()) == null ? null :
                movieGoerService.findById(ticketSeatCreateDTO.getOwnerId()).orElseThrow(() -> new Exception("User doesnt exist"));
        Optional<Screening> screening = screeningService.findById(ticketSeatCreateDTO.getScreeningId());
        if(screening.isEmpty())
            throw new Exception("Screening doesnt exist"); // placeholder
        return toDTO(ticketSeatRepository.save(new TicketSeat(ticketSeatCreateDTO.getNumber(), ticketSeatCreateDTO.isTaken(), movieGoer, screening.get())));
    }

    @Transactional
    public TicketSeatDTO update(long id, TicketSeatCreateDTO ticketSeatCreateDTO) throws Exception {
        Optional<TicketSeat> optionalTicketSeat = ticketSeatRepository.findById(id);
        if(optionalTicketSeat.isEmpty())
            throw new Exception("TicketSeat doesnt exist"); //placeholder exception
        Optional<Screening> screening = screeningService.findById(ticketSeatCreateDTO.getScreeningId());
        if(screening.isEmpty())
            throw new Exception("Screening doesnt exist");
        TicketSeat ticketSeat = optionalTicketSeat.get();
        ticketSeat.setNumber(ticketSeatCreateDTO.getNumber());
        ticketSeat.setTaken(ticketSeatCreateDTO.isTaken());
        ticketSeat.setScreening(screening.get());
        ticketSeat.setOwner(ticketSeatCreateDTO.getOwnerId() == null ?
                        null :
                        movieGoerService.findById(ticketSeatCreateDTO.getOwnerId()).orElseThrow(() -> new Exception("User doesnt exist")));
        return toDTO(ticketSeat);
    }

    private TicketSeatDTO toDTO(TicketSeat ticketSeat){
        return new TicketSeatDTO(
                ticketSeat.getId(),
                ticketSeat.getNumber(),
                ticketSeat.isTaken(),
                ticketSeat.getScreening().getId(),
                ticketSeat.getOwner()!=null ? ticketSeat.getOwner().getId() : null);
    }

    private Optional<TicketSeatDTO> toDTO(Optional<TicketSeat> ticketSeat){
        if(ticketSeat.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(ticketSeat.get()));
    }
}
