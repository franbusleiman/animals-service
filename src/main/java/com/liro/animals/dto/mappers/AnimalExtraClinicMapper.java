package com.liro.animals.dto.mappers;


import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import com.liro.animals.repositories.AnimalRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface AnimalExtraClinicMapper {





    AnimalsExtraClinics animalClinicDTOToAnimalExtraClinics(AnimalClinicDTO animalClinicDTO);


    AnimalClinicResponse animalExtraClinicToAnimalExtraClinicResponse(AnimalsExtraClinics animalsExtraClinics);

}
