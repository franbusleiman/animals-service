package com.liro.animals.controllers;


import com.liro.animals.dto.AnimalClinicDTO;
import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.responses.AnimalClinicResponse;
import com.liro.animals.service.AnimalClinicsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.liro.animals.util.Util.getUser;

@RestController
@RequestMapping("/clinics")
public class AnimalClinicsController {

    private AnimalClinicsService animalClinicsService;
    public AnimalClinicsController(AnimalClinicsService animalClinicsService) {
        this.animalClinicsService = animalClinicsService;
    }



    @GetMapping(value = "/findByClinicId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalClinicResponse>> getExtraClinicsRelationsByClinicId(Pageable pageable, @RequestParam("clinicId") Long clinicId,
                                                                                         @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalClinicsService.getExtraClinicsRelationsByClinicId(pageable,clinicId, getUser(token, null)));
    }

    @GetMapping(value = "/findByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<AnimalClinicResponse>> getExtraClinicsRelationsByAnimalId(Pageable pageable, @RequestParam("animalId") Long animalId,
                                                                                         @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(animalClinicsService.getExtraClinicsRelationsByClinicId( pageable,animalId, getUser(token, null)));
    }

    @PostMapping(value = "/addClinic",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addClinic(@Valid @RequestBody AnimalClinicDTO animalClinicDTO,
                                                 @RequestHeader(name = "Authorization", required = false) String token){

        animalClinicsService.addClinic(animalClinicDTO, getUser(token, null));

        return new ApiResponse(true, "Clinic added successfully");
    }
}
