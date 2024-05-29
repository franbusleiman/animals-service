package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalDataIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalDataIndexRepository extends JpaRepository<AnimalDataIndex, Long> {

    boolean existsByAnimalDataIndexType(String animalDataIndexTypeContaining);

    Page<AnimalDataIndex> findAllByAnimalDataIndexTypeContaining(
            String animalDataIndexTypeContaining,
            Pageable pageable
    );

    Optional<AnimalDataIndex> findByAnimalDataIndexType(String animalDataIndexType);
}
