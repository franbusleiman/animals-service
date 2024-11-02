package com.liro.animals.model.dbentities;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "breeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String formalName;
    private String details;
    private String countryOfOrigin;


    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_type_id", nullable = false)
    private AnimalType animalType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "breed")
    private Set<Animal> animals;

    @ElementCollection
    @CollectionTable(name = "breeds_alternative_names", joinColumns = @JoinColumn(name = "breed_id"))
    @Column(name = "alternative_names")
    private List<String> alternativeNames = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "breeds_groups",
            joinColumns = @JoinColumn(name = "breed_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private Set<Group> groups;

}
