package com.liro.animals.service.impl;


import com.liro.animals.dto.AnimalDataDTO;
import com.liro.animals.dto.mappers.AnimalDataMapper;
import com.liro.animals.dto.requests.BAGId;
import com.liro.animals.dto.responses.AnimalDataResponse;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.*;
import com.liro.animals.repositories.*;
import com.liro.animals.service.AnimalDataService;
import com.liro.animals.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AnimalDataServiceImpl implements AnimalDataService {

    private final AnimalDataIndexRepository animalDataIndexRepository;
    private final AnimalDataTypeRepository animalDataTypeRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final GroupRepository groupRepository;
    private final BreedRepository breedRepository;
    private final AnimalDataRepository animalDataRepository;
    private final AnimalDataMapper animalDataMapper;

    @Autowired
    public AnimalDataServiceImpl(AnimalDataIndexRepository animalDataIndexRepository,
                                 AnimalDataTypeRepository animalDataTypeRepository,
                                 AnimalTypeRepository animalTypeRepository,
                                 GroupRepository groupRepository,
                                 BreedRepository breedRepository,
                                 AnimalDataRepository animalDataRepository,
                                 AnimalDataMapper animalDataMapper) {
        this.animalDataIndexRepository = animalDataIndexRepository;
        this.animalDataTypeRepository = animalDataTypeRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.groupRepository = groupRepository;
        this.breedRepository = breedRepository;
        this.animalDataRepository = animalDataRepository;
        this.animalDataMapper = animalDataMapper;
    }

    @Override
    public Page<AnimalDataResponse> findAllByBAGId(BAGId bagId, Pageable pageable) {
        if (bagId.getBreedId() != null) {
            return animalDataRepository.findAllByBreedId(bagId.getBreedId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getAnimalTypeId() != null) {
            return animalDataRepository.findAllByAnimalTypeId(bagId.getAnimalTypeId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getGroupId() != null) {
            return animalDataRepository.findAllByGroupId(bagId.getGroupId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else {
            throw new ResourceNotFoundException("It's mandatory to include only on of: breedId, animalTypeId, groupId");
        }
    }

    @Override
    public AnimalDataResponse getAnimalDataResponse(Long animalDataId) {
        AnimalData animalData = animalDataRepository.findById(animalDataId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalData not found with id: " + animalDataId));
        return animalDataMapper.animalDataToAnimalDataResponse(animalData);
    }

    @Override
    public Page<AnimalDataResponse> getAllByAnimalDataIndexType(String dataIndexType, BAGId bagId, Pageable pageable) {
        if (bagId.getBreedId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataIndexAnimalDataIndexTypeAndBreedId(dataIndexType, bagId.getBreedId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getAnimalTypeId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataIndexAnimalDataIndexTypeAndAnimalTypeId(dataIndexType, bagId.getAnimalTypeId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getGroupId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataIndexAnimalDataIndexTypeAndGroupId(dataIndexType, bagId.getGroupId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else {
            throw new ResourceNotFoundException("It's mandatory to include only on of: breedId, animalTypeId, groupId");
        }
    }

    @Override
    public Page<AnimalDataResponse> getAllByAnimalDataType(String dataType, BAGId bagId, Pageable pageable) {
        if (bagId.getBreedId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataTypeAnimalDataTypeAndBreedId(dataType, bagId.getBreedId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getAnimalTypeId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataTypeAnimalDataTypeAndAnimalTypeId(dataType, bagId.getAnimalTypeId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else if (bagId.getGroupId() != null) {
            return animalDataRepository
                    .findAllByAnimalDataTypeAnimalDataTypeAndGroupId(dataType, bagId.getGroupId(), pageable)
                    .map(animalDataMapper::animalDataToAnimalDataResponse);
        } else {
            throw new ResourceNotFoundException("It's mandatory to include only on of: breedId, animalTypeId, groupId");
        }
    }

    @Override
    public AnimalDataResponse createAnimalData(AnimalDataDTO animalDataDto) {
        if (animalDataDto.getAnimalDataType() != null) {
            animalDataDto.setAnimalDataType(animalDataDto.getAnimalDataType().toLowerCase());
        }
        if (animalDataDto.getAnimalDataValue() != null) {
            animalDataDto.setAnimalDataValue(animalDataDto.getAnimalDataValue().toLowerCase());
        }
        AnimalData animalData = animalDataMapper.animalDataDtoToAnimalData(animalDataDto);

        setData(animalDataDto, animalData);

        return animalDataMapper.animalDataToAnimalDataResponse(animalDataRepository.save(animalData));
    }

    @Override
    public void updateAnimalData(AnimalDataDTO animalDataDto, Long animalDataId) {
        if (animalDataDto.getAnimalDataType() != null) {
            animalDataDto.setAnimalDataType(animalDataDto.getAnimalDataType().toLowerCase());
        }
        if (animalDataDto.getAnimalDataValue() != null) {
            animalDataDto.setAnimalDataValue(animalDataDto.getAnimalDataValue().toLowerCase());
        }
        AnimalData animalData = animalDataRepository.findById(animalDataId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalData not found with id: "
                + animalDataDto.getBreedId()));

        setData(animalDataDto, animalData);

        Util.updateIfNotNull(animalData::setAnimalIndexValue, animalDataDto.getAnimalIndexValue());
        Util.updateIfNotNull(animalData::setAnimalDataValue, animalDataDto.getAnimalDataValue());

        animalDataRepository.save(animalData);
    }


    private void setData(AnimalDataDTO animalDataDto, AnimalData animalData) {
        if (animalDataDto.getBreedId() != null) {
            Breed breed = breedRepository.findById(animalDataDto.getBreedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: "
                            + animalDataDto.getBreedId()));
            animalData.setBreed(breed);
            if (breed.getAnimalData() == null) breed.setAnimalData(new HashSet<>());
            breed.getAnimalData().add(animalData);
        } else if (animalDataDto.getAnimalTypeId() != null) {
            AnimalType animalType = animalTypeRepository.findById(animalDataDto.getAnimalTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("AnimalType not found with id: "
                            + animalDataDto.getAnimalTypeId()));
            animalData.setAnimalType(animalType);
            if (animalType.getAnimalData() == null) animalType.setAnimalData(new HashSet<>());
            animalType.getAnimalData().add(animalData);
        } else if (animalDataDto.getGroupId() != null) {
            Group group = groupRepository.findById(animalDataDto.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: "
                            + animalDataDto.getGroupId()));
            animalData.setGroup(group);
            if (group.getAnimalData() == null) group.setAnimalData(new HashSet<>());
            group.getAnimalData().add(animalData);
        } else {
            throw new ResourceNotFoundException("It's mandatory to include only on of: breedId, animalTypeId, groupId");
        }

        if (animalDataDto.getAnimalDataIndexType() != null) {
            AnimalDataIndex animalDataIndex = animalDataIndexRepository
                .findByAnimalDataIndexType(animalDataDto.getAnimalDataIndexType())
                .orElseThrow(() -> new ResourceNotFoundException("AnimalDataIndex not found with name: "
                    + animalDataDto.getAnimalDataIndexType()));
            animalData.setAnimalDataIndex(animalDataIndex);
            if (animalDataIndex.getAnimalDataSet() == null) animalDataIndex.setAnimalDataSet(new HashSet<>());
            animalDataIndex.getAnimalDataSet().add(animalData);
        }

        if (animalDataDto.getAnimalDataType() != null) {
            AnimalDataType animalDataType = animalDataTypeRepository
                .findByAnimalDataType(animalDataDto.getAnimalDataType())
                .orElseThrow(() -> new ResourceNotFoundException("AnimalDataType not found with name: "
                    + animalDataDto.getAnimalDataType()));
            animalData.setAnimalDataType(animalDataType);
            if (animalDataType.getAnimalDataSet() == null) animalDataType.setAnimalDataSet(new HashSet<>());
            animalDataType.getAnimalDataSet().add(animalData);
        }
    }
}