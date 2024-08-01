package com.liro.animals.dto.mappers;

import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesWADTO;
import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.responses.AnimalCompleteResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import com.liro.animals.model.dbentities.Record;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AnimalColorMapper.class)
public interface AnimalMapper {

    @Mapping(target = "breedId", source = "breed.id")
    @Mapping(target = "mainColorHex", source = "mainColor.hex")
    AnimalResponse animalToAnimalResponse(Animal animal);

    @Mapping(target = "breedId", source = "breed.id")
    @Mapping(target = "mainColorHex", source = "mainColor.hex")
    AnimalCompleteResponse animalToAnimalCompleteResponse(Animal animal);

    @Mapping(target = "validCastrated", ignore = true)
    @Mapping(target = "validBreed", ignore = true)
    @Mapping(target = "validBirthDate", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "death", ignore = true)
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "sharedWith", ignore = true)
    @Mapping(target = "ownerUserId", ignore = true)
    @Mapping(target = "mainColor", ignore = true)
    @Mapping(target = "colors", ignore = true)
    @Mapping(target = "mainVetUserId", ignore = true)
    @Mapping(target = "extraVets", ignore = true)
    @Mapping(target = "breed", ignore = true)
    Animal animalDtoToAnimal(AnimalDTO animalRequest);

    @Mapping(target = "recordType", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "animal", ignore = true)
    Record recordDtoToRecord(RecordDTO recordDTO);

    AnimalsSharedClientProfilesWADTO aSCPToASCPWADto(AnimalsSharedUsers animalsSharedUsers);
}
