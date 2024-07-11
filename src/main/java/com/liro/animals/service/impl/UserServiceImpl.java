package com.liro.animals.service.impl;

import com.liro.animals.config.FeignUserClient;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    FeignUserClient feignUserClient;

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return feignUserClient.getUserByEmail(email).getBody();
    }

    @Override
    public UserResponseDTO getUserByIdentificationNr(Long identificationNr) {
        return feignUserClient.getUserByIdentificationNr(String.valueOf(identificationNr)).getBody();

    }
}
