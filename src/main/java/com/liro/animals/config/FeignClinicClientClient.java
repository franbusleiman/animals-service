package com.liro.animals.config;

import com.liro.animals.dto.ApiResponse;
import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.dto.requests.ClinicClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "clinics-service")
public interface FeignClinicClientClient {

    @PostMapping(value = "/clinicClients")
    ResponseEntity<ApiResponse> createClinicClient(@RequestBody ClinicClientDTO clinicClientDto);

}
