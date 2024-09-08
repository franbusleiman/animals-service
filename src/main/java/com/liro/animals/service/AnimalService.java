package com.liro.animals.service;


import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalMigratorDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesWADTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalCompleteResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalService {

    AnimalResponse getAnimalResponse(Long animalId, UserDTO userDTO);

    AnimalResponse createAnimal(AnimalDTO animalRequest, UserDTO userDTO);

    Void migrateAnimals(List<AnimalMigratorDTO> animalMigratorDTOList, Long vetUserId);


    void updateAnimal(AnimalDTO animalRequest, Long animalId, UserDTO userDTO);

    void deleteAnimal(Long animalId, UserDTO userDTO);

    void changeShareStateAnimal(Long animalId, String shareToEmail, boolean readOnly, UserDTO userDTO);

    void removeShareAnimal(Long animalId, String shareToEmail, UserDTO userDTO);

    void increaseNumberOfPhotos(Long animalId, UserDTO userDTO);

    void decreaseNumberOfPhotos(Long animalId, UserDTO userDTO);

    void addColor(Long animalId, Long animalColorId, UserDTO userDTO);

    void removeColor(Long animalId, Long animalColorId, UserDTO userDTO);

    void changeMainColor(Long animalId, Long animalColorId, UserDTO userDTO);

    void changeOwner(Long animalId, String emailToTransfer, UserDTO userDTO);

    void hasPermissions(Long animalId, UserDTO userDTO, boolean needWritePermissions,
                        boolean onlyOwner, boolean vetEnabled, boolean onlyVet);

    Page<AnimalsSharedClientProfilesWADTO> getSharedAnimals(Pageable pageable, UserDTO userDTO);

    Page<AnimalResponse> getOwnAnimals(Pageable pageable, UserDTO userDTO);
    List<AnimalCompleteResponse> getAnimalsByUserId(Long userId);
    Page<AnimalCompleteResponse> getAnimalsByNameAndVetId(Pageable pageable, String name, UserDTO userDTO);
    Page<AnimalCompleteResponse> getAnimalsByOwnerDni(Pageable pageable, Long dni, UserDTO userDTO);

}
