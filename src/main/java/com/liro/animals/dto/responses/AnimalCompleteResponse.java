package com.liro.animals.dto.responses;

import com.liro.animals.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalCompleteResponse extends AnimalDTO {

    private Long id;

    private UserResponseDTO owner;
    private BreedResponse breed;
    private AnimalTypeResponse animalType;
    private RecordResponse record;
    private Set<AnimalsSharedClientProfilesDTO> sharedWith;
    private String profilePictureURL;
    private Boolean disabled;
    private Boolean death;
}