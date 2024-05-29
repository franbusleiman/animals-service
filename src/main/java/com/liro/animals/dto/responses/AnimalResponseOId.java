package com.liro.animals.dto.responses;

import com.liro.animals.dto.AnimalDTO;
import lombok.Getter;
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
public class AnimalResponseOId extends AnimalDTO {

    private Long id;

    private Long ownerClientProfileId;
}