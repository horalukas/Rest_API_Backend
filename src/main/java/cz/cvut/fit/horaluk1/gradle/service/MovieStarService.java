package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.MovieStarCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieStarDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.MovieStar;
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
    private final MovieService movieService;

    @Autowired
    public MovieStarService(MovieStarRepository movieStarRepository, MovieService movieService) {
        this.movieStarRepository = movieStarRepository;
        this.movieService = movieService;
    }

    public List<MovieStarDTO> findAll(){
        return movieStarRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MovieStar> findByIds(List<Integer> ids){
        return movieStarRepository.findAllById(ids);
    }

    public Optional<MovieStar> findById(int id){
        return movieStarRepository.findById(id);
    }

    public Optional<MovieStarDTO> findByIdAsDTO(int id){
        return toDTO(movieStarRepository.findById(id));
    }

    public List<MovieStarDTO> findAllByLastName(String name){
        return movieStarRepository.findAllByLastName(name).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MovieStarDTO create(MovieStarCreateDTO movieStarCreateDTO) throws Exception{
        List<Movie> movies = movieService.findByIds(movieStarCreateDTO.getMovieIds());
        if(movies.size() != movieStarCreateDTO.getMovieIds().size())
            throw new Exception("some movies not found"); // placeholder
        return toDTO(
                movieStarRepository.save(
                        new MovieStar(movieStarCreateDTO.getFirstName(),
                                movieStarCreateDTO.getSecondName(),
                                movies)
                )
        );
    }

    @Transactional
    public MovieStarDTO update(int id, MovieStarCreateDTO movieStarCreateDTO) throws Exception{
        Optional<MovieStar> optionalStar = findById(id);
        if(optionalStar.isEmpty())
            throw new Exception("Star doesnt exist");//placeholder
        MovieStar star = optionalStar.get();
        List<Movie> movies = movieService.findByIds(movieStarCreateDTO.getMovieIds());
        if(movies.size() != movieStarCreateDTO.getMovieIds().size())
            throw new Exception("some movies not found"); // placeholder
        star.setFirstName(movieStarCreateDTO.getFirstName());
        star.setLastName(movieStarCreateDTO.getSecondName());
        star.setMovies(movies);
        return toDTO(star);
    }

    private MovieStarDTO toDTO(MovieStar movieStar){
        return new MovieStarDTO(
                movieStar.getId(),
                movieStar.getFirstName(),
                movieStar.getLastName(),
                movieStar.getMovies().stream().map(Movie::getId).collect(Collectors.toList()));
    }

    private Optional<MovieStarDTO> toDTO(Optional<MovieStar> movieStar){
        if(movieStar.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(movieStar.get()));
    }
}
