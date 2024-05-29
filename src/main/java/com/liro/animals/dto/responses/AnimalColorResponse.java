package com.liro.animals.dto.responses;

import com.liro.animals.dto.AnimalColorDTO;
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
public class AnimalColorResponse extends AnimalColorDTO {

    private Long id;
}
