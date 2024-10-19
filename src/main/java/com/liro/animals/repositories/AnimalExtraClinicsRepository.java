package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalExtraClinicsRepository extends JpaRepository<AnimalsExtraClinics, Long> {


    Page<AnimalsExtraClinics> findAllByClinicId(Pageable pageable, Long clinidId);

    Page<AnimalsExtraClinics> findAllByAnimalId(Pageable pageable, Long animalId);

}