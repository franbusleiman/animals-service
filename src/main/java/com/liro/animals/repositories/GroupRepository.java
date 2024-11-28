package com.liro.animals.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByGroupName(String groupName);

    Page<Group> findAllByGroupNameContaining(String groupName, Pageable pageable);

    Page<Group> findAllByAnimalTypesId(Long animalTypeId, Pageable pageable);

    Page<Group> findAllByBreedsId(Long animalTypeId, Pageable pageable);
}