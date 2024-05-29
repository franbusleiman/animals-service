package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalDataDTO;
import com.liro.animals.dto.responses.AnimalDataResponse;
import com.liro.animals.model.dbentities.AnimalData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalDataMapper {

    @Mapping(target = "animalDataIndexType", source = "animalDataIndex.animalDataIndexType")
    @Mapping(target = "animalDataType", source = "animalDataType.animalDataType")
    AnimalDataResponse animalDataToAnimalDataResponse(AnimalData animalData);

    @Mapping(target = "breed", ignore = true)
    @Mapping(target = "animalDataType", ignore = true)
    @Mapping(target = "animalDataIndex", ignore = true)
    AnimalData animalDataDtoToAnimalData(AnimalDataDTO animalDataDTO);
}
