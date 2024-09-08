package com.liro.animals.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liro.animals.model.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalMigratorDTO {
    private String name;
    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthDate;
    private Boolean death;
    private Sex sex;
    private String breed;
    private Long ownerUserId;
}
