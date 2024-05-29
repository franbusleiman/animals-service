package com.liro.animals.service;


import com.liro.animals.dto.UserDTO;
import org.apache.catalina.User;

public interface UserService {

    UserDTO getUserByEmail(String email);
}
