package com.liro.animals.service.impl;


import com.liro.animals.dto.RecordTypeDTO;
import com.liro.animals.dto.mappers.RecordTypeMapper;
import com.liro.animals.dto.responses.RecordTypeResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.RecordType;
import com.liro.animals.repositories.RecordTypeRepository;
import com.liro.animals.service.RecordTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordTypeServiceImpl implements RecordTypeService {

    private final RecordTypeRepository recordTypeRepository;
    private final RecordTypeMapper recordTypeMapper;

    @Autowired
    public RecordTypeServiceImpl(RecordTypeRepository recordTypeRepository,
                                 RecordTypeMapper recordTypeMapper) {
        this.recordTypeRepository = recordTypeRepository;
        this.recordTypeMapper = recordTypeMapper;
    }

    @Override
    public Page<RecordTypeResponse> findAll(Pageable pageable) {
        return recordTypeRepository.findAll(pageable)
            .map(recordTypeMapper::recordTypeToRecordTypeResponse);
    }

    @Override
    public Page<RecordTypeResponse> findAllByRecordTypeContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return recordTypeRepository.findAllByRecordTypeContaining(nameContaining, pageable)
            .map(recordTypeMapper::recordTypeToRecordTypeResponse);
    }

    @Override
    public RecordTypeResponse getRecordTypeResponse(Long recordTypeId) {
        RecordType recordType = recordTypeRepository.findById(recordTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("RecordType not found with id: " + recordTypeId));
        return recordTypeMapper.recordTypeToRecordTypeResponse(recordType);
    }

    @Override
    public RecordTypeResponse createRecordType(RecordTypeDTO recordTypeDto) {
        if (recordTypeDto.getRecordType() != null) {
            recordTypeDto.setRecordType(recordTypeDto.getRecordType().toLowerCase());
        }
        RecordType recordType = recordTypeMapper.recordTypeDtoToRecordType(recordTypeDto);

        return recordTypeMapper.recordTypeToRecordTypeResponse(
            recordTypeRepository.save(recordType)
        );
    }

    @Override
    public RecordTypeRepository getRepository() {
        return recordTypeRepository;
    }
}