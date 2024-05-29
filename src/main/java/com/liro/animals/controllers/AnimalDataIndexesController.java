package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalDataIndexDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalDataIndexResponse;
import com.liro.animals.service.AnimalDataIndexService;
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
@RequestMapping("/breeds/animalDataIndex")
public class AnimalDataIndexesController {

    private final AnimalDataIndexService animalDataIndexService;

    @Autowired
    public AnimalDataIndexesController(AnimalDataIndexService animalDataIndexService) {
        this.animalDataIndexService = animalDataIndexService;
    }

    @GetMapping(value = "/{animalDataIndexId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalDataIndexResponse> getAnimalDataIndex(@PathVariable("animalDataIndexId") Long animalDataIndexId) {
        return ResponseEntity.ok(animalDataIndexService.getAnimalDataIndexResponse(animalDataIndexId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataIndexResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(animalDataIndexService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalDataIndexResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(animalDataIndexService
            .findAllByAnimalDataIndexTypeContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimalDataIndex(@Valid @RequestBody AnimalDataIndexDTO animalDataIndexDto) {
        AnimalDataIndexResponse animalDataIndexResponse = animalDataIndexService.createAnimalDataIndex(animalDataIndexDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/breeds/animalDataIndex/{animalDataIndexId}")
            .buildAndExpand(animalDataIndexResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "AnimalDataIndex created successfully"));
    }
}