package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.MovieCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
import cz.cvut.fit.horaluk1.gradle.exception.ExistingEntityException;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.repository.MovieRepository;
import cz.cvut.fit.horaluk1.gradle.repository.MovieStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieStarRepository movieStarRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieStarRepository movieStarRepository) {
        this.movieRepository = movieRepository;
        this.movieStarRepository = movieStarRepository;
    }

    public List<MovieDTO> findAll(){
        return movieRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<Movie> findById(int id){
        return movieRepository.findById(id);
    }

    public Optional<MovieDTO> findByIdAsDTO(int id){
        return toDTO(movieRepository.findById(id));
    }

    public Optional<MovieDTO> findByName(String name){
        return toDTO(movieRepository.findByName(name));
    }

    public List<MovieDTO> findAllByDirector(String director){
        return movieRepository.findAllByDirector(director).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MovieDTO> findAllByRating(String rating){
        return movieRepository.findAllByRating(rating).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MovieDTO create(MovieCreateDTO movieCreateDTO) throws Exception{
        Optional<Movie> optionalMovie = movieRepository.findByName(movieCreateDTO.getName());
        if(!optionalMovie.isEmpty())
            throw new ExistingEntityException();
        List<MovieStar> stars = movieStarRepository.findAllById(movieCreateDTO.getStarIds());
        if(stars.size() != movieCreateDTO.getStarIds().size())
            throw new IllegalArgumentException("Some stars not found");
        return toDTO(
                movieRepository.save(
                        new Movie(movieCreateDTO.getName(),
                                movieCreateDTO.getDirector(),
                                movieCreateDTO.getMinutes(),
                                movieCreateDTO.getRating(),
                                stars)
                )
        );
    }

    @Transactional
    public MovieDTO update(int id, MovieCreateDTO movieCreateDTO) throws Exception{
        Optional<Movie> optionalMovie = findById(id);
        if(optionalMovie.isEmpty())
            throw new NotFoundException();
        Movie movie = optionalMovie.get();
        List<MovieStar> stars = movieStarRepository.findAllById(movieCreateDTO.getStarIds());
        if(stars.size() != movieCreateDTO.getStarIds().size())
            throw new IllegalArgumentException("some stars not found");
        movie.setName(movieCreateDTO.getName());
        movie.setDirector(movieCreateDTO.getDirector());
        movie.setMinutes(movieCreateDTO.getMinutes());
        movie.setRating(movieCreateDTO.getRating());
        movie.setStars(stars);

        return toDTO(movie);
    }

    private MovieDTO toDTO(Movie movie){
        return new MovieDTO(
                movie.getId(),
                movie.getName(),
                movie.getDirector(),
                movie.getMinutes(),
                movie.getRating(),
                movie.getStars().stream().map(MovieStar::getId).collect(Collectors.toList())
        );
    }

    private Optional<MovieDTO> toDTO(Optional<Movie> movie){
        if(movie.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(movie.get()));
    }
}
