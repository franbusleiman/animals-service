package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.AnimalDataType;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalsSharedUsersRepository extends JpaRepository<AnimalsSharedUsers, Long> {

    Optional<AnimalsSharedUsers> findByAnimalIdAndUserId(Long animalId, Long userId);

    Page<AnimalsSharedUsers> findAllByUserId(Long userId, Pageable pageable);
}
