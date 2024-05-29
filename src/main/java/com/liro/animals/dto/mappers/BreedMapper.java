package com.liro.animals.dto.mappers;

import com.liro.animals.dto.BreedDTO;
import com.liro.animals.dto.responses.BreedResponse;
import com.liro.animals.model.dbentities.Breed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BreedMapper {

    @Mapping(target = "animalTypeId", source = "animalType.id")
    BreedResponse breedToBreedResponse(Breed breed);

    @Mapping(target = "animalData", ignore = true)
    @Mapping(target = "animalType", ignore = true)
    @Mapping(target = "animals", ignore = true)
    Breed breedDtoToBreed(BreedDTO breedDTO);
}
