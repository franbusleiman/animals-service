package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdClass;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "animals_shared_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalsSharedUsers {

    @EmbeddedId
    private AnimalsSharedUserIdClass animalsSharedUserIdClass;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private Boolean readOnly;
}