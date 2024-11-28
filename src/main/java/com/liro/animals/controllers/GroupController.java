package com.liro.animals.controllers;


import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.GroupDTO;
import com.liro.animals.dto.responses.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = "/{groupId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GroupResponse> getGroup(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.getGroupResponse(groupId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<GroupResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(groupService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<GroupResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(groupService.findAllByNameContaining(nameContaining, pageable));
    }

    @GetMapping(value = "/getAllByBreedId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<GroupResponse>> getAllByBreedId(@RequestParam("breedId") Long breedId,
                                                               Pageable pageable) {
        return ResponseEntity.ok(groupService.findAllByBreedId(breedId, pageable));
    }

    @GetMapping(value = "/getAllByAnimalTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<GroupResponse>> getAllByAnimalTypeId(@RequestParam("animalTypeId") Long animalTypeId,
                                                                    Pageable pageable) {
        return ResponseEntity.ok(groupService.findAllByAnimalTypeId(animalTypeId, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createGroup(@Valid @RequestBody GroupDTO groupDto) {
        GroupResponse groupResponse = groupService.createGroup(groupDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/group/{groupId}")
            .buildAndExpand(groupResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "Group created successfully"));
    }

    @PutMapping(value = "/addBreedToGroup", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addBreedToGroup(@RequestParam("breedId") Long breedId,
                                                @RequestParam("groupId") Long groupId) {
        groupService.addBreedToGroup(breedId, groupId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/removeBreedToGroup", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> removeBreedToGroup(@RequestParam("breedId") Long breedId,
                                                   @RequestParam("groupId") Long groupId) {
        groupService.removeBreedFromGroup(breedId, groupId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/addAnimalTypeToGroup", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addAnimalTypeToGroup(@RequestParam("animalTypeId") Long animalTypeId,
                                                     @RequestParam("groupId") Long groupId) {
        groupService.addAnimalTypeToGroup(animalTypeId, groupId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/removeAnimalTypeToGroup", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> removeAnimalTypeToGroup(@RequestParam("animalTypeId") Long animalTypeId,
                                                        @RequestParam("groupId") Long groupId) {
        groupService.removeAnimalTypeFromGroup(animalTypeId, groupId);

        return ResponseEntity.ok().build();
    }
}