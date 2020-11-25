package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.ScreeningCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.ScreeningDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;
import cz.cvut.fit.horaluk1.gradle.entity.Screening;
import cz.cvut.fit.horaluk1.gradle.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final AuditoriumService auditoriumService;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository, MovieService movieService, AuditoriumService auditoriumService) {
        this.screeningRepository = screeningRepository;
        this.movieService = movieService;
        this.auditoriumService = auditoriumService;
    }

    public List<ScreeningDTO> findAll(){
        return screeningRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Screening> findByIds(List<Integer> ids){
        return screeningRepository.findAllById(ids);
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
    public ScreeningDTO create(ScreeningCreateDTO screeningCreateDTO) throws Exception {
        Optional<Auditorium> auditorium = auditoriumService.findById(screeningCreateDTO.getAuditoriumId());
        if(auditorium.isEmpty())
            throw new Exception("Auditorium doesnt exist"); // placeholder
        Optional<Movie> movie = movieService.findById(screeningCreateDTO.getMovieId());
        if(movie.isEmpty())
            throw new Exception("Movie doesnt exist"); // placeholder
        return toDTO(screeningRepository.save(new Screening(screeningCreateDTO.getTime(), screeningCreateDTO.is_3D(), auditorium.get(), movie.get())));
    }

    @Transactional
    public ScreeningDTO update(int id, ScreeningCreateDTO screeningCreateDTO) throws Exception {
        Optional<Screening> optionalScreening = screeningRepository.findById(id);
        if(optionalScreening.isEmpty())
            throw new Exception("Screening doesnt exist"); //placeholder exception
        Optional<Auditorium> auditorium = auditoriumService.findById(screeningCreateDTO.getAuditoriumId());
        if(auditorium.isEmpty())
            throw new Exception("Auditorium doesnt exist");
        Optional<Movie> movie= movieService.findById(screeningCreateDTO.getMovieId());
        if(movie.isEmpty())
            throw new Exception("Movie doesnt exist");
        Screening screening = optionalScreening.get();
        screening.set_3D(screeningCreateDTO.is_3D());
        screening.setTime(screeningCreateDTO.getTime());
        screening.setAuditorium(auditorium.get());
        screening.setMovie(movie.get());
        return toDTO(screening);
    }

    private ScreeningDTO toDTO(Screening screening){
        return new ScreeningDTO(screening.getId(), screening.getTime(), screening.is_3D(), screening.getAuditorium().getId(), screening.getMovie().getId());
    }

    private Optional<ScreeningDTO> toDTO(Optional<Screening> screening){
        if(screening.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(screening.get()));
    }
}
