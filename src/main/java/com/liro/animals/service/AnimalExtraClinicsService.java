package com.liro.animals.service;

import com.liro.animals.dto.AnimalExtraClinicDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalExtraClinicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalExtraClinicsService {

    AnimalExtraClinicResponse addExtraClinic(AnimalExtraClinicDTO animalExtraClinicDTO, UserDTO userDTO);

    void addClinic(AnimalExtraClinicDTO animalExtraClinicDTO, UserDTO userDTO);

    Page<AnimalExtraClinicResponse> getExtraClinicsRelationsByClinicId(Pageable pageable, Long clinicId, UserDTO userDTO);

    Page<AnimalExtraClinicResponse> getExtraClinicsRelationsByAnimalId(Pageable pageable,Long animalId, UserDTO userDTO);

}
