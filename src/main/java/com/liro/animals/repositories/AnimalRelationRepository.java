package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRelationRepository extends JpaRepository<AnimalRelation, Long> {

    Page<AnimalRelation> findAllByAnimalId(Long animalId, Pageable pageable);

    Page<AnimalRelation> findAllByAnimalRelationOfId(Long animalId, Pageable pageable);
}