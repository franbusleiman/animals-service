package com.liro.animals.service;


import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.RecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {

    Page<RecordResponse> findAllByAnimalId(Long animalId, Pageable pageable);

    Page<RecordResponse> findAllLastByAnimalId(Long animalId,  Pageable pageable);

    Page<RecordResponse> findAllByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId,
                                                           Pageable pageable);

    RecordResponse findLastByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId);

    RecordResponse getRecordResponse(Long recordId);

    RecordResponse createRecord(RecordDTO recordDTO, String token, Long clinicId);
    Void migrateRecords(List<RecordDTO> recordDTOs, Long  vetUserId);


    void deleteRecord(Long recordId);
}
