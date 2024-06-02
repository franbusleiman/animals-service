package com.liro.animals.controllers;


import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.RecordTypeDTO;
import com.liro.animals.dto.responses.RecordTypeResponse;
import com.liro.animals.service.RecordTypeService;
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
@RequestMapping("/recordTypes")
public class RecordTypesController {

    private final RecordTypeService recordTypeService;

    @Autowired
    public RecordTypesController(RecordTypeService recordTypeService) {
        this.recordTypeService = recordTypeService;
    }

    @GetMapping(value = "/{recordTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RecordTypeResponse> getRecordType(@PathVariable("recordTypeId") Long recordTypeId) {
        return ResponseEntity.ok(recordTypeService.getRecordTypeResponse(recordTypeId));
    }

    @GetMapping(value = "/findAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<RecordTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(recordTypeService.findAll(pageable));
    }

    @GetMapping(value = "/findAllByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<RecordTypeResponse>> getByNameContaining(
        @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return ResponseEntity.ok(recordTypeService
            .findAllByRecordTypeContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createRecordType(@Valid @RequestBody RecordTypeDTO recordTypeDto) {
        if (recordTypeDto.getRecordType() != null) {
            recordTypeDto.setRecordType(recordTypeDto.getRecordType().toLowerCase());
        }
        RecordTypeResponse recordTypeResponse = recordTypeService.createRecordType(recordTypeDto);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/recordTypes/recordType/{recordTypeId}")
            .buildAndExpand(recordTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
            new ApiResponse(true, "RecordType created successfully"));
    }
}