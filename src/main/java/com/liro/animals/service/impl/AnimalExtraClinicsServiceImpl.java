package com.liro.animals.service.impl;


import com.liro.animals.config.FeignClinicClientClient;
import com.liro.animals.dto.AnimalExtraClinicDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.mappers.AnimalExtraClinicMapper;
import com.liro.animals.dto.requests.ClinicClientDTO;
import com.liro.animals.dto.responses.AnimalExtraClinicResponse;
import com.liro.animals.exceptions.ConflictException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import com.liro.animals.repositories.AnimalExtraClinicsRepository;
import com.liro.animals.service.AnimalExtraClinicsService;
import com.liro.animals.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.liro.animals.util.Util.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AnimalExtraClinicsServiceImpl implements AnimalExtraClinicsService {


    private AnimalExtraClinicsRepository animalExtraClinicsRepository;
    private AnimalExtraClinicMapper animalExtraClinicMapper;
    private FeignClinicClientClient feignClinicClientClient;
    private Util util;


    public AnimalExtraClinicsServiceImpl(AnimalExtraClinicsRepository animalExtraClinicsRepository, AnimalExtraClinicMapper animalExtraClinicMapper,
                                         FeignClinicClientClient feignClinicClientClient,
                                         Util util) {
        this.animalExtraClinicsRepository = animalExtraClinicsRepository;
        this.animalExtraClinicMapper = animalExtraClinicMapper;
        this.feignClinicClientClient = feignClinicClientClient;
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
    public AnimalExtraClinicResponse addClinic(AnimalExtraClinicDTO animalExtraClinicDTO, UserDTO userDTO) {

        AnimalsExtraClinics animalsExtraClinics =animalExtraClinicMapper.animalExtraClinicDTOTOAnimalExtrClinic(animalExtraClinicDTO);

       Animal animal = util.validatePermissions(animalExtraClinicDTO.getAnimalId(), userDTO,true, false, false);

        System.out.println(userDTO.getId() + userDTO.getEmail());
        System.out.println(animalExtraClinicDTO.getExtraClinicId() + userDTO.getId());

        if (animal.getMainClinicId() == null){
            animal.setMainClinicId(animalsExtraClinics.getClinicId());
            animal.getExtraClinics().add(animalsExtraClinics);
        } else if (animal.getExtraClinics() == null) {
            animal.setExtraClinics(new HashSet<AnimalsExtraClinics>());
            animal.getExtraClinics().add(animalsExtraClinics);
            animalsExtraClinics.setAnimal(animal);
        }else{
            animal.getExtraClinics().add(animalsExtraClinics);
            animalsExtraClinics.setAnimal(animal);
        }

        try {
            System.out.println(animalExtraClinicDTO.getExtraClinicId() + userDTO.getId());
            ClinicClientDTO clinicClientDTO = ClinicClientDTO.builder()
                    .userId(userDTO.getId())
                    .clinicId(animalExtraClinicDTO.getExtraClinicId())
                    .accountBalance(0.00)
                    .build();
            feignClinicClientClient.addClinicClient(clinicClientDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
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
