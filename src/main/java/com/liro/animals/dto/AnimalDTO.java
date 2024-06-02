package com.liro.animals.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.liro.animals.model.enums.Castrated;
import com.liro.animals.model.enums.Sex;
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

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalDTO {

    private String name;
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    private Boolean approxBirthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate deathDate;

    private Boolean approxDeathDate;
    private Boolean death;
    private Castrated castrated;
    private String bornLocation;
    private Long bornLat;
    private Long bornLong;
    private Long bornHeight;
    private Sex sex;
    private String mainColorHex;
    private Long breedId;
    private int numberOfPhotos;
}
