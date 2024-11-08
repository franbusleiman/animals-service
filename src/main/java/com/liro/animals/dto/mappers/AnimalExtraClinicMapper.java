package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnimalExtraClinicMapper {

    @Mapping(target = "animal", ignore = true)
    @Mapping(target = "clinicId", source = "clinicId")
    AnimalsExtraClinics animalClinicDTOToAnimalExtraClinics(AnimalClinicDTO animalClinicDTO);


    AnimalClinicResponse animalExtraClinicToAnimalExtraClinicResponse(AnimalsExtraClinics animalsExtraClinics);

}
