package com.liro.animals.model.dbentities;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animal_colors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String hex;

    @ManyToMany(mappedBy = "colors")
    private Set<Animal> animals = new HashSet<>();

    @OneToMany(mappedBy = "mainColor")
    private Set<Animal> mainColorOf = new HashSet<>();

}
