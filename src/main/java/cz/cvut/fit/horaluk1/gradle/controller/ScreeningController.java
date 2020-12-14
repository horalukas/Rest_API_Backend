package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.AuditoriumService;
import cz.cvut.fit.horaluk1.gradle.service.ScreeningService;
import cz.cvut.fit.horaluk1.gradle.service.TicketSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ScreeningController {
    private final ScreeningService screeningService;
    private final AuditoriumService auditoriumService;
    private final TicketSeatService ticketSeatService;

    @Autowired
    public ScreeningController(ScreeningService screeningService, AuditoriumService auditoriumService, TicketSeatService ticketSeatService) {
        this.screeningService = screeningService;
        this.auditoriumService = auditoriumService;
        this.ticketSeatService = ticketSeatService;
    }

    @GetMapping("/screening")
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningDTO> all(){
        return  screeningService.findAll();
    }

    @GetMapping("/screening/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ScreeningDTO byId(@PathVariable int id){
        return screeningService.findByIdAsDTO(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/screening", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningDTO> byMovieName(@RequestParam String name){
        return screeningService.findAllByMovieName(name);
    }

    @PostMapping("/screening")
    @ResponseStatus(HttpStatus.CREATED)
    public ScreeningDTO save(@RequestBody ScreeningCreateDTO screeningCreateDTO){
        ScreeningDTO screeningDTO;
        try {
            screeningDTO =  screeningService.create(screeningCreateDTO);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for(int i=1; i <= auditoriumService.findById(screeningDTO.getAuditoriumId()).get().getCapacity(); i++){
           TicketSeatCreateDTO ticketSeatCreateDTO = new TicketSeatCreateDTO(i, false, null, screeningDTO.getId());
            ticketSeatService.create(ticketSeatCreateDTO);
        }
        return screeningDTO;
    }

    @PutMapping("/screening/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ScreeningDTO update(@PathVariable int id, @RequestBody ScreeningCreateDTO screeningCreateDTO){
        try {
            return screeningService.update(id, screeningCreateDTO);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/screening/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable int id){
        screeningService.deleteById(id);
    }
}
