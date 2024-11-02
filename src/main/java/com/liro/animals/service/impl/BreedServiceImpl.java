package com.liro.animals.service.impl;


import com.liro.animals.dto.BreedDTO;
import com.liro.animals.dto.mappers.BreedMapper;
import com.liro.animals.dto.responses.BreedResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.AnimalType;
import com.liro.animals.model.dbentities.Breed;
import com.liro.animals.repositories.AnimalTypeRepository;
import com.liro.animals.repositories.BreedRepository;
import com.liro.animals.service.BreedService;
import com.liro.animals.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final BreedMapper breedMapper;

    @Autowired
    public BreedServiceImpl(BreedRepository breedRepository,
                            AnimalTypeRepository animalTypeRepository,
                            BreedMapper breedMapper) {
        this.breedRepository = breedRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.breedMapper = breedMapper;
    }

    @Override
    public Page<BreedResponse> findAll(Pageable pageable) {
        return breedRepository.findAll(pageable).map(breedMapper::breedToBreedResponse);
    }

    @Override
    public Page<BreedResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return breedRepository.findAllByNameContaining(nameContaining, pageable)
            .map(breedMapper::breedToBreedResponse);
    }

    @Override
    public Page<BreedResponse> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable) {
        return breedRepository.findAllByAnimalTypeId(animalTypeId, pageable)
            .map(breedMapper::breedToBreedResponse);
    }

    @Override
    public Page<BreedResponse> findAllByNameContainingAndAnimalTypeId(String nameContaining, Long animalTypeId,
                                                                      Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return breedRepository.findAllByNameContainingAndAnimalTypeId(nameContaining, animalTypeId, pageable)
            .map(breedMapper::breedToBreedResponse);
    }

    @Override
    public BreedResponse getBreedResponse(Long breedId) {
        Breed breed = breedRepository.findById(breedId)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: " + breedId));
        return breedMapper.breedToBreedResponse(breed);
    }

    @Override
    public BreedResponse createBreed(BreedDTO breedDto) {
        if (breedDto.getName() != null) {
            breedDto.setName(breedDto.getName().toLowerCase());
        }
        if (breedDto.getFormalName() != null) {
            breedDto.setFormalName(breedDto.getFormalName().toLowerCase());
        }
        Breed breed = breedMapper.breedDtoToBreed(breedDto);
        breed.setName(breed.getName().toLowerCase());
        breed.setFormalName(breed.getFormalName().toLowerCase());

        AnimalType animalType = animalTypeRepository.
            findById(breedDto.getAnimalTypeId()).orElseThrow(
            () -> new ResourceNotFoundException("AnimalType not found with id: "
                + breedDto.getAnimalTypeId()));

        breed.setAnimalType(animalType);

        if (animalType.getBreeds() == null) animalType.setBreeds(new HashSet<>());
        animalType.getBreeds().add(breed);

        return breedMapper.breedToBreedResponse(breedRepository.save(breed));
    }

    @Override
    public Void migrateBreeds(List<BreedDTO> breedDTOs) {

        breedDTOs.forEach(breedDto -> {
            System.out.println(breedDto);

            if (!breedRepository.findByName(breedDto.getName()).isPresent()){
                if (breedDto.getName() != null) {
                    breedDto.setName(breedDto.getName().toLowerCase());
                }
                if (breedDto.getFormalName() != null) {
                    breedDto.setFormalName(breedDto.getFormalName().toLowerCase());
                }
                Breed breed = breedMapper.breedDtoToBreed(breedDto);
                breed.setName(breed.getName().toLowerCase());
                breed.setFormalName(breed.getFormalName().toLowerCase());

                AnimalType animalType = animalTypeRepository.
                        findById(breedDto.getAnimalTypeId()).orElseThrow(
                                () -> new ResourceNotFoundException("AnimalType not found with id: "
                                        + breedDto.getAnimalTypeId()));

                breed.setAnimalType(animalType);

                if (animalType.getBreeds() == null) animalType.setBreeds(new HashSet<>());
                animalType.getBreeds().add(breed);

                System.out.println(breed);
                breedRepository.save(breed);
            }
            });


        return null;
    }

    @Override
    public void updateBreed(BreedDTO breedDto, Long breedId) {
        if (breedDto.getName() != null) {
            breedDto.setName(breedDto.getName().toLowerCase());
        }
        if (breedDto.getFormalName() != null) {
            breedDto.setFormalName(breedDto.getFormalName().toLowerCase());
        }
        Breed breed = breedRepository.findById(breedId)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: " + breedDto));

        Util.updateIfNotNull(breed::setName, breedDto.getName());
        Util.updateIfNotNull(breed::setFormalName, breedDto.getFormalName());
        Util.updateIfNotNull(breed::setDetails, breedDto.getDetails());

        breedRepository.save(breed);
    }

    @Override
    public BreedRepository getRepository() {
        return breedRepository;
    }
}