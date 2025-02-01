package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByOwnerUserId(Long ownerUserId);


    @Query("SELECT a FROM Animal a " +
            "WHERE a.ownerUserId = :ownerUserId " +
            "AND (:death IS NULL OR a.death = :death)")
    Page<Animal> findAllByOwnerUserIdAndIsDeath(@Param("ownerUserId")Long ownerUserId, Pageable pageable, @Param("death")Boolean death);


    @Query("SELECT DISTINCT a FROM Animal a " +
            "LEFT JOIN a.extraClinics ec " +
            "WHERE a.name LIKE %:name% " +
            "AND (a.mainClinicId = :clinicId OR ec.clinicId = :clinicId)")
    Page<Animal> findAllByNameContainingAndMainClinicOrExtraClinics(@Param("name") String name, @Param("clinicId") Long clinicId, Pageable pageable);

    Page<Animal> findAllByOwnerUserIdAndDisabled(Long ownerUserId, Boolean disabled, Pageable pageable);
}