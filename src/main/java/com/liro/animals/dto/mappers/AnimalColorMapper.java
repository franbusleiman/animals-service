package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalColorDTO;
import com.liro.animals.dto.responses.AnimalColorResponse;
import com.liro.animals.model.dbentities.AnimalColor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface AnimalColorMapper {

    AnimalColorResponse animalColorToAnimalColorResponse(AnimalColor animalColor);

    @Mapping(target = "animals", ignore = true)
    @Mapping(target = "mainColorOf", ignore = true)
    AnimalColor animalColorDtoToAnimalColor(AnimalColorDTO animalColorDTO);
}
