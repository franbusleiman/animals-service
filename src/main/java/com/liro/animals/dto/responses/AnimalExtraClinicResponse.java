package com.liro.animals.dto.responses;


import com.liro.animals.dto.AnimalExtraClinicDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalExtraClinicResponse extends AnimalExtraClinicDTO {


    private Long id;
}
