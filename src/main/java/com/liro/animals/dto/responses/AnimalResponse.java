package com.liro.animals.dto.responses;


import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesDTO;
import com.liro.animals.model.dbentities.AnimalColor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalResponse extends AnimalDTO {

    private Long id;

    private BreedResponse breed;
    private AnimalTypeResponse animalType;
    private Long ownerUserId;
    private Set<AnimalsSharedClientProfilesDTO> sharedWith;
    private boolean disabled;
    private Boolean death;
    private Set<AnimalColorResponse> colors;
}