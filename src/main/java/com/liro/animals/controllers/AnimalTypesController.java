package com.liro.animals.controllers;

import com.liro.animals.dto.AnimalTypeDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalTypeResponse;
import com.liro.animals.service.AnimalTypeService;
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
@RequestMapping("/animalTypes")
public class AnimalTypesController {

    private final AnimalTypeService animalTypeService;

    @Autowired
    public AnimalTypesController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping(value = "/{animalTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalTypeResponse> getAnimalType(@PathVariable("animalTypeId") Long animalTypeId) {
        return ResponseEntity.ok(animalTypeService.getAnimalTypeResponse(animalTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(animalTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalTypeResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(animalTypeService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimalType(@Valid @RequestBody AnimalTypeDTO animalTypeDto) {
        AnimalTypeResponse animalTypeResponse = animalTypeService.createAnimalType(animalTypeDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/animalTypes/{animalTypeId}")
            .buildAndExpand(animalTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "AnimalType created successfully"));
    }
}