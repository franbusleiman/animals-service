package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalDTO;
import com.liro.animals.dto.AnimalMigratorDTO;
import com.liro.animals.dto.AnimalsSharedClientProfilesWADTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalCompleteResponse;
import com.liro.animals.dto.responses.AnimalMigrationResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import com.liro.animals.service.AnimalService;
import com.liro.animals.service.AnimalsSharedUsersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.List;

import static com.liro.animals.util.Util.getUser;


@RestController
@RequestMapping("/animals")
public class AnimalsController {

    private final AnimalService animalService;
    private final AnimalsSharedUsersService animalsSharedUsersService;

    @Autowired
    public AnimalsController(AnimalService animalService,
                             AnimalsSharedUsersService animalsSharedUsersService) {
        this.animalService = animalService;
        this.animalsSharedUsersService = animalsSharedUsersService;
    }

    @GetMapping(value = "/{animalId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalResponse> getAnimal(@PathVariable("animalId") Long animalId,
                                                    @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(animalService.getAnimalResponse(animalId, getUser(token, clinicId)));
    }

    @ApiPageable
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalCompleteResponse>> getAnimalsBySearchCriteria(Pageable pageable,
                                                                                   @RequestParam("param") String param,
                                                                                   @RequestHeader(name = "clinicId", required = false) Long clinicId,

                                                                                   @RequestHeader(name = "Authorization", required = false) String token) {


        System.out.println("Page number: " + pageable.getPageNumber());
        System.out.println("Page size: " + pageable.getPageSize());
            try {
                return ResponseEntity.ok(animalService.getAnimalsByOwnerDni(pageable, Long.valueOf(param), getUser(token, clinicId)));
            } catch (NumberFormatException e) {

                return ResponseEntity.ok(animalService.getAnimalsByNameAndVetId(pageable, param, getUser(token, clinicId)));
            }

    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/user/{userId}")
    public ResponseEntity<List<AnimalCompleteResponse>> getAnimalsByUserId(@PathVariable("userId") Long userId) {

            return ResponseEntity.ok(animalService.getAnimalsByUserId(userId));

    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AnimalResponse> createAnimal(@Valid @RequestBody AnimalDTO animalRequest,
                                                    @RequestHeader(name = "clinicId", required = false) Long clinicId,

                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        AnimalResponse animalResponse = animalService.createAnimal(animalRequest, getUser(token, clinicId));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/animals/{animalId}")
                .buildAndExpand(animalResponse.getId()).toUri();

        return ResponseEntity.created(location).body(animalResponse);
    }

    @PostMapping(value = "/migrate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AnimalMigrationResponse>> migrateAnimals(@Valid @RequestBody List<AnimalMigratorDTO> animalMigratorDTOList, @RequestParam("vetClinicId") Long vetClinicId) {

        return ResponseEntity.ok().body(animalService.migrateAnimals(animalMigratorDTOList, vetClinicId));
    }


    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateAnimal(@Valid @RequestBody AnimalDTO animalRequest,
                                             @RequestParam("animalId") Long animalId,
                                             @RequestHeader(name = "clinicId", required = false) Long clinicId,

                                             @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.updateAnimal(animalRequest, animalId,token, clinicId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{animalId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteAnimal(@PathVariable("animalId") Long animalId,
                                             @RequestHeader(name = "clinicId", required = false) Long clinicId,

                                             @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.deleteAnimal(animalId, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/findAllShared", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalsSharedClientProfilesWADTO>> getSharedAnimals(Pageable pageable,
                                                                                   @RequestHeader(name = "clinicId", required = false) Long clinicId,

                                                                                   @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(animalService.getSharedAnimals(pageable, getUser(token, clinicId)));
    }

    @GetMapping(value = "/findAllOwn", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalResponse>> getOwnAnimals(Pageable pageable,
                                                              @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                              @RequestHeader(name = "Authorization", required = false) String token) {

        return ResponseEntity.ok(animalService.getOwnAnimals(pageable, getUser(token, clinicId)));
    }

    @PostMapping(value = "/changeShareState", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeShareStateAnimal(@RequestParam("animalId") Long animalId,
                                                       @RequestParam("shareToEmail") String shareToEmail,
                                                       @RequestParam("readOnly") boolean readOnly,
                                                       @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalsSharedUsersService.createRelation(animalId,readOnly, shareToEmail, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/hasPermissions", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> hasPermissions(@RequestParam("animalId") Long animalId,
                                               @RequestParam("needWritePermissions") Boolean needWritePermissions,
                                               @RequestParam("onlyOwner") Boolean onlyOwner,
                                               @RequestParam("onlyVet") Boolean onlyVet,
                                               @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                               @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.hasPermissions(animalId, token, clinicId, needWritePermissions,
                onlyOwner, onlyVet);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changeOwner", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeOwner(@RequestParam("animalId") Long animalId,
                                            @RequestParam("emailToTransfer") String emailToTransfer,
                                            @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                            @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.changeOwner(animalId, emailToTransfer, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/unshare", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> unshareAnimal(@RequestParam("animalId") Long animalId,
                                              @RequestParam("shareToEmail") String shareToEmail,
                                              @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                              @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.removeShareAnimal(animalId, shareToEmail, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/increaseNumberOfPhotos", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> increaseNumberOfPhotos(@RequestParam("animalId") Long animalId,
                                                       @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.increaseNumberOfPhotos(animalId, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/decreaseNumberOfPhotos", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> decreaseNumberOfPhotos(@RequestParam("animalId") Long animalId,
                                                       @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                       @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.decreaseNumberOfPhotos(animalId, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/addColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> addColor(@RequestParam("animalId") Long animalId,
                                         @RequestParam("animalColorId") Long animalColorId,
                                         @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                         @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.addColor(animalId, animalColorId, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/removeColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> removeColor(@RequestParam("animalId") Long animalId,
                                            @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                            @RequestParam("animalColorId") Long animalColorId,
                                            @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.removeColor(animalId, animalColorId, token, clinicId);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/changeMainColor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeMainColor(@RequestParam("animalId") Long animalId,
                                                @RequestParam("animalColorId") Long animalColorId,
                                                @RequestHeader(name = "clinicId", required = false) Long clinicId,
                                                @RequestHeader(name = "Authorization", required = false) String token) {
        animalService.changeMainColor(animalId, animalColorId, token, clinicId);

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