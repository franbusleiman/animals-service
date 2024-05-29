package com.liro.animals.service.impl;

import com.liro.animals.dto.AnimalDataIndexDTO;
import com.liro.animals.dto.mappers.AnimalDataIndexMapper;
import com.liro.animals.dto.responses.AnimalDataIndexResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.AnimalDataIndex;
import com.liro.animals.repositories.AnimalDataIndexRepository;
import com.liro.animals.service.AnimalDataIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnimalDataIndexServiceImpl implements AnimalDataIndexService {

    private final AnimalDataIndexRepository animalDataIndexRepository;
    private final AnimalDataIndexMapper animalDataIndexMapper;

    @Autowired
    public AnimalDataIndexServiceImpl(AnimalDataIndexRepository animalDataIndexRepository,
                                       AnimalDataIndexMapper animalDataIndexMapper) {
        this.animalDataIndexRepository = animalDataIndexRepository;
        this.animalDataIndexMapper = animalDataIndexMapper;
    }

    @Override
    public Page<AnimalDataIndexResponse> findAll(Pageable pageable) {
        return animalDataIndexRepository.findAll(pageable)
            .map(animalDataIndexMapper::animalDataIndexToAnimalDataIndexResponse);
    }

    @Override
    public Page<AnimalDataIndexResponse> findAllByAnimalDataIndexTypeContaining(String nameContaining,
                                                                                Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return animalDataIndexRepository.findAllByAnimalDataIndexTypeContaining(nameContaining, pageable)
            .map(animalDataIndexMapper::animalDataIndexToAnimalDataIndexResponse);
    }

    @Override
    public AnimalDataIndexResponse getAnimalDataIndexResponse(Long animalDataIndexId) {
        AnimalDataIndex animalDataIndex = animalDataIndexRepository.findById(animalDataIndexId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalDataIndex not found with id: " + animalDataIndexId));
        return animalDataIndexMapper.animalDataIndexToAnimalDataIndexResponse(animalDataIndex);
    }

    @Override
    public AnimalDataIndexResponse createAnimalDataIndex(AnimalDataIndexDTO animalDataIndexDto) {
        if (animalDataIndexDto.getAnimalDataIndexType() != null) {
            animalDataIndexDto.setAnimalDataIndexType(animalDataIndexDto.getAnimalDataIndexType().toLowerCase());
        }
        AnimalDataIndex animalDataIndex = animalDataIndexMapper.animalDataIndexDtoToAnimalDataIndex(animalDataIndexDto);

        return animalDataIndexMapper.animalDataIndexToAnimalDataIndexResponse(
            animalDataIndexRepository.save(animalDataIndex)
        );
    }

    @Override
    public AnimalDataIndexRepository getRepository() {
        return animalDataIndexRepository;
    }
}