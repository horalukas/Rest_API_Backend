package cz.cvut.fit.horaluk1.gradle.service;

import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.repository.AuditoriumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;

    @Autowired
    public AuditoriumService(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public List<AuditoriumDTO> findAll(){
        return auditoriumRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Auditorium> findByIds(List<Integer> ids){
        return auditoriumRepository.findAllById(ids);
    }

    public Optional<Auditorium> findById(int id){
        return auditoriumRepository.findById(id);
    }

    public Optional<AuditoriumDTO> findByIdAsDTO(int id){
        return toDTO(auditoriumRepository.findById(id));
    }

    public AuditoriumDTO create(AuditoriumCreateDTO auditoriumCreateDTO){
        return toDTO(auditoriumRepository.save(new Auditorium(auditoriumCreateDTO.getCapacity())));
    }

    @Transactional
    public AuditoriumDTO update(int id, AuditoriumCreateDTO auditoriumCreateDTO) throws Exception{
        Optional<Auditorium> optionalAuditorium = auditoriumRepository.findById(id);
        if(optionalAuditorium.isEmpty())
            throw new Exception("Auditorium doesnt exist"); //placeholder exception
        Auditorium auditorium = optionalAuditorium.get();
        auditorium.setCapacity(auditoriumCreateDTO.getCapacity());
        return toDTO(auditorium);
    }

    private AuditoriumDTO toDTO(Auditorium auditorium){
        return new AuditoriumDTO(auditorium.getId(), auditorium.getCapacity());
    }

    private Optional<AuditoriumDTO> toDTO(Optional<Auditorium> auditorium){
        if(auditorium.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(auditorium.get()));
    }
}
