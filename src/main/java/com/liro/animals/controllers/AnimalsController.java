package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesWADTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import com.liro.animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.liro.animals.util.Util.getUser;


@RestController
@RequestMapping("/animals")
public class AnimalsController {

    private final AnimalService animalService;

    @Autowired
    public AnimalsController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping(value = "/{animalId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalResponse> getAnimal(@PathVariable("animalId") Long animalId,
                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(animalService.getAnimalResponse(animalId, getUser(token)));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalResponse>> getAnimalsBySearchCriteria(Pageable pageable,
                                                                           @RequestParam("param") String param,
                                                                           @RequestHeader(name = "Authorization", required = false) String token) {


        try {
            return ResponseEntity.ok(animalService.getAnimalsByOwnerDni(pageable, Long.valueOf(param)));
        } catch (NumberFormatException e) {

            return ResponseEntity.ok(animalService.getAnimalsByNameAndVetId(pageable, param, getUser(token)));
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAnimal(@Valid @RequestBody AnimalDTO animalRequest,
                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        AnimalResponse animalResponse = animalService.createAnimal(animalRequest, getUser(token));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/animals/{animalId}")
                .buildAndExpand(animalResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Animal created successfully"));
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateAnimal(@Valid @RequestBody AnimalDTO animalRequest,
                                             @RequestParam("animalId") Long animalId,
                                             @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.updateAnimal(animalRequest, animalId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{animalId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteAnimal(@PathVariable("animalId") Long animalId,
                                             @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.deleteAnimal(animalId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/findAllShared", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalsSharedClientProfilesWADTO>> getSharedAnimals(Pageable pageable,
                                                                                   @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(animalService.getSharedAnimals(pageable, getUser(token)));
    }

    @GetMapping(value = "/findAllOwn", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalResponse>> getOwnAnimals(Pageable pageable, @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(animalService.getOwnAnimals(pageable, getUser(token)));
    }

    @PostMapping(value = "/changeShareState", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeShareStateAnimal(@RequestParam("animalId") Long animalId,
                                                       @RequestParam("shareToEmail") String shareToEmail,
                                                       @RequestParam("readOnly") boolean readOnly,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.changeShareStateAnimal(animalId, shareToEmail, readOnly, getUser(token));

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/hasPermissions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> hasPermissions(@RequestParam("animalId") Long animalId,
                                               @RequestParam("needWritePermissions") Boolean needWritePermissions,
                                               @RequestParam("onlyOwner") Boolean onlyOwner,
                                               @RequestParam("vetEnabled") Boolean vetEnabled,
                                               @RequestParam("onlyVet") Boolean onlyVet,
                                               @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.hasPermissions(animalId, getUser(token), needWritePermissions,
                onlyOwner, vetEnabled, onlyVet);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changeOwner", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeOwner(@RequestParam("animalId") Long animalId,
                                            @RequestParam("emailToTransfer") String emailToTransfer,
                                            @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.changeOwner(animalId, emailToTransfer, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/unshare", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> unshareAnimal(@RequestParam("animalId") Long animalId,
                                              @RequestParam("shareToEmail") String shareToEmail,
                                              @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.removeShareAnimal(animalId, shareToEmail, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/increaseNumberOfPhotos", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> increaseNumberOfPhotos(@RequestParam("animalId") Long animalId,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.increaseNumberOfPhotos(animalId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/decreaseNumberOfPhotos", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> decreaseNumberOfPhotos(@RequestParam("animalId") Long animalId,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.decreaseNumberOfPhotos(animalId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/addColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addColor(@RequestParam("animalId") Long animalId,
                                         @RequestParam("animalColorId") Long animalColorId,
                                         @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.addColor(animalId, animalColorId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/removeColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> removeColor(@RequestParam("animalId") Long animalId,
                                            @RequestParam("animalColorId") Long animalColorId,
                                            @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.removeColor(animalId, animalColorId, getUser(token));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changeMainColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeMainColor(@RequestParam("animalId") Long animalId,
                                                @RequestParam("animalColorId") Long animalColorId,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.changeMainColor(animalId, animalColorId, getUser(token));

        return ResponseEntity.ok().build();
    }
}