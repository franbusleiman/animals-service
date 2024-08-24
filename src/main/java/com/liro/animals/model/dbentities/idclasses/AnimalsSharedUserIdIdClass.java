package com.liro.animals.model.dbentities.idclasses;


import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class AnimalsSharedUserIdIdClass implements Serializable {
    private Long animal;
    private Long userId;
}
