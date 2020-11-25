package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.MovieGoerDTO;
import cz.cvut.fit.horaluk1.gradle.entity.MovieGoer;
import cz.cvut.fit.horaluk1.gradle.repository.MovieGoerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieGoerService {

    private final MovieGoerRepository movieGoerRepository;

    @Autowired
    public MovieGoerService(MovieGoerRepository movieGoerRepository) {
        this.movieGoerRepository = movieGoerRepository;
    }

    public List<MovieGoerDTO> findAll(){
        return movieGoerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MovieGoer> findByIds(List<Integer> ids){
        return movieGoerRepository.findAllById(ids);
    }

    public Optional<MovieGoer> findById(int id){
        return movieGoerRepository.findById(id);
    }

    public Optional<MovieGoerDTO> findByIdAsDTO(int id){
        return toDTO(movieGoerRepository.findById(id));
    }

    public Optional<MovieGoerDTO> findByEmail(String email){
        return toDTO(movieGoerRepository.findByEmail(email));
    }

    public MovieGoerDTO create(MovieGoerCreateDTO movieGoerCreateDTO){
        return toDTO(movieGoerRepository.save(new MovieGoer(movieGoerCreateDTO.getEmail(), movieGoerCreateDTO.getPassword())));
    }

    @Transactional
    public MovieGoerDTO update(int id, MovieGoerCreateDTO movieGoerCreateDTO)throws Exception{
        Optional<MovieGoer> optionalMovieGoer = movieGoerRepository.findById(id);
        if(optionalMovieGoer.isEmpty())
            throw new Exception("MovieGoer doesnt exist"); //placeholder exception
        MovieGoer movieGoer = optionalMovieGoer.get();
        movieGoer.setEmail(movieGoerCreateDTO.getEmail());
        movieGoer.setPassword(movieGoerCreateDTO.getPassword());
        return toDTO(movieGoer);
    }

    private MovieGoerDTO toDTO(MovieGoer movieGoer){
        return new MovieGoerDTO(movieGoer.getId(), movieGoer.getEmail(), movieGoer.getPassword());
    }

    private Optional<MovieGoerDTO> toDTO(Optional<MovieGoer> movieGoer){
        if(movieGoer.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(movieGoer.get()));
    }
}
