package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AuditoriumController {
    private final AuditoriumService auditoriumService;

    @Autowired
    public AuditoriumController(AuditoriumService auditoriumService){
        this.auditoriumService = auditoriumService;
    }

    @GetMapping("/auditorium/all")
    List<AuditoriumDTO> all(){
        return auditoriumService.findAll();
    }

    @GetMapping("/auditorium/{id}")
    AuditoriumDTO byId(@PathVariable int id){
        return auditoriumService.findByIdAsDTO(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/auditorium")
    AuditoriumDTO save(@RequestBody AuditoriumCreateDTO auditorium){
        return auditoriumService.create(auditorium);
    }

    AuditoriumDTO save(@PathVariable int id, @RequestBody AuditoriumCreateDTO auditorium) {
        try {
            return auditoriumService.update(id, auditorium);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
