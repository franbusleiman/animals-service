package com.liro.animals.service.impl;


import com.liro.animals.config.FeignClinicClientClient;
import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.mappers.AnimalExtraClinicMapper;
import com.liro.animals.dto.requests.ClinicClientDTO;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import com.liro.animals.repositories.AnimalExtraClinicsRepository;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.service.AnimalExtraClinicsService;
import com.liro.animals.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AnimalExtraClinicsServiceImpl implements AnimalExtraClinicsService {


    private AnimalExtraClinicsRepository animalExtraClinicsRepository;
    private AnimalExtraClinicMapper animalExtraClinicMapper;
    private FeignClinicClientClient feignClinicClientClient;

    private AnimalRepository animalRepository;
    private Util util;


    public AnimalExtraClinicsServiceImpl(AnimalExtraClinicsRepository animalExtraClinicsRepository, AnimalExtraClinicMapper animalExtraClinicMapper,
                                         FeignClinicClientClient feignClinicClientClient,
                                         AnimalRepository animalRepository,
                                         Util util) {
        this.animalExtraClinicsRepository = animalExtraClinicsRepository;
        this.animalExtraClinicMapper = animalExtraClinicMapper;
        this.feignClinicClientClient = feignClinicClientClient;
        this.animalRepository = animalRepository;
        this.util = util;
    }

//    @Override
//    public AnimalClinicResponse addExtraClinic(AnimalClinicDTO animalClinicDTO, UserDTO userDTO) {
//
//
//
//        AnimalsExtraClinics animalsExtraClinics = animalExtraClinicMapper.animalClinicDTOToAnimalExtraClinics(animalClinicDTO);
//
//        util.validatePermissions(animalClinicDTO.getAnimalId(), userDTO,
//                true, false, false);
//
//        return animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalExtraClinicsRepository.save(animalsExtraClinics));
//    }

    @Override
    public void addClinic(AnimalClinicDTO animalClinicDTO, String token, Long clinicId) {

        UserDTO userDTO = Util.getUser(token, clinicId);

       Animal animal = util.validatePermissions(animalClinicDTO.getAnimalId(), token, clinicId,true, false, false);

        if (animal.getMainClinicId() == null){
            animal.setMainClinicId(animalClinicDTO.getClinicId());
            animalRepository.save(animal);
        } else if (animal.getExtraClinics() == null) {
            animal.setExtraClinics(new HashSet<AnimalsExtraClinics>());
        }else {
            AnimalsExtraClinics animalsExtraClinics = AnimalsExtraClinics.builder()
                    .animal(animal)
                    .clinicId(animalClinicDTO.getClinicId())
                    .build();
            animal.getExtraClinics().add(animalsExtraClinics);
            animalExtraClinicsRepository.save(animalsExtraClinics);
        }
        try {
            ClinicClientDTO clinicClientDTO = ClinicClientDTO.builder()
                    .userId(userDTO.getId())
                    .clinicId(animalClinicDTO.getAnimalId())
                    .accountBalance(0.00)
                    .build();
            feignClinicClientClient.addClinicClient(clinicClientDTO, token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Page<AnimalClinicResponse> getExtraClinicsRelationsByClinicId(Pageable pageable, Long clinicId, UserDTO userDTO) {
        return animalExtraClinicsRepository.findAllByClinicId(pageable, clinicId)
                .map(animalsExtraClinics -> animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalsExtraClinics));
    }

    @Override
    public Page<AnimalClinicResponse> getExtraClinicsRelationsByAnimalId(Pageable pageable, Long animalId, UserDTO userDTO) {
        return animalExtraClinicsRepository.findAllByAnimalId(pageable, animalId)
                .map(animalsExtraClinics -> animalExtraClinicMapper.animalExtraClinicToAnimalExtraClinicResponse(animalsExtraClinics));    }

}
