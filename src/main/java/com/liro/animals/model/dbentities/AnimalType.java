package com.liro.animals.model.dbentities;

import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "animal_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String formalName;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "animalType")
    private Set<Breed> breeds;
}