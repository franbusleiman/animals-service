package com.liro.animals.dto.responses;

import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesDTO;
import com.liro.animals.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AnimalCompleteResponse extends AnimalDTO {

    private Long id;

    private UserResponseDTO owner;
    private Set<AnimalsSharedClientProfilesDTO> sharedWith;
    private boolean disabled;
    private boolean death;
}