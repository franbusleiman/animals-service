package com.liro.animals.service;


import com.liro.animals.dto.AnimalTypeDTO;
import com.liro.animals.dto.responses.AnimalTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalTypeService {

    Page<AnimalTypeResponse> findAll(Pageable pageable);

    Page<AnimalTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    AnimalTypeResponse getAnimalTypeResponse(Long animalTypeId);

    AnimalTypeResponse createAnimalType(AnimalTypeDTO animalTypeDTO);
}
