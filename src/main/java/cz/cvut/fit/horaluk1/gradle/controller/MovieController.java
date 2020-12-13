package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.MovieCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerDTO;
import cz.cvut.fit.horaluk1.gradle.exception.ExistingEntityException;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MovieController {
    private final MovieService movieService;

   @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movie/all")
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTO> all(){
        return movieService.findAll();
    }

    @GetMapping("/movie/{id}")
    @ResponseStatus(HttpStatus.OK)
    MovieDTO byId(@PathVariable int id) {
        return movieService.findByIdAsDTO(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/movie", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    MovieDTO byName(@RequestParam String name){
        return movieService.findByName(name).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/movie", params = {"director"})
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTO> allByDirector(@RequestParam String director) {
        return movieService.findAllByDirector(director);
    }

    @GetMapping(value = "/movie", params = {"rating"})
    @ResponseStatus(HttpStatus.OK)
    List<MovieDTO> allByRating(@RequestParam String rating) {
        return movieService.findAllByRating(rating);
    }

    @PostMapping("/movie")
    @ResponseStatus(HttpStatus.CREATED)
    MovieDTO save(@RequestBody MovieCreateDTO movie){
        try {
            return movieService.create(movie);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/movie/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    MovieDTO save(@PathVariable int id, @RequestBody MovieCreateDTO movieCreateDTO){
        try {
            return movieService.update(id, movieCreateDTO);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
