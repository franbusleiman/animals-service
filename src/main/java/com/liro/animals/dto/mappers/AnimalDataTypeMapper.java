package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalDataTypeDTO;
import com.liro.animals.dto.responses.AnimalDataTypeResponse;
import com.liro.animals.model.dbentities.AnimalDataType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalDataTypeMapper {

    AnimalDataTypeResponse animalDataTypeToAnimalDataTypeResponse(AnimalDataType animalDataType);

    @Mapping(target = "animalDataSet", ignore = true)
    AnimalDataType animalDataTypeDtoToAnimalDataType(AnimalDataTypeDTO animalDataTypeDTO);
}
