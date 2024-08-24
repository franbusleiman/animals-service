package com.liro.animals.model.dbentities.idclasses;


import com.liro.animals.model.dbentities.Animal;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AnimalsSharedUserIdIdClass implements Serializable {

    private Long animal;
    private Long userId;


    public AnimalsSharedUserIdIdClass() {
    }

    public AnimalsSharedUserIdIdClass(Long animal, Long userId) {
        this.animal= animal;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalsSharedUserIdIdClass that = (AnimalsSharedUserIdIdClass) o;
        return Objects.equals(animal, that.animal) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal, userId);
    }
}
