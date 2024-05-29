package com.liro.animals.service;


import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.RecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {

    Page<RecordResponse> findAllByAnimalId(Long animalId, UserDTO userDTO, Pageable pageable);

    Page<RecordResponse> findAllLastByAnimalId(Long animalId, UserDTO userDTO, Pageable pageable);

    Page<RecordResponse> findAllByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId,
                                                          UserDTO userDTO, Pageable pageable);

    RecordResponse findLastByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId, UserDTO userDTO);

    RecordResponse getRecordResponse(Long recordId, UserDTO userDTO);

    RecordResponse createRecord(RecordDTO recordDTO, UserDTO userDTO);

    void deleteRecord(Long recordId, UserDTO userDTO);
}
