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

    @EmbeddedId
    private  AnimalsSharedUserIdIdClass id;

    private Boolean readOnly;

}