package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ScreeningController {
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping("/screening")
    @ResponseStatus(HttpStatus.OK)
    List<ScreeningDTO> all(){
        return  screeningService.findAll();
    }

    @GetMapping("/screening/{id}")
    @ResponseStatus(HttpStatus.OK)
    ScreeningDTO byId(@PathVariable int id){
        return screeningService.findByIdAsDTO(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/screening", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    List<ScreeningDTO> byMovieName(@RequestParam String name){
        return screeningService.findAllByMovieName(name);
    }

    @PostMapping("/screening")
    @ResponseStatus(HttpStatus.CREATED)
    ScreeningDTO save(@RequestBody ScreeningCreateDTO screeningCreateDTO){
        try {
            return screeningService.create(screeningCreateDTO);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/screening/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ScreeningDTO save(@PathVariable int id, @RequestBody ScreeningCreateDTO screeningCreateDTO){
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
    void deleteById(@PathVariable int id){
        screeningService.deleteById(id);
    }
}
