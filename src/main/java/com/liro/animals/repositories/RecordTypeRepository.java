package com.liro.animals.repositories;

import com.liro.animals.model.dbentities.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {

    boolean existsByRecordType(String recordType);

    Page<RecordType> findAllByRecordTypeContaining(String nameContaining, Pageable pageable);
}