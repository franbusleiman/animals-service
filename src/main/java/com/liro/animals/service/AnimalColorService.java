package com.liro.animals.service;


import com.liro.animals.dto.AnimalColorDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalColorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalColorService {

    Page<AnimalColorResponse> findAll(Pageable pageable);

    Page<AnimalColorResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    List<AnimalColorResponse> findAllByAnimalId(Long animalId,  UserDTO userDTO);

    AnimalColorResponse findMainColorByAnimalId(Long animalId, UserDTO userDTO);

    AnimalColorResponse getAnimalColorResponse(Long animalColorId);

    AnimalColorResponse createAnimalColor(AnimalColorDTO animalColorDTO);
}
