package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import com.liro.animals.service.AnimalExtraClinicsService;
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
@RequestMapping("/clinics")
public class AnimalExtraClinicsController {

    private AnimalExtraClinicsService animalExtraClinicsService;
    public AnimalExtraClinicsController(AnimalExtraClinicsService animalExtraClinicsService) {
        this.animalExtraClinicsService = animalExtraClinicsService;
    }



    @GetMapping(value = "/findByClinicId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalClinicResponse>> getExtraClinicsRelationsByClinicId(Pageable pageable, @RequestParam("clinicId") Long clinicId,
                                                                                         @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalExtraClinicsService.getExtraClinicsRelationsByClinicId(pageable,clinicId, getUser(token, null)));
    }

    @GetMapping(value = "/findByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalClinicResponse>> getExtraClinicsRelationsByAnimalId(Pageable pageable, @RequestParam("animalId") Long animalId,
                                                                                         @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalExtraClinicsService.getExtraClinicsRelationsByClinicId( pageable,animalId, getUser(token, null)));
    }

//    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AnimalClinicDTO animalClinicDTO,
//                                                    @RequestHeader(name = "Authorization",  required = false) String token) {
//
//        AnimalClinicResponse animalExtraClinicResponse = animalExtraClinicsService.addExtraClinic(animalClinicDTO, getUser(token, null));
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/clinics/{clinicId}")
//                .buildAndExpand(animalExtraClinicResponse.getId()).toUri();
//
//        return ResponseEntity.created(location).body(
//                new ApiResponse(true, "Clinic added successfully"));
//    }

    @PostMapping(value = "/addClinic",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addClinic(@Valid @RequestBody AnimalClinicDTO animalClinicDTO,
                                                 @RequestHeader(name = "Authorization", required = false) String token){

        animalExtraClinicsService.addClinic(animalClinicDTO, token, animalClinicDTO.getClinicId());

        return new ApiResponse(true, "Clinic added successfully");
    }
}
