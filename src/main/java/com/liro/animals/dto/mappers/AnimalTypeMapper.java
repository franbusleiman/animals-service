package com.liro.animals.dto.mappers;

import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalTypeDTO;
import com.liro.animals.dto.responses.AnimalTypeResponse;
import com.liro.animals.model.dbentities.AnimalType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalTypeMapper {

    AnimalTypeResponse animalTypeToAnimalTypeResponse(AnimalType animalType);

    @Mapping(target = "breeds", ignore = true)
    AnimalType animalTypeDtoToAnimalType(AnimalTypeDTO animalTypeDTO);
}
