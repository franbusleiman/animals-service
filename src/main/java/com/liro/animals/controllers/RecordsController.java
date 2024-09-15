package com.liro.animals.controllers;


import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.responses.RecordResponse;
import com.liro.animals.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/records")
public class RecordsController {

    private final RecordService recordService;

    @Autowired
    public RecordsController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping(value = "/{recordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecordResponse> getRecord(@PathVariable("recordId") Long recordId,
                                                    @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(recordService.getRecordResponse(recordId, getUser(token)));
    }

    @GetMapping(value = "/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<RecordResponse>> getAll(@RequestParam("animalId") Long animalId,
                                                       @RequestHeader(name = "Authorization",  required = false) String token,
                                                       Pageable pageable) {
        return ResponseEntity.ok(recordService.findAllByAnimalId(animalId, getUser(token), pageable));
    }

    @GetMapping(value = "/findAllLastByAnimalId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<RecordResponse>> getAllLast(@RequestParam("animalId") Long animalId,
                                                           @RequestHeader(name = "Authorization",  required = false) String token,
                                                           Pageable pageable) {
        return ResponseEntity.ok(recordService.findAllLastByAnimalId(animalId, getUser(token), pageable));
    }

    @GetMapping(value = "/findAllByAnimalIdAndRecordTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<RecordResponse>> getAllByRecordType(@RequestParam("animalId") Long animalId,
                                                                   @RequestParam("recordTypeId") Long recordTypeId,
                                                                   @RequestHeader(name = "Authorization",  required = false) String token,
                                                                   Pageable pageable) {
        return ResponseEntity.ok(recordService
            .findAllByAnimalIdAndRecordTypeId(animalId, recordTypeId, getUser(token), pageable));
    }

    @GetMapping(value = "/findLastByAnimalIdAndRecordTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecordResponse> getLastByRecordType(@RequestParam("animalId") Long animalId,
                                                              @RequestParam("recordTypeId") Long recordTypeId,
                                                              @RequestHeader(name = "Authorization",  required = false) String token) {
        return ResponseEntity.ok(recordService.findLastByAnimalIdAndRecordTypeId(animalId, recordTypeId, getUser(token)));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createRecord(@Valid @RequestBody RecordDTO recordDto,
                                                    @RequestHeader(name = "Authorization",  required = false) String token) {
        RecordResponse recordResponse = recordService.createRecord(recordDto, getUser(token));

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/records/{recordId}")
            .buildAndExpand(recordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "Record created successfully"));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> migrateRecords(@Valid @RequestBody List<RecordDTO> recordDtos,
                                                    @RequestParam(name = "vetUserId",  required = false) Long vetUserId) {
        Void recordResponse = recordService.migrateRecords(recordDtos, vetUserId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{recordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteRecord(@PathVariable("recordId") Long recordId,
                                             @RequestHeader(name = "Authorization",  required = false) String token) {
        recordService.deleteRecord(recordId, getUser(token));

        return ResponseEntity.ok().build();
    }
}