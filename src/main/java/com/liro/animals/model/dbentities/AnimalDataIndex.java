package com.liro.animals.model.dbentities;

import lombok.*;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animal_data_indexes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDataIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String animalDataIndexType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "animalDataIndex")
    private Set<AnimalData> animalDataSet = new HashSet<>();
}