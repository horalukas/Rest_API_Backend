package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.TicketSeatDTO;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.TicketSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TicketSeatController {
    private final TicketSeatService ticketSeatService;

    @Autowired
    public TicketSeatController(TicketSeatService ticketSeatService) {
        this.ticketSeatService = ticketSeatService;
    }

    @GetMapping("/ticketseat")
    @ResponseStatus(HttpStatus.OK)
    List<TicketSeatDTO> all(){
        return  ticketSeatService.findAll();
    }

    @GetMapping("/ticketseat/{id}")
    @ResponseStatus(HttpStatus.OK)
    TicketSeatDTO byId(@PathVariable long id){
        return ticketSeatService.findByIdAsDTO(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/ticketseat", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    List<TicketSeatDTO> byOwnerEmail(@RequestParam String email){
        return ticketSeatService.findAllByOwnerEmail(email);
    }

    @GetMapping(value = "/ticketseat", params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    List<TicketSeatDTO> byScreeningId(@RequestParam int id){
        return ticketSeatService.findAllByScreeningId(id);
    }

    @PostMapping("/ticketseat")
    @ResponseStatus(HttpStatus.CREATED)
    TicketSeatDTO save(@RequestBody TicketSeatCreateDTO ticketSeatCreateDTO){
        try {
            return ticketSeatService.create(ticketSeatCreateDTO);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/ticketseat/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    TicketSeatDTO save(@PathVariable long id, @RequestBody TicketSeatCreateDTO ticketSeatCreateDTO){
        try {
            return ticketSeatService.update(id, ticketSeatCreateDTO);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/ticketseat/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable long id){
        ticketSeatService.deleteById(id);
    }
}
