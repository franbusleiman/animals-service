package com.liro.animals.model.dbentities;

import javax.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animal_data_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDataType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String animalDataType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "animalDataType")
    private Set<AnimalData> animalDataSet = new HashSet<>();

}