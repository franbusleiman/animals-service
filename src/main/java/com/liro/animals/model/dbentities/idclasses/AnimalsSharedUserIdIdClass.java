package com.liro.animals.model.dbentities.idclasses;


import com.liro.animals.model.dbentities.Animal;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
public class AnimalsSharedUserIdIdClass implements Serializable {

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    public AnimalsSharedUserIdIdClass() {
    }

    public AnimalsSharedUserIdIdClass(Animal animal, Long userId) {
        this.animal = animal;
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

