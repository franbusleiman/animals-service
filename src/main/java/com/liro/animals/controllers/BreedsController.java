package com.liro.animals.controllers;


import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.BreedDTO;
import com.liro.animals.dto.responses.BreedResponse;
import com.liro.animals.service.BreedService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

@RestController
@RequestMapping("/breeds")
public class BreedsController {

    private final BreedService breedService;

    @Autowired
    public BreedsController(BreedService breedService) {
        this.breedService = breedService;
    }

    @GetMapping(value = "/{breedId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BreedResponse> getBreed(@PathVariable("breedId") Long breedId) {
        return ResponseEntity.ok(breedService.getBreedResponse(breedId));
    }

    @ApiPageable
    @GetMapping(value = "/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BreedResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(breedService.findAll(pageable));
    }
    @ApiPageable
    @GetMapping(value = "/findAllByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BreedResponse>> getByNameContaining(@RequestParam("nameContaining") String nameContaining,
                                                                   Pageable pageable) {
        return ResponseEntity.ok(breedService.findAllByNameContaining(nameContaining, pageable));
    }

    @ApiPageable
    @GetMapping(value = "/findAllByAnimalTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BreedResponse>> getAllByAnimalTypeId(@RequestParam("animalTypeId") Long animalTypeId,
                                                                    Pageable pageable) {
        return ResponseEntity.ok(breedService.findAllByAnimalTypeId(animalTypeId, pageable));
    }

    @ApiPageable
    @GetMapping(value = "/findAllByNameContainingAndAnimalTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<BreedResponse>> getAllByNameContainingAndAnimalTypeId(@RequestParam("nameContaining") String nameContaining,
                                                                                     @RequestParam("animalTypeId") Long animalTypeId,
                                                                                     Pageable pageable) {
        return ResponseEntity.ok(breedService.findAllByNameContainingAndAnimalTypeId(nameContaining, animalTypeId, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createBreed(@Valid @RequestBody BreedDTO breedDto) {
        BreedResponse breedResponse = breedService.createBreed(breedDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/breeds/{breedId}")
            .buildAndExpand(breedResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "Breed created successfully"));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateBreed(@Valid @RequestBody BreedDTO breedDto,
                                            @RequestParam("breedId") Long breedId) {
        breedService.updateBreed(breedDto, breedId);

        return ResponseEntity.ok().build();
    }


    @Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
    @interface ApiPageable {
    }
}