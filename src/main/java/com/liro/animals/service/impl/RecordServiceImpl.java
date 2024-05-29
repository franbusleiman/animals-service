package com.liro.animals.service.impl;


import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.mappers.RecordMapper;
import com.liro.animals.dto.responses.RecordResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.RecordType;
import com.liro.animals.repositories.RecordRepository;
import com.liro.animals.repositories.RecordTypeRepository;
import com.liro.animals.service.RecordService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.liro.animals.model.dbentities.Record;


import java.util.HashSet;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordTypeRepository recordTypeRepository;
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final UserService userService;
    private final Util util;

    @Autowired
    public RecordServiceImpl(RecordTypeRepository recordTypeRepository,
                             RecordRepository recordRepository,
                             RecordMapper recordMapper,
                             UserService userService,
                             Util util) {
        this.recordTypeRepository = recordTypeRepository;
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
        this.userService = userService;
        this.util = util;
    }

    @Override
    public Page<RecordResponse> findAllByAnimalId(Long animalId, UserDTO userDTO, Pageable pageable) {
            util.validatePermissions(animalId, userDTO, false, false, true, false);

        return recordRepository.findAllByAnimalId(animalId, pageable)
            .map(recordMapper::recordToRecordResponse);
    }

    @Override
    public Page<RecordResponse> findAllLastByAnimalId(Long animalId, UserDTO userDTO, Pageable pageable) {
            util.validatePermissions(animalId, userDTO, false, false, true, false);

        return recordRepository.findAllLastByAnimalId(animalId, pageable)
            .map(recordMapper::recordToRecordResponse);
    }

    @Override
    public Page<RecordResponse> findAllByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId,
                                                                 UserDTO userDTO, Pageable pageable) {
            util.validatePermissions(animalId, userDTO, false, false, true, false);

        return recordRepository.findAllByAnimalIdAndRecordTypeId(animalId, recordTypeId, pageable)
            .map(recordMapper::recordToRecordResponse);
    }

    @Override
    public RecordResponse findLastByAnimalIdAndRecordTypeId(Long animalId, Long recordTypeId,
                                                            UserDTO userDTO) {
            util.validatePermissions(animalId, userDTO, false, false, true, false);

        return recordMapper.recordToRecordResponse(recordRepository
            .findLastByAnimalIdAndRecordTypeId(animalId, recordTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Record not found with animalId: "
                + animalId + ", and recordTypeId: " + recordTypeId)));
    }

    @Override
    public RecordResponse getRecordResponse(Long recordId, UserDTO userDTO) {
    
        Record record = recordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordId));

        util.validatePermissions(record.getAnimal().getId(), userDTO,
            false, false, true, false);

        return recordMapper.recordToRecordResponse(record);
    }

    @Override
    public RecordResponse createRecord(RecordDTO recordDto, UserDTO userDTO) {
            Animal animal = util.validatePermissions(recordDto.getAnimalId(), userDTO,
            true, false, true, false);
        Record record = recordMapper.recordDtoToRecord(recordDto);

        if (util.validateVet(userDTO)) {
            record.setValidData(true);
            record.setVetUserId(userDTO.getId());
        }

        RecordType recordType = recordTypeRepository.findById(recordDto.getRecordTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("RecordType not found with id: "
                + recordDto.getRecordTypeId()));

        record.setRecordType(recordType);

        if (recordType.getRecords() == null) recordType.setRecords(new HashSet<>());
        recordType.getRecords().add(record);

        record.setAnimal(animal);

        return recordMapper.recordToRecordResponse(recordRepository.save(record));
    }

    @Override
    public void deleteRecord(Long recordId, UserDTO userDTO) {
    
        Record record = recordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + recordId));

        util.validatePermissions(record.getAnimal().getId(), userDTO,
            true, false, true, false);

        recordRepository.deleteById(recordId);
    }
}