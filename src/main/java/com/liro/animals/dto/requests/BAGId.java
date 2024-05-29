package com.liro.animals.dto.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BAGId {

    private Long breedId;
    private Long animalTypeId;
    private Long groupId;
}
