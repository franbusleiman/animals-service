package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalExtraClinicDTO;
import com.liro.animals.dto.responses.AnimalExtraClinicResponse;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import org.mapstruct.Mapper;

@Mapper
public interface AnimalExtraClinicMapper {


    AnimalsExtraClinics animalExtraClinicDTOTOAnimalExtrClinic(AnimalExtraClinicDTO animalExtraClinicDTO);


    AnimalExtraClinicResponse animalExtraClinicToAnimalExtraClinicResponse(AnimalsExtraClinics animalsExtraClinics);

}
