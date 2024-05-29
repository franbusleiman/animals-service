package com.liro.animals.service;


import com.liro.animals.dto.AnimalDataTypeDTO;
import com.liro.animals.dto.responses.AnimalDataTypeResponse;
import com.liro.animals.repositories.AnimalDataTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalDataTypeService {

    Page<AnimalDataTypeResponse> findAll(Pageable pageable);

    Page<AnimalDataTypeResponse> findAllByAnimalDataTypeContaining(String nameContaining, Pageable pageable);

    AnimalDataTypeResponse getAnimalDataTypeResponse(Long animalDataTypeId);

    AnimalDataTypeResponse createAnimalDataType(AnimalDataTypeDTO animalDataTypeDTO);

    AnimalDataTypeRepository getRepository();
}
