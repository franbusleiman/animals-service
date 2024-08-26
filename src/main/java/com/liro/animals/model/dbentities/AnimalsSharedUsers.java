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

    @EmbeddedId
    private  AnimalsSharedUserIdIdClass id;

    private Boolean readOnly;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalsSharedUsers that = (AnimalsSharedUsers) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(readOnly, that.readOnly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, readOnly);
    }
}