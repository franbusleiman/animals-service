package com.liro.animals.controllers;

import com.liro.animals.dto.AnimalDataTypeDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalDataTypeResponse;
import com.liro.animals.service.AnimalDataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/animalDataTypes/animalDataType")
public class AnimalDataTypesController {

    private final AnimalDataTypeService animalDataTypeService;

    @Autowired
    public AnimalDataTypesController(AnimalDataTypeService animalDataTypeService) {
        this.animalDataTypeService = animalDataTypeService;
    }

    @GetMapping(value = "/{animalDataTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalDataTypeResponse> getAnimalDataType(@PathVariable("animalDataTypeId") Long animalDataTypeId) {
        return ResponseEntity.ok(animalDataTypeService.getAnimalDataTypeResponse(animalDataTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(animalDataTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataTypeResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(animalDataTypeService
            .findAllByAnimalDataTypeContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimalDataType(@Valid @RequestBody AnimalDataTypeDTO animalDataTypeDto) {
        AnimalDataTypeResponse animalDataTypeResponse = animalDataTypeService.createAnimalDataType(animalDataTypeDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/animalDataTypes/animalDataType/{animalDataTypeId}")
            .buildAndExpand(animalDataTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "AnimalDataType created successfully"));
    }
}