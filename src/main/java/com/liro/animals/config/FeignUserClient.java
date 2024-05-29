package com.liro.animals.config;

import com.liro.animals.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "users-service")
public interface FeignUserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String email);
}