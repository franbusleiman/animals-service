package com.liro.animals.config;

import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.dto.requests.ClinicClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "clinics-service")
public interface FeignClinicClientClient {

    @RequestMapping(method = RequestMethod.POST, value = "/clinicClients")
    ResponseEntity<Void> createClinicClient(@RequestBody ClinicClientDTO clinicClientDTO,
                                         @RequestHeader(name = "Authorization", required = false) String token);
}
