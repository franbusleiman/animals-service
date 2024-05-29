package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdIdClass;
import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "breeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String formalName;
    private String details;
    private String countryOfOrigin;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "breed")
    private Set<AnimalData> animalData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_type_id", nullable = false)
    private AnimalType animalType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "breed")
    private Set<Animal> animals;

    @ManyToMany
    @JoinTable(
            name = "breeds_groups",
            joinColumns = @JoinColumn(name = "breed_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private Set<Group> groups;

}
