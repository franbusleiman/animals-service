package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Page<Record> findAllByAnimalId(Long animalId, Pageable pageable);

    Page<Record> findAllByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId, Pageable pageable);

    @Query(
            value = "SELECT * FROM records where (animal_id, record_type_id, date) IN (SELECT animal_id, " +
                    "record_type_id, MAX(date) AS date FROM records WHERE animal_id = :animalId GROUP BY " +
                    "record_type_id, animal_id) --#pageable",
            countQuery = "SELECT COUNT(*) FROM records where (animal_id, record_type_id, date) IN (SELECT animal_id, " +
                    "record_type_id, MAX(date) AS date FROM records WHERE animal_id = :animalId GROUP BY " +
                    "record_type_id, animal_id)",
            nativeQuery = true
    )
    Page<Record> findAllLastByAnimalId(@Param("animalId") Long animalId, Pageable pageable);

    @Query(
            value = "SELECT * from records WHERE animal_id = :animalId " +
                    "AND record_type_id = :recordTypeId ORDER BY date DESC LIMIT 1",
            nativeQuery = true
    )
    Optional<Record> findLastByAnimalIdAndRecordTypeId(
            @Param("animalId") Long animalId,
            @Param("recordTypeId") Long recordTypeId
    );
}
