package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdIdClass;
import javax.persistence.*;

import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "animals_shared_users")
@IdClass(AnimalsSharedUserIdIdClass.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalsSharedUsers {

    @Id
    @Column(name = "animal_id", insertable = false, updatable = false)
    private Long animalId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    private Boolean readOnly;

    @ManyToOne(cascade = CascadeType.ALL)
    private Animal animal;


}