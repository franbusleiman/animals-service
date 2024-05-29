package com.liro.animals.service;


import com.liro.animals.dto.GroupDTO;
import com.liro.animals.dto.responses.GroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    Page<GroupResponse> findAll(Pageable pageable);

    GroupResponse getGroupResponse(Long groupId);

    Page<GroupResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    Page<GroupResponse> findAllByBreedId(Long breedId, Pageable pageable);

    Page<GroupResponse> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable);

    GroupResponse createGroup(GroupDTO groupDTO);

    void addBreedToGroup(Long breedId, Long groupId);

    void removeBreedFromGroup(Long breedId, Long groupId);

    void addAnimalTypeToGroup(Long animalTypeId, Long groupId);

    void removeAnimalTypeFromGroup(Long breedId, Long groupId);
}
