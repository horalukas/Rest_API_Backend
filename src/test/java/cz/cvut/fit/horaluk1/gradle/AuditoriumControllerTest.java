package cz.cvut.fit.horaluk1.gradle;

import cz.cvut.fit.horaluk1.gradle.controller.AuditoriumController;
import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumCreateDTO;
import cz.cvut.fit.horaluk1.gradle.dto.AuditoriumDTO;
import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
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
class AuditoriumControllerTest {
    @Autowired
    private AuditoriumController auditoriumController;

    @MockBean
    private AuditoriumService auditoriumService;

    @Test
    void all(){
        AuditoriumDTO auditorium1dto = new AuditoriumDTO(1, 100);
        AuditoriumDTO auditorium2dto = new AuditoriumDTO(2, 200);
        AuditoriumDTO auditorium3dto = new AuditoriumDTO(3, 300);
        List<AuditoriumDTO> auditoriumsdto = new ArrayList<>();
        auditoriumsdto.add(auditorium1dto);
        auditoriumsdto.add(auditorium2dto);
        auditoriumsdto.add(auditorium3dto);
        BDDMockito.given(auditoriumService.findAll()).willReturn(auditoriumsdto);
        assertArrayEquals(auditoriumsdto.toArray(), auditoriumController.all().toArray());
        Mockito.verify(auditoriumService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId(){
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO(1, 200);
        BDDMockito.given(auditoriumService.findByIdAsDTO(auditoriumDTO.getId())).willReturn(Optional.of(auditoriumDTO));
        assertEquals(auditoriumDTO, auditoriumController.byId(auditoriumDTO.getId()));
        Mockito.verify(auditoriumService, Mockito.atLeastOnce()).findByIdAsDTO(auditoriumDTO.getId());
    }

    @Test
    void save(){
        AuditoriumDTO auditoriumDTO = new AuditoriumDTO(1, 200);
        AuditoriumCreateDTO auditoriumCreateDTO = new AuditoriumCreateDTO(200);
        BDDMockito.given(auditoriumService.create(any(AuditoriumCreateDTO.class))).willReturn(auditoriumDTO);
        AuditoriumDTO returnedDTO = auditoriumController.save(auditoriumCreateDTO);
        AuditoriumDTO expectedDTO = new AuditoriumDTO(1, 200);
        assertEquals(returnedDTO, expectedDTO);
        ArgumentCaptor<AuditoriumCreateDTO> argumentCaptor = ArgumentCaptor.forClass(AuditoriumCreateDTO.class);
        Mockito.verify(auditoriumService, Mockito.atLeastOnce()).create(argumentCaptor.capture());
        AuditoriumCreateDTO provided = argumentCaptor.getValue();
        assertEquals(200, provided.getCapacity());
    }

    @Test
    void update(){
        Auditorium auditoriumToUpdate = new Auditorium(200);
        ReflectionTestUtils.setField(auditoriumToUpdate, "id", 1);
        AuditoriumCreateDTO auditoriumCreateDTO = new AuditoriumCreateDTO(200);
        AuditoriumCreateDTO auditoriumCreateDTOnew = new AuditoriumCreateDTO(300);
        AuditoriumDTO returnDTO = new AuditoriumDTO(1,300);
        BDDMockito.given(auditoriumService.findById(auditoriumToUpdate.getId())).willReturn(Optional.of(auditoriumToUpdate));
        BDDMockito.given(auditoriumService.update(auditoriumToUpdate.getId(), auditoriumCreateDTOnew)).willReturn(returnDTO);
        auditoriumController.save(auditoriumCreateDTO);
        AuditoriumDTO returnedDTO = auditoriumController.update(1, auditoriumCreateDTOnew);
        AuditoriumDTO expectedDTO = new AuditoriumDTO(1,300);
        assertEquals(expectedDTO, returnedDTO);
    }
}
