package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByOwnerUserId(Long ownerUserId);

    Page<Animal> findAllByOwnerUserId(Long ownerUserId, Pageable pageable);

    Page<Animal> findAllByNameContainingAndMainVetUserId(String nameContaining, Long mainVetUserId, Pageable pageable);


    Page<Animal> findAllByOwnerUserIdAndDisabled(Long ownerUserId, Boolean disabled, Pageable pageable);
}