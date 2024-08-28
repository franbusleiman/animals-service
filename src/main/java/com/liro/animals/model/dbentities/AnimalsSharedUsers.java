package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdClass;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "animals_shared_users")
@IdClass(AnimalsSharedUserIdClass.class)
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Animal animal;


}