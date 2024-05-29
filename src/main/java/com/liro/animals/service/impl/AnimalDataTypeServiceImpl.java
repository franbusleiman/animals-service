package com.liro.animals.service.impl;


import com.liro.animals.dto.AnimalDataTypeDTO;
import com.liro.animals.dto.mappers.AnimalDataTypeMapper;
import com.liro.animals.dto.responses.AnimalDataTypeResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.AnimalDataType;
import com.liro.animals.repositories.AnimalDataTypeRepository;
import com.liro.animals.service.AnimalDataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalDataTypeServiceImpl implements AnimalDataTypeService {

    private final AnimalDataTypeRepository animalDataTypeRepository;
    private final AnimalDataTypeMapper animalDataTypeMapper;

    @Autowired
    public AnimalDataTypeServiceImpl(AnimalDataTypeRepository animalDataTypeRepository,
                                      AnimalDataTypeMapper animalDataTypeMapper) {
        this.animalDataTypeRepository = animalDataTypeRepository;
        this.animalDataTypeMapper = animalDataTypeMapper;
    }

    @Override
    public Page<AnimalDataTypeResponse> findAll(Pageable pageable) {
        return animalDataTypeRepository.findAll(pageable)
            .map(animalDataTypeMapper::animalDataTypeToAnimalDataTypeResponse);
    }

    @Override
    public Page<AnimalDataTypeResponse> findAllByAnimalDataTypeContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return animalDataTypeRepository.findAllByAnimalDataTypeContaining(nameContaining, pageable)
            .map(animalDataTypeMapper::animalDataTypeToAnimalDataTypeResponse);
    }

    @Override
    public AnimalDataTypeResponse getAnimalDataTypeResponse(Long animalDataTypeId) {
        AnimalDataType animalDataType = animalDataTypeRepository.findById(animalDataTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalDataType not found with id: " + animalDataTypeId));
        return animalDataTypeMapper.animalDataTypeToAnimalDataTypeResponse(animalDataType);
    }

    @Override
    public AnimalDataTypeResponse createAnimalDataType(AnimalDataTypeDTO animalDataTypeDto) {
        if (animalDataTypeDto.getAnimalDataType() != null) {
            animalDataTypeDto.setAnimalDataType(animalDataTypeDto.getAnimalDataType().toLowerCase());
        }
        AnimalDataType animalDataType = animalDataTypeMapper.animalDataTypeDtoToAnimalDataType(animalDataTypeDto);

        return animalDataTypeMapper.animalDataTypeToAnimalDataTypeResponse(
            animalDataTypeRepository.save(animalDataType)
        );
    }

    @Override
    public AnimalDataTypeRepository getRepository() {
        return animalDataTypeRepository;
    }
}