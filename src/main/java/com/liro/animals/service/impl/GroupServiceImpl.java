package com.liro.animals.service.impl;


import com.liro.animals.dto.GroupDTO;
import com.liro.animals.dto.mappers.GroupMapper;
import com.liro.animals.dto.responses.GroupResponse;
import com.liro.animals.exceptions.ConflictException;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.AnimalType;
import com.liro.animals.model.dbentities.Breed;
import com.liro.animals.model.dbentities.Group;
import com.liro.animals.repositories.AnimalTypeRepository;
import com.liro.animals.repositories.BreedRepository;
import com.liro.animals.repositories.GroupRepository;
import com.liro.animals.service.GroupService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final BreedRepository breedRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final GroupMapper groupMapper;
    private final UserService userService;
    private final Util util;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            BreedRepository breedRepository,
                            AnimalTypeRepository animalTypeRepository,
                            GroupMapper groupMapper,
                            UserService userService,
                            Util util) {
        this.groupRepository = groupRepository;
        this.breedRepository = breedRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.groupMapper = groupMapper;
        this.userService = userService;
        this.util = util;
    }

    @Override
    public Page<GroupResponse> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable)
            .map(groupMapper::groupToGroupResponse);
    }

    @Override
    public Page<GroupResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return groupRepository.findAllByGroupNameContaining(nameContaining, pageable)
            .map(groupMapper::groupToGroupResponse);
    }

    @Override
    public Page<GroupResponse> findAllByBreedId(Long breedId, Pageable pageable) {
        return groupRepository.findAllByBreedsId(breedId, pageable)
            .map(groupMapper::groupToGroupResponse);
    }

    @Override
    public Page<GroupResponse> findAllByAnimalTypeId(Long animalTypeId, Pageable pageable) {
        return groupRepository.findAllByAnimalTypesId(animalTypeId, pageable)
            .map(groupMapper::groupToGroupResponse);
    }

    @Override
    public GroupResponse getGroupResponse(Long groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        return groupMapper.groupToGroupResponse(group);
    }

    @Override
    public GroupResponse createGroup(GroupDTO groupDto) {
        if (groupDto.getGroupName() != null) {
            groupDto.setGroupName(groupDto.getGroupName().toLowerCase());
        }

        Group group = groupMapper.groupDtoToGroup(groupDto);
        if (groupRepository.existsByGroupName(groupDto.getGroupName())) {
            throw new ConflictException("Group with name: "
                + groupDto.getGroupName() + ", already exists");
        }

        return groupMapper.groupToGroupResponse(
            groupRepository.save(group)
        );
    }

    @Override
    public void addBreedToGroup(Long breedId, Long groupId) {
        Breed breed = breedRepository.findById(breedId)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: " + breedId));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        if (group.getBreeds() == null) group.setBreeds(new HashSet<>());
        group.getBreeds().add(breed);

        if (breed.getGroups() == null) breed.setGroups(new HashSet<>());
        breed.getGroups().add(group);

        groupRepository.save(group);
    }

    @Override
    public void removeBreedFromGroup(Long breedId, Long groupId) {
        Breed breed = breedRepository.findById(breedId)
            .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: " + breedId));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        group.getBreeds().remove(breed);
        breed.getGroups().remove(group);

        groupRepository.save(group);
    }

    @Override
    public void addAnimalTypeToGroup(Long animalTypeId, Long groupId) {
        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalType not found with id: " + animalTypeId));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        group.getAnimalTypes().remove(animalType);
        animalType.getGroups().remove(group);

        groupRepository.save(group);
    }

    @Override
    public void removeAnimalTypeFromGroup(Long animalTypeId, Long groupId) {
        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalType not found with id: " + animalTypeId));

        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        if (group.getAnimalTypes() == null) group.setAnimalTypes(new HashSet<>());
        group.getAnimalTypes().add(animalType);

        if (animalType.getGroups() == null) animalType.setGroups(new HashSet<>());
        animalType.getGroups().add(group);

        groupRepository.save(group);
    }
}