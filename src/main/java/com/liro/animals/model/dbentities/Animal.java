package com.liro.animals.model.dbentities;

import com.liro.animals.model.enums.Castrated;
import com.liro.animals.model.enums.Sex;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "animals",
        indexes = {
                @Index(name = "owner_user_index", columnList = "ownerUserId"),
                @Index(name = "main_vet_user_index", columnList = "mainVetUserId")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(nullable = false)
    private LocalDate birthDate;

    private Boolean approxBirthDate;
    private LocalDate deathDate;
    private Boolean approxDeathDate;
    private Boolean disabled;
    private Boolean death;
    private String deathCause;
    private Boolean validBreed;
    private Boolean validCastrated;
    private Boolean validBirthDate = false;
    private String bornLocation;
    private Long bornLat;
    private Long bornLong;
    private Long bornHeight;
    private Integer numberOfPhotos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Castrated castrated;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToMany
    @JoinTable(
            name = "animals_animal_colors",
            joinColumns = @JoinColumn(name = "animal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "animal_color_id", referencedColumnName = "id")
    )
    private Set<AnimalColor> colors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "main_color_id")
    private AnimalColor mainColor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "breed_id", nullable = false) 
    private Breed breed;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, mappedBy = "animal")
    private Set<AnimalsSharedUsers> sharedWith = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animal")
    private Set<AnimalsExtraVets> extraVets = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animal")
    private Set<Record> records = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animal")
    private Set<AnimalRelation> relations = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animalRelationOf")
    private Set<AnimalRelation> relationsOf = new HashSet<>();

    private Long mainVetUserId;

    @Column(nullable = false)
    private Long ownerUserId;
}
