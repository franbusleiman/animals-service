package com.liro.animals.service;

import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalExtraClinicsService {

//    AnimalClinicResponse addExtraClinic(AnimalClinicDTO animalClinicDTO, UserDTO userDTO);

    void addClinic(AnimalClinicDTO animalClinicDTO, String token, Long clinicId);

    Page<AnimalClinicResponse> getExtraClinicsRelationsByClinicId(Pageable pageable, Long clinicId, UserDTO userDTO);

    Page<AnimalClinicResponse> getExtraClinicsRelationsByAnimalId(Pageable pageable, Long animalId, UserDTO userDTO);

}
