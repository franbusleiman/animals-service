package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalDataType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalDataTypeRepository extends JpaRepository<AnimalDataType, Long> {

    boolean existsByAnimalDataType(String animalDataType);

    Page<AnimalDataType> findAllByAnimalDataTypeContaining(String animalDataTypeContaining, Pageable pageable);

    Optional<AnimalDataType> findByAnimalDataType(String animalDataType);
}