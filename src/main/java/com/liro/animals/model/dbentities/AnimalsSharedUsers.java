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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Long animalId;

    @Column(nullable = false)
    private Long userId;

    private Boolean readOnly;

}