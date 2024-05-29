package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDataRepository extends JpaRepository<AnimalData, Long> {

    Page<AnimalData> findAllByBreedId(Long breedId, Pageable pageable);

    Page<AnimalData> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable);

    Page<AnimalData> findAllByGroupId(Long groupId, Pageable pageable);

    Page<AnimalData> findAllByAnimalDataIndexAnimalDataIndexTypeAndBreedId(
            String animalDataIndexType,
            Long breedId,
            Pageable pageable
    );

    Page<AnimalData> findAllByAnimalDataIndexAnimalDataIndexTypeAndAnimalTypeId(
            String animalDataIndexType,
            Long animalTypeId,
            Pageable pageable
    );

    Page<AnimalData> findAllByAnimalDataIndexAnimalDataIndexTypeAndGroupId(
            String animalDataIndexType,
            Long groupId,
            Pageable pageable
    );

    Page<AnimalData> findAllByAnimalDataTypeAnimalDataTypeAndBreedId(
            String animalDataType,
            Long breedId,
            Pageable pageable
    );

    Page<AnimalData> findAllByAnimalDataTypeAnimalDataTypeAndAnimalTypeId(
            String animalDataType,
            Long animalTypeId,
            Pageable pageable
    );

    Page<AnimalData> findAllByAnimalDataTypeAnimalDataTypeAndGroupId(
            String animalDataType,
            Long groupId,
            Pageable pageable
    );
}


