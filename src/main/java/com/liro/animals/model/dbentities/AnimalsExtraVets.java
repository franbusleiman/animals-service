package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsExtraVetsIdClass;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "animals_extra_vets")
@IdClass(AnimalsExtraVetsIdClass.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalsExtraVets {

    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Id
    private Long vetUserId;
}