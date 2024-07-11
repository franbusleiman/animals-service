package com.liro.animals.service;


import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import org.apache.catalina.User;

public interface UserService {

    UserResponseDTO getUserByEmail(String email);
    UserResponseDTO getUserByIdentificationNr(Long identificationNr);

}
