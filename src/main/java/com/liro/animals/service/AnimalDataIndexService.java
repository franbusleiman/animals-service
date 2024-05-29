package com.liro.animals.service;


import com.liro.animals.dto.AnimalDataIndexDTO;
import com.liro.animals.dto.responses.AnimalDataIndexResponse;
import com.liro.animals.repositories.AnimalDataIndexRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalDataIndexService {

    Page<AnimalDataIndexResponse> findAll(Pageable pageable);

    Page<AnimalDataIndexResponse> findAllByAnimalDataIndexTypeContaining(String nameContaining, Pageable pageable);

    AnimalDataIndexResponse getAnimalDataIndexResponse(Long animalDataIndexId);

    AnimalDataIndexResponse createAnimalDataIndex(AnimalDataIndexDTO animalDataIndexDTO);

    AnimalDataIndexRepository getRepository();
}
