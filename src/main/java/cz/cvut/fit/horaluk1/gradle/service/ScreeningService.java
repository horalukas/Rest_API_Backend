package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.exception.NotFoundException;
import cz.cvut.fit.horaluk1.gradle.repository.AuditoriumRepository;
import cz.cvut.fit.horaluk1.gradle.repository.MovieRepository;
import cz.cvut.fit.horaluk1.gradle.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final AuditoriumRepository auditoriumRepository;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository, MovieRepository movieRepository, AuditoriumRepository auditoriumRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.auditoriumRepository = auditoriumRepository;
    }

    public List<ScreeningDTO> findAll(){
        return screeningRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<Screening> findById(int id){
        return screeningRepository.findById(id);
    }

    public Optional<ScreeningDTO> findByIdAsDTO(int id){
        return toDTO(screeningRepository.findById(id));
    }

    public List<ScreeningDTO> findAllByMovieName(String name){
        return screeningRepository.findAllByMovieName(name).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ScreeningDTO create(ScreeningCreateDTO screeningCreateDTO) throws IllegalArgumentException {
        Optional<Auditorium> auditorium = auditoriumRepository.findById(screeningCreateDTO.getAuditoriumId());
        if(auditorium.isEmpty())
            throw new IllegalArgumentException();
        Optional<Movie> movie = movieRepository.findById(screeningCreateDTO.getMovieId());
        if(movie.isEmpty())
            throw new IllegalArgumentException();
        return toDTO(screeningRepository.save(new Screening(screeningCreateDTO.getTime(), screeningCreateDTO.is_3D(), auditorium.get(), movie.get())));
    }

    @Transactional
    public ScreeningDTO update(int id, ScreeningCreateDTO screeningCreateDTO) throws IllegalArgumentException {
        Optional<Screening> optionalScreening = screeningRepository.findById(id);
        if(optionalScreening.isEmpty())
            throw new NotFoundException();
        Optional<Auditorium> auditorium = auditoriumRepository.findById(screeningCreateDTO.getAuditoriumId());
        if(auditorium.isEmpty())
            throw new IllegalArgumentException("Auditorium doesnt exist");
        Optional<Movie> movie= movieRepository.findById(screeningCreateDTO.getMovieId());
        if(movie.isEmpty())
            throw new IllegalArgumentException("Movie doesnt exist");
        Screening screening = optionalScreening.get();
        screening.set_3D(screeningCreateDTO.is_3D());
        screening.setTime(screeningCreateDTO.getTime());
        screening.setAuditorium(auditorium.get());
        screening.setMovie(movie.get());
        return toDTO(screening);
    }

    @Transactional
    public void delete(Screening screening){screeningRepository.delete(screening);}

    @Transactional
    public void deleteById(int id){screeningRepository.deleteById(id);}

    /*@Transactional
    @Scheduled()
    public void deleteAuto(){
        List<ScreeningDTO> screeningDTOS = findAll();
        for(ScreeningDTO dto : screeningDTOS){
            long beginning = dto.getTime().getTime();
            long ending = new Date(beginning + (movieRepository.findById(dto.getMovieId()).get().getMinutes() * 60000)).getTime();
            long now = new Date().getTime();
            if(ending < now){
                deleteById(dto.getId());
            }
        }
    }*/

    private ScreeningDTO toDTO(Screening screening){
        return new ScreeningDTO(screening.getId(), screening.getTime(), screening.is_3D(), screening.getAuditorium().getId(), screening.getMovie().getId());
    }

    private Optional<ScreeningDTO> toDTO(Optional<Screening> screening){
        if(screening.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(screening.get()));
    }
}
