package com.liro.animals.config;

import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users-service")
public interface FeignUserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users/email/{email}")
    ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable("email") String email);

    @RequestMapping(method = RequestMethod.GET, value = "/users/identificationNr/{id}")
    ResponseEntity<UserResponseDTO> getUserByIdentificationNr(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String id);
}