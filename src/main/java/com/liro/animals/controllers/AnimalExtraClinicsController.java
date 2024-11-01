package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalExtraClinicDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.responses.AnimalExtraClinicResponse;
import com.liro.animals.dto.responses.RecordResponse;
import com.liro.animals.service.AnimalExtraClinicsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.liro.animals.util.Util.getUser;

@RestController
@RequestMapping("/clinics")
public class AnimalExtraClinicsController {

    private AnimalExtraClinicsService animalExtraClinicsService;
    public AnimalExtraClinicsController(AnimalExtraClinicsService animalExtraClinicsService) {
        this.animalExtraClinicsService = animalExtraClinicsService;
    }



    @GetMapping(value = "/findByClinicId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalExtraClinicResponse>> getExtraClinicsRelationsByClinicId(Pageable pageable, @RequestParam("clinicId") Long clinicId,
                                                                                              @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalExtraClinicsService.getExtraClinicsRelationsByClinicId(pageable,clinicId, getUser(token, null)));
    }

    @GetMapping(value = "/findByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalExtraClinicResponse>> getExtraClinicsRelationsByAnimalId(Pageable pageable, @RequestParam("animalId") Long animalId,
                                                                                              @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalExtraClinicsService.getExtraClinicsRelationsByClinicId( pageable,animalId, getUser(token, null)));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AnimalExtraClinicDTO animalExtraClinicDTO,
                                                    @RequestHeader(name = "Authorization",  required = false) String token) {

        AnimalExtraClinicResponse animalExtraClinicResponse = animalExtraClinicsService.addExtraClinic(animalExtraClinicDTO, getUser(token, null));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/clinics/{clinicId}")
                .buildAndExpand(animalExtraClinicResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Clinic added successfully"));
    }

    @PostMapping(value = "/addClinic",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addClinic(@Valid @RequestBody AnimalExtraClinicDTO animalExtraClinicDTO,
                                                 @RequestHeader(name = "Authorization", required = false) String token){
        System.out.println("------------ CONTROLADOR INICIADO------------");

        AnimalExtraClinicResponse animalExtraClinicResponse = animalExtraClinicsService.addExtraClinic(animalExtraClinicDTO, getUser(token, null));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{clinicId}")
                .buildAndExpand(animalExtraClinicResponse.getId())
                .toUri();

        System.out.println("------------ RETORNANDO ------------");

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Clinic added successfully"));
    }
}
