package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdIdClass;
import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String groupName;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "group")
    private Set<AnimalData> animalData;

    @ManyToMany(mappedBy = "groups")
    private Set<AnimalType> animalTypes;

    @ManyToMany(mappedBy = "groups")
    private Set<Breed> breeds;
}