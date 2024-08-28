package com.liro.animals.model.dbentities.idclasses;


import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
public class AnimalsSharedUserIdClass implements Serializable {

    private Long animalId;

    private Long userId;

    public AnimalsSharedUserIdClass() {
    }

    public AnimalsSharedUserIdClass(Long animalId, Long userId) {
        this.animalId = animalId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalsSharedUserIdClass that = (AnimalsSharedUserIdClass) o;
        return Objects.equals(animalId, that.animalId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, userId);
    }
}

