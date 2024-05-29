package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

    boolean existsByName(String name);

    Optional<AnimalType> findByFormalName(String formalName);

    Page<AnimalType> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<AnimalType> findAllByGroupsId(Long groupId, Pageable pageable);

    Page<AnimalType> findAllByNameContainingAndGroupsId(String nameContaining, Long groupId, Pageable pageable);
}
