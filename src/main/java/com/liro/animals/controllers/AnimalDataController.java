package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalDataDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.requests.BAGId;
import com.liro.animals.dto.responses.AnimalDataResponse;
import com.liro.animals.service.AnimalDataService;
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
@RequestMapping("/breeds/animalData")
public class AnimalDataController {

    private final AnimalDataService animalDataService;

    @Autowired
    public AnimalDataController(AnimalDataService animalDataService) {
        this.animalDataService = animalDataService;
    }

    @GetMapping(value = "/{animalDataId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalDataResponse> getAnimalData(@PathVariable("animalDataId") Long animalDataId) {
        return ResponseEntity.ok(animalDataService.getAnimalDataResponse(animalDataId));
    }

    @GetMapping(value = "/getAllByBAGId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataResponse>> getAllByBAGId(@RequestParam(value = "breedId", required = false) Long breedId,
                                                                  @RequestParam(value = "animalTypeId", required = false) Long animalTypeId,
                                                                  @RequestParam(value = "groupId", required = false) Long groupId,
                                                                  Pageable pageable) {
        return ResponseEntity.ok(animalDataService.findAllByBAGId(BAGId.builder()
                .breedId(breedId)
                .animalTypeId(animalTypeId)
                .groupId(groupId)
                .build(), pageable));
    }

    @GetMapping(value = "/getAllByDataIndexType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataResponse>> getAllByDataIndexType(@RequestParam("dataIndexType") String dataIndexType,
                                                                          @RequestParam(value = "breedId", required = false) Long breedId,
                                                                          @RequestParam(value = "animalTypeId", required = false) Long animalTypeId,
                                                                          @RequestParam(value = "groupId", required = false) Long groupId,
                                                                          Pageable pageable) {
        return ResponseEntity.ok(animalDataService.getAllByAnimalDataIndexType(dataIndexType, BAGId.builder()
                .breedId(breedId)
                .animalTypeId(animalTypeId)
                .groupId(groupId)
                .build(), pageable));
    }

    @GetMapping(value = "/getAllByDataType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataResponse>> getAllByDataType(@RequestParam("dataType") String dataType,
                                                                     @RequestParam(value = "breedId", required = false) Long breedId,
                                                                     @RequestParam(value = "animalTypeId", required = false) Long animalTypeId,
                                                                     @RequestParam(value = "groupId", required = false) Long groupId,
                                                                     Pageable pageable) {
        return ResponseEntity.ok(animalDataService.getAllByAnimalDataType(dataType, BAGId.builder()
                .breedId(breedId)
                .animalTypeId(animalTypeId)
                .groupId(groupId)
                .build(), pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimalData(@Valid @RequestBody AnimalDataDTO animalDataDto) {
        AnimalDataResponse animalDataResponse = animalDataService.createAnimalData(animalDataDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/breeds/animalData/{animalDataId}")
            .buildAndExpand(animalDataResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "AnimalData created successfully"));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateAnimalData(@Valid @RequestBody AnimalDataDTO animalDataDto,
                                                 @RequestParam("animalDataId") Long animalDataId) {
        animalDataService.updateAnimalData(animalDataDto, animalDataId);

        return ResponseEntity.ok().build();
    }
}