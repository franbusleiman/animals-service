package com.liro.animals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDataDTO {

    private String animalDataIndexType;
    private String animalIndexValue;

    private String animalDataType;
    private String animalDataValue;

    private Long breedId;
    private Long animalTypeId;
    private Long groupId;
}
