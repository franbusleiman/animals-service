package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalDataType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Page<Animal> findAllByOwnerUserId(Long ownerUserId, Pageable pageable);

    Page<Animal> findAllByOwnerUserIdAndDisabled(Long ownerUserId, Boolean disabled, Pageable pageable);
}