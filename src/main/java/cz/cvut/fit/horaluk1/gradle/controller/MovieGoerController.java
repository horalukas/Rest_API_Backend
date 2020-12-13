package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.exception.ExistingEntityException;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.MovieGoerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MovieGoerController {
    private final MovieGoerService movieGoerService;

    @Autowired
    public MovieGoerController(MovieGoerService movieGoerService) {
        this.movieGoerService = movieGoerService;
    }

    @GetMapping("/moviegoer/all")
    @ResponseStatus(HttpStatus.OK)
    List<MovieGoerDTO> all(){
        return movieGoerService.findAll();
    }

    @GetMapping("/moviegoer/{id}")
    @ResponseStatus(HttpStatus.OK)
    MovieGoerDTO byId(@PathVariable int id) {
        return movieGoerService.findByIdAsDTO(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/moviegoer", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    MovieGoerDTO byEmail(@RequestParam String email){
        return movieGoerService.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/moviegoer")
    @ResponseStatus(HttpStatus.CREATED)
    MovieGoerDTO save(@RequestBody MovieGoerCreateDTO movieGoer){
        try {
            return movieGoerService.create(movieGoer);
        }catch(ExistingEntityException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/moviegoer/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    MovieGoerDTO save(@PathVariable int id, @RequestBody MovieGoerCreateDTO movieGoerCreateDTO){
        try {
            return movieGoerService.update(id, movieGoerCreateDTO);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/moviegoer/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable int id){
        movieGoerService.deleteById(id);
    }

    @DeleteMapping(value = "/moviegoer", params = {"email"})
    @ResponseStatus(HttpStatus.OK)
    void deleteByEmail(@RequestParam String email){
        movieGoerService.deleteByEmail(email);
    }
}
