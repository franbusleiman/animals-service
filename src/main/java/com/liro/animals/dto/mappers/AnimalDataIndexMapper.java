package com.liro.animals.dto.mappers;

import com.liro.animals.dto.AnimalDataIndexDTO;
import com.liro.animals.dto.responses.AnimalDataIndexResponse;
import com.liro.animals.model.dbentities.AnimalDataIndex;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalDataIndexMapper {

    AnimalDataIndexResponse animalDataIndexToAnimalDataIndexResponse(AnimalDataIndex animalDataIndex);

    @Mapping(target = "animalDataSet", ignore = true)
    AnimalDataIndex animalDataIndexDtoToAnimalDataIndex(AnimalDataIndexDTO animalDataIndexDTO);
}
