package com.liro.animals.service.impl;


import com.liro.animals.dto.AnimalTypeDTO;
import com.liro.animals.dto.mappers.AnimalTypeMapper;
import com.liro.animals.dto.responses.AnimalTypeResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.AnimalType;
import com.liro.animals.repositories.AnimalTypeRepository;
import com.liro.animals.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalTypeMapper animalTypeMapper;

    @Autowired
    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository,
                                 AnimalTypeMapper animalTypeMapper) {
        this.animalTypeRepository = animalTypeRepository;
        this.animalTypeMapper = animalTypeMapper;
    }

    @Override
    public Page<AnimalTypeResponse> findAll(Pageable pageable) {
        return animalTypeRepository.findAll(pageable)
            .map(animalTypeMapper::animalTypeToAnimalTypeResponse);
    }

    @Override
    public Page<AnimalTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return animalTypeRepository.findAllByNameContaining(nameContaining, pageable)
            .map(animalTypeMapper::animalTypeToAnimalTypeResponse);
    }

    @Override
    public AnimalTypeResponse getAnimalTypeResponse(Long animalTypeId) {
        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalType not found with id: " + animalTypeId));
        return animalTypeMapper.animalTypeToAnimalTypeResponse(animalType);
    }

    @Override
    public AnimalTypeResponse createAnimalType(AnimalTypeDTO animalTypeDto) {
        if (animalTypeDto.getName() != null) {
            animalTypeDto.setName(animalTypeDto.getName().toLowerCase());
        }
        if (animalTypeDto.getFormalName() != null) {
            animalTypeDto.setFormalName(animalTypeDto.getFormalName().toLowerCase());
        }
        AnimalType animalType = animalTypeMapper.animalTypeDtoToAnimalType(animalTypeDto);

        return animalTypeMapper.animalTypeToAnimalTypeResponse(
            animalTypeRepository.save(animalType)
        );
    }
}