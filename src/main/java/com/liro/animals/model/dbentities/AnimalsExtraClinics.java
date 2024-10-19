package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsExtraClinicsIdClass;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals_extra_clinics")
@IdClass(AnimalsExtraClinicsIdClass.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalsExtraClinics {

    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Id
    private Long clinicId;
}