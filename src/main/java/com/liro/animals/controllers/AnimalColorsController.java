package com.liro.animals.controllers;



import com.liro.animals.dto.AnimalColorDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalColorResponse;
import com.liro.animals.service.AnimalColorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.List;

import static com.liro.animals.util.Util.getUser;

@RestController
@RequestMapping("/animalColors")
public class AnimalColorsController {

    private final AnimalColorService animalColorService;

    @Autowired
    public AnimalColorsController(AnimalColorService animalColorService) {
        this.animalColorService = animalColorService;
    }

    @GetMapping(value = "/{animalColorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalColorResponse> getAnimalColor(@PathVariable("animalColorId") Long animalColorId) {
        return ResponseEntity.ok(animalColorService.getAnimalColorResponse(animalColorId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalColorResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(animalColorService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalColorResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(animalColorService.findAllByNameContaining(nameContaining, pageable));
    }

    @GetMapping(value = "/getAllByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AnimalColorResponse>> getAllByAnimalId(@RequestParam("animalId") Long animalId,
             @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(animalColorService.findAllByAnimalId(animalId, getUser(token)));
    }

    @GetMapping(value = "/getMainColorByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalColorResponse> getMainColorByAnimalId(@RequestParam("animalId") Long animalId,
                                                                      @RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok(animalColorService.findMainColorByAnimalId(animalId, getUser(token)));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimalColor(@Valid @RequestBody AnimalColorDTO animalColorDto) {
        AnimalColorResponse animalColorResponse = animalColorService.createAnimalColor(animalColorDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/animalColors/{animalColorId}")
            .buildAndExpand(animalColorResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "Animal color created successfully"));
    }
}