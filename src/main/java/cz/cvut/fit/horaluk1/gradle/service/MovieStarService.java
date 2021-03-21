package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.MovieStarCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
import cz.cvut.fit.horaluk1.gradle.exception.ExistingEntityException;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.repository.MovieStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieStarService {

    private final MovieStarRepository movieStarRepository;

    @Autowired
    public MovieStarService(MovieStarRepository movieStarRepository) {
        this.movieStarRepository = movieStarRepository;
    }

    public List<MovieStarDTO> findAll(){
        return movieStarRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MovieStarDTO> findByIds(List<Integer> ids){
        return movieStarRepository.findAllById(ids).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<MovieStar> findById(int id){
        return movieStarRepository.findById(id);
    }

    public Optional<MovieStarDTO> findByIdAsDTO(int id){
        return toDTO(movieStarRepository.findById(id));
    }

    public Optional<MovieStarDTO> findAllByFirstNameAndLastName(String firstName, String lastName){
        return toDTO(movieStarRepository.findAllByFirstNameAndLastName(firstName, lastName));
    }

    @Transactional
    public MovieStarDTO create(MovieStarCreateDTO movieStarCreateDTO) throws ExistingEntityException{
        Optional<MovieStar> optionalMovieStar = movieStarRepository.findAllByFirstNameAndLastName(movieStarCreateDTO.getFirstName(), movieStarCreateDTO.getLastName());
        if(optionalMovieStar.isPresent())
            throw new ExistingEntityException();
        return toDTO(
                movieStarRepository.save(
                        new MovieStar(movieStarCreateDTO.getFirstName(),
                                movieStarCreateDTO.getLastName())
                )
        );
    }

    @Transactional
    public MovieStarDTO update(int id, MovieStarCreateDTO movieStarCreateDTO) throws NotFoundException{
        Optional<MovieStar> optionalStar = findById(id);
        if(optionalStar.isEmpty())
            throw new NotFoundException();
        MovieStar star = optionalStar.get();
        star.setFirstName(movieStarCreateDTO.getFirstName());
        star.setLastName(movieStarCreateDTO.getLastName());
        return toDTO(star);
    }

    private MovieStarDTO toDTO(MovieStar movieStar){
        return new MovieStarDTO(
                movieStar.getId(),
                movieStar.getFirstName(),
                movieStar.getLastName());
    }

    private Optional<MovieStarDTO> toDTO(Optional<MovieStar> movieStar){
        if(movieStar.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(movieStar.get()));
    }

}
