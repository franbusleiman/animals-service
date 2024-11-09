package com.liro.animals.service;


import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalMigratorDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesWADTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalCompleteResponse;
import com.liro.animals.dto.responses.AnimalMigrationResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalService {

    AnimalResponse getAnimalResponse(Long animalId, UserDTO userDTO);

    AnimalResponse createAnimal(AnimalDTO animalRequest, UserDTO userDTO);

    List<AnimalMigrationResponse> migrateAnimals(List<AnimalMigratorDTO> animalMigratorDTOList, Long vetClinicId);


    void updateAnimal(AnimalDTO animalRequest, Long animalId, String token, Long clinicId);

    void deleteAnimal(Long animalId, String token, Long clinicId);

    void changeShareStateAnimal(Long animalId, String shareToEmail, boolean readOnly, String token, Long clinicId);

    void removeShareAnimal(Long animalId, String shareToEmail, String token, Long clinicId);

    void increaseNumberOfPhotos(Long animalId, String token, Long clinicId);

    void decreaseNumberOfPhotos(Long animalId, String token, Long clinicId);

    void addColor(Long animalId, Long animalColorId, String token, Long clinicId);

    void removeColor(Long animalId, Long animalColorId, String token, Long clinicId);

    void changeMainColor(Long animalId, Long animalColorId, String token, Long clinicId);

    void changeOwner(Long animalId, String emailToTransfer, String token, Long clinicId);

    void hasPermissions(Long animalId, String token, Long clinicId, boolean needWritePermissions,
                        boolean onlyOwner, boolean onlyVet);

    Page<AnimalsSharedClientProfilesWADTO> getSharedAnimals(Pageable pageable, UserDTO userDTO);

    Page<AnimalResponse> getOwnAnimals(Pageable pageable, UserDTO userDTO);
    List<AnimalCompleteResponse> getAnimalsByUserId(Long userId);
    Page<AnimalCompleteResponse> getAnimalsByNameAndVetId(Pageable pageable, String name, UserDTO userDTO);
    Page<AnimalCompleteResponse> getAnimalsByOwnerDni(Pageable pageable, Long dni, UserDTO userDTO);

}
