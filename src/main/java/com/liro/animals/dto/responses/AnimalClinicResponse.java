package com.liro.animals.dto.responses;


import com.liro.animals.dto.AnimalClinicDTO;
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
public class AnimalClinicResponse extends AnimalClinicDTO {


    private Long id;
}
