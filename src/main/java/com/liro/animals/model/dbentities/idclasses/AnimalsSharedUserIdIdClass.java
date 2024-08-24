package com.liro.animals.model.dbentities.idclasses;


import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalsSharedUserIdIdClass implements Serializable {
    private Long animal;
    private Long userId;
}
