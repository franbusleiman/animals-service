package com.liro.animals.dto;

import com.liro.animals.dto.responses.AnimalResponseOId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
public class AnimalsSharedClientProfilesWADTO {

    private boolean readOnly;

    private AnimalResponseOId animal;
}
