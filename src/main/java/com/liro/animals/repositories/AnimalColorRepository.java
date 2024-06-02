package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AnimalColorRepository extends JpaRepository<AnimalColor, Long> {

    Boolean existsByName(String name);
    Boolean existsByHex(String hex);
    Optional<AnimalColor> findByHex(String hex);

    Page<AnimalColor> findAllByNameContaining(String nameContaining, Pageable pageable);
}