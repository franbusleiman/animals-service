package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.Breed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Page<Breed> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<Breed> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable);

    Page<Breed> findAllByGroupsId(Long groupId, Pageable pageable);

    Page<Breed> findAllByNameContainingAndAnimalTypeId(
            String nameContaining,
            Long animalTypeId,
            Pageable pageable
    );

    Page<Breed> findAllByNameContainingAndGroupsId(
            String nameContaining,
            Long groupId,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM breeds WHERE animal_type_id = :animalTypeId AND (name LIKE %:nameContaining% " +
                    "OR formal_name LIKE %:nameContaining% " +
                    "OR id IN (SELECT breed_id FROM animal_data WHERE animal_data_index_id IN (SELECT id FROM " +
                    "animal_data_indexes WHERE animal_data_index_type = 'general') AND animal_data_type_id IN " +
                    "(SELECT id FROM animal_data_types WHERE animal_data_type = 'other_names') " +
                    "AND animal_data_value LIKE %:nameContaining%)) --#pageable",
            countQuery = "SELECT COUNT(*) FROM breeds WHERE animal_type_id = :animalTypeId AND (name LIKE %:nameContaining% " +
                    "OR id IN (SELECT breed_id FROM animal_data WHERE animal_data_index_id IN (SELECT id FROM " +
                    "animal_data_indexes WHERE animal_data_index_type = 'general') AND animal_data_type_id IN " +
                    "(SELECT id FROM animal_data_types WHERE animal_data_type = 'other_names') " +
                    "AND animal_data_value LIKE %:nameContaining%))",
            nativeQuery = true
    )
    Page<Breed> findAllByAnimalTypeIdAndNameContainingOrOtherNameContaining(
            @Param("nameContaining") String nameContaining,
            @Param("animalTypeId") Long animalTypeId,
            Pageable pageable
    );

    @Query(
            value = "SELECT * FROM breeds WHERE id IN " +
                    "( SELECT breed_id FROM breeds_groups WHERE group_id = :groupId )" +
                    " AND (name LIKE %:nameContaining% OR formal_name LIKE %:nameContaining% " +
                    "OR id IN (SELECT breed_id FROM animal_data WHERE animal_data_index_id IN (SELECT id FROM " +
                    "animal_data_indexes WHERE animal_data_index_type = 'general') AND animal_data_type_id IN " +
                    "(SELECT id FROM animal_data_types WHERE animal_data_type = 'other_names') " +
                    "AND animal_data_value LIKE %:nameContaining%)) --#pageable",
            countQuery = "SELECT COUNT(*) FROM breeds WHERE id IN " +
                    "( SELECT breed_id FROM breeds_groups WHERE group_id = :groupId )" +
                    " AND (name LIKE %:nameContaining% OR formal_name LIKE %:nameContaining% " +
                    "OR id IN (SELECT breed_id FROM animal_data WHERE animal_data_index_id IN (SELECT id FROM " +
                    "animal_data_indexes WHERE animal_data_index_type = 'general') AND animal_data_type_id IN " +
                    "(SELECT id FROM animal_data_types WHERE animal_data_type = 'other_names') " +
                    "AND animal_data_value LIKE %:nameContaining%))",
            nativeQuery = true
    )
    Page<Breed> findAllByGroupsIdAndNameContainingOrOtherNameContaining(
            @Param("nameContaining") String nameContaining,
            @Param("groupId") Long groupId,
            Pageable pageable
    );
}
