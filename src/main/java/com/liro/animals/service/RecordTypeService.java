package com.liro.animals.service;


import com.liro.animals.dto.RecordTypeDTO;
import com.liro.animals.dto.responses.RecordTypeResponse;
import com.liro.animals.repositories.RecordTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordTypeService {

    Page<RecordTypeResponse> findAll(Pageable pageable);

    Page<RecordTypeResponse> findAllByRecordTypeContaining(String nameContaining, Pageable pageable);

    RecordTypeResponse getRecordTypeResponse(Long recordTypeId);

    RecordTypeResponse createRecordType(RecordTypeDTO recordTypeDTO);

    RecordTypeRepository getRepository();
}
