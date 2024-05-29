package com.liro.animals.service;


import com.liro.animals.dto.BreedDTO;
import com.liro.animals.dto.responses.BreedResponse;
import com.liro.animals.repositories.BreedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BreedService {

    Page<BreedResponse> findAll(Pageable pageable);

    Page<BreedResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<BreedResponse> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable);

    Page<BreedResponse> findAllByNameContainingAndAnimalTypeId(String nameContaining, Long animalTypeId, Pageable pageable);

    BreedResponse getBreedResponse(Long breedId);

    BreedResponse createBreed(BreedDTO breedDTO);

    void updateBreed(BreedDTO breedDto, Long breedId);

    BreedRepository getRepository();
}
