package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdIdClass;
import javax.persistence.*;

import lombok.*;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Id
    @Column(name = "user_id",nullable = false)
    private Long userId;

    private Boolean readOnly;

}