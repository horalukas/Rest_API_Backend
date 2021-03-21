package cz.cvut.fit.horaluk1.gradle.controller;

import cz.cvut.fit.horaluk1.gradle.dto.MovieStarCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarDTO;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.service.MovieStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MovieStarController {
    private final MovieStarService movieStarService;

    @Autowired
    public MovieStarController(MovieStarService movieStarService) {
        this.movieStarService = movieStarService;
    }

    @GetMapping("/moviestar/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieStarDTO> all(){
        return movieStarService.findAll();
    }

    @GetMapping("/moviestar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieStarDTO byId(@PathVariable int id) {
        return movieStarService.findByIdAsDTO(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/moviestar/ids", params = {"ids"})
    @ResponseStatus(HttpStatus.OK)
    public List<MovieStarDTO> allById(@RequestParam List<Integer> ids){
        return movieStarService.findByIds(ids);
    }

    @GetMapping(value = "/moviestar", params = {"first", "last"})
    @ResponseStatus(HttpStatus.OK)
    public MovieStarDTO allByName(@RequestParam String first, @RequestParam String last) {
        return movieStarService.findAllByFirstNameAndLastName(first, last).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/moviestar")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieStarDTO save(@RequestBody MovieStarCreateDTO movieStar){
        try {
            return movieStarService.create(movieStar);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/moviestar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MovieStarDTO update(@PathVariable int id, @RequestBody MovieStarCreateDTO movieStarCreateDTO){
        try {
            return movieStarService.update(id, movieStarCreateDTO);
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
