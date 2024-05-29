package com.liro.animals.dto.responses;

import com.liro.animals.dto.GroupDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

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
public class GroupResponse extends GroupDTO {

    private Long id;

    private Set<BreedResponse> breeds;

    private Set<AnimalTypeResponse> animalTypes;
}
