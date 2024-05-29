package com.liro.animals.model.dbentities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "animal_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_data_index_id", nullable = false)
    private AnimalDataIndex animalDataIndex;

    private String animalIndexValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_data_type_id", nullable = false)
    private AnimalDataType animalDataType;

    @Column(nullable = false)
    private String animalDataValue;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}