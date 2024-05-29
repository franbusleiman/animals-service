package com.liro.animals.model.dbentities;

import com.liro.animals.model.enums.RelationType;
import javax.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "animal_relation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private RelationType relationType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_relation_of_id", nullable = false)
    private Animal animalRelationOf;
}