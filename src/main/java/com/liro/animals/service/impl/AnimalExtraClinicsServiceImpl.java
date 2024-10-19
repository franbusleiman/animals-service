package com.liro.animals.service.impl;


import com.liro.animals.dto.AnimalExtraClinicDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.mappers.AnimalExtraClinicMapper;
import com.liro.animals.dto.responses.AnimalExtraClinicResponse;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import com.liro.animals.repositories.AnimalExtraClinicsRepository;
import com.liro.animals.service.AnimalExtraClinicsService;
import com.liro.animals.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.liro.animals.util.Util.*;

import java.util.List;

@Service
public class AnimalExtraClinicsServiceImpl implements AnimalExtraClinicsService {


    private AnimalExtraClinicsRepository animalExtraClinicsRepository;
    private AnimalExtraClinicMapper animalExtraClinicMapper;
    private Util util;


    public AnimalExtraClinicsServiceImpl(AnimalExtraClinicsRepository animalExtraClinicsRepository, AnimalExtraClinicMapper animalExtraClinicMapper,
                                         Util util) {
        this.animalExtraClinicsRepository = animalExtraClinicsRepository;
        this.animalExtraClinicMapper = animalExtraClinicMapper;
        this.util = util;
    }

    @Override
    public AnimalExtraClinicResponse addExtraClinic(AnimalExtraClinicDTO animalExtraClinicDTO, UserDTO userDTO) {


        AnimalsExtraClinics animalsExtraClinics = animalExtraClinicMapper.animalExtraClinicDTOTOAnimalExtrClinic(animalExtraClinicDTO);

        util.validatePermissions(animalExtraClinicDTO.getAnimalId(), userDTO,
                true, false, false);

        return animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalExtraClinicsRepository.save(animalsExtraClinics));
    }

    @Override
    public Page<AnimalExtraClinicResponse> getExtraClinicsRelationsByClinicId(Pageable pageable, Long clinicId, UserDTO userDTO) {
        return animalExtraClinicsRepository.findAllByClinicId(pageable, clinicId)
                .map(animalsExtraClinics -> animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalsExtraClinics));
    }

    @Override
    public Page<AnimalExtraClinicResponse> getExtraClinicsRelationsByAnimalId(Pageable pageable,Long animalId, UserDTO userDTO) {
        return animalExtraClinicsRepository.findAllByAnimalId(pageable, animalId)
                .map(animalsExtraClinics -> animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalsExtraClinics));    }

}
