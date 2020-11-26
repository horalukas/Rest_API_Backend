package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.repository.AuditoriumRepository;
import cz.cvut.fit.horaluk1.gradle.service.AuditoriumService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class AuditoriumServiceTest {

    @Autowired
    private AuditoriumService auditoriumService;

    @MockBean
    private AuditoriumRepository auditoriumRepositoryMock;


    @Test
    void findAll(){
        AuditoriumDTO auditorium1dto = new AuditoriumDTO(0, 100);
        AuditoriumDTO auditorium2dto = new AuditoriumDTO(0, 200);
        AuditoriumDTO auditorium3dto = new AuditoriumDTO(0, 300);
        List<AuditoriumDTO> auditoriumsdto = new ArrayList<>();
        auditoriumsdto.add(auditorium1dto);
        auditoriumsdto.add(auditorium2dto);
        auditoriumsdto.add(auditorium3dto);
        Auditorium auditorium1 = new Auditorium(100);
        Auditorium auditorium2 = new Auditorium(200);
        Auditorium auditorium3 = new Auditorium(300);
        List<Auditorium> auditoriums = new ArrayList<>();
        auditoriums.add(auditorium1);
        auditoriums.add(auditorium2);
        auditoriums.add(auditorium3);
        BDDMockito.given(auditoriumRepositoryMock.findAll()).willReturn(auditoriums);
        assertArrayEquals(auditoriumsdto.toArray(), auditoriumService.findAll().toArray());
        Mockito.verify(auditoriumRepositoryMock, Mockito.atLeastOnce()).findAll();
    }


    @Test
    void findById(){
        Auditorium auditorium = new Auditorium(100);
        BDDMockito.given(auditoriumRepositoryMock.findById(auditorium.getId())).willReturn(Optional.of(auditorium));
        assertEquals(auditorium, auditoriumService.findById(auditorium.getId()).get());
        Mockito.verify(auditoriumRepositoryMock, Mockito.atLeastOnce()).findById(auditorium.getId());
    }

    @Test
    void findByIdAsDTO(){
        Auditorium auditorium = new Auditorium(100);
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO(0, 100);
        BDDMockito.given(auditoriumRepositoryMock.findById(auditorium.getId())).willReturn(Optional.of(auditorium));
        AuditoriumDTO returnsdto = auditoriumService.findByIdAsDTO(auditorium.getId()).get();
        assertEquals(auditoriumDTO, auditoriumService.findByIdAsDTO(auditorium.getId()).get());
        Mockito.verify(auditoriumRepositoryMock, Mockito.atLeastOnce()).findById(auditorium.getId());
    }

    @Test
    void create(){
        Auditorium auditoriumToReturn = new Auditorium(200);
        ReflectionTestUtils.setField(auditoriumToReturn, "id", 1);
        AuditoriumCreateDTO auditoriumCreateDTO = new AuditoriumCreateDTO(200);
        BDDMockito.given(auditoriumRepositoryMock.save(any(Auditorium.class))).willReturn(auditoriumToReturn);
        AuditoriumDTO returnedDTO = auditoriumService.create(auditoriumCreateDTO);
        AuditoriumDTO expectedDTO = new AuditoriumDTO(1,200);
        assertEquals(expectedDTO, returnedDTO);
        ArgumentCaptor<Auditorium> argumentCaptor = ArgumentCaptor.forClass(Auditorium.class);
        Mockito.verify(auditoriumRepositoryMock, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        Auditorium providedToSave = argumentCaptor.getValue();
        assertEquals(200, providedToSave.getCapacity());
    }

    @Test
    void update() throws Exception {
        Auditorium auditoriumToUpdate = new Auditorium(200);
        ReflectionTestUtils.setField(auditoriumToUpdate, "id", 1);
        AuditoriumCreateDTO auditoriumCreateDTO = new AuditoriumCreateDTO(200);
        BDDMockito.given(auditoriumRepositoryMock.save(any(Auditorium.class))).willReturn(auditoriumToUpdate);
        BDDMockito.given(auditoriumRepositoryMock.findById(auditoriumToUpdate.getId())).willReturn(Optional.of(auditoriumToUpdate));
        auditoriumService.create(auditoriumCreateDTO);
        AuditoriumCreateDTO auditoriumCreateDTOnew = new AuditoriumCreateDTO(300);
        AuditoriumDTO returnedDTO = auditoriumService.update(1, auditoriumCreateDTOnew);
        AuditoriumDTO expectedDTO = new AuditoriumDTO(1,300);
        assertEquals(expectedDTO, returnedDTO);

    }

}
