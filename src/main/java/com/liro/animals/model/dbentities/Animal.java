package com.liro.animals.model.dbentities;

import com.liro.animals.model.enums.Castrated;
import com.liro.animals.model.enums.Sex;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "animals",
        indexes = {
                @Index(name = "owner_user_index", columnList = "ownerUserId"),
                @Index(name = "main_clinic_index", columnList = "mainClinicId")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(nullable = false)
    private Boolean death;
    private String deathCause;
    private Boolean validBreed;
    private String profilePictureURL;


    @NotNull
    private Boolean validCastrated = false;

    private Boolean validBirthDate = false;
    private String bornLocation;
    private Long bornLat;
    private Long bornLong;
    private Long bornHeight;
    private Integer numberOfPhotos;

    @NotNull
    private Boolean isPublic;
    private String vetterCode;

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

    @OneToMany(mappedBy = "animal")
    private Set<AnimalsSharedUsers> sharedWith = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animal")
    private Set<AnimalsExtraClinics> extraClinics = new HashSet<>();

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "animal")
    private Set<Record> records = new HashSet<>();

    private Long mainClinicId;

    private Long ownerUserId;
}
