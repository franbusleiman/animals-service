package com.liro.animals.service;


import com.liro.animals.dto.AnimalDataDTO;
import com.liro.animals.dto.requests.BAGId;
import com.liro.animals.dto.responses.AnimalDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalDataService {

    Page<AnimalDataResponse> findAllByBAGId(BAGId bagId, Pageable pageable);

    AnimalDataResponse getAnimalDataResponse(Long animalDataId);

    Page<AnimalDataResponse> getAllByAnimalDataIndexType(String dataIndexType, BAGId bagId, Pageable pageable);

    Page<AnimalDataResponse> getAllByAnimalDataType(String dataType, BAGId bagId, Pageable pageable);

    AnimalDataResponse createAnimalData(AnimalDataDTO animalDataDTO);

    void updateAnimalData(AnimalDataDTO animalDataDto, Long animalDataId);
}
