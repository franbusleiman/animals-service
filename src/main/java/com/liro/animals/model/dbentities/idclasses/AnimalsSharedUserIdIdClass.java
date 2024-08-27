package com.liro.animals.model.dbentities.idclasses;


import com.liro.animals.model.dbentities.Animal;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
public class AnimalsSharedUserIdIdClass implements Serializable {

    private Long animalId;

    private Long userId;

    public AnimalsSharedUserIdIdClass() {
    }

    public AnimalsSharedUserIdIdClass(Long animalId, Long userId) {
        this.animalId = animalId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalsSharedUserIdIdClass that = (AnimalsSharedUserIdIdClass) o;
        return Objects.equals(animalId, that.animalId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, userId);
    }
}

