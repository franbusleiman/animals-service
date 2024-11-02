package com.liro.animals.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class BreedDTO {

    private String name;
    private String formalName;
    private String details;
    private List<String> alternativeNames;
    private Long animalTypeId;
}
