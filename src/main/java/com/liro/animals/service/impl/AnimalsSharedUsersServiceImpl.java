package com.liro.animals.service.impl;

import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.service.AnimalsSharedUsersService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AnimalsSharedUsersServiceImpl implements AnimalsSharedUsersService {

    private final UserService userService;
    private final Util util;
    private final AnimalRepository animalRepository;



    public AnimalsSharedUsersServiceImpl(UserService userService,
                                         Util util,
                                         AnimalRepository animalRepository) {
        this.userService = userService;
        this.util = util;
        this.animalRepository = animalRepository;
    }

    @Override
    public void createRelation(Long animalId, Boolean readOnly, String shareToEmail, UserDTO userDTO) {

        UserResponseDTO userToShare = userService.getUserByEmail(shareToEmail);
        System.out.println("---------------------- = " + userToShare.getName() + userToShare.getEmail() + "---------------------");

        Animal animal = util.validatePermissions(animalId, userDTO,
                true, true, false, false);

        if (animal.getSharedWith() == null) {
            System.out.println("------------------------- GETSHAREDWITH == NULL --------------------------");
            animal.setSharedWith(new HashSet<>());
        }

        AnimalsSharedUsers newSharedUser = AnimalsSharedUsers.builder()
                .animal(animal)
                .userId(userToShare.getId())
                .readOnly(readOnly)
                .build();

        System.out.println("------ REALCION CREADA -------------------------");
        animal.getSharedWith().add(newSharedUser);
        System.out.println("------------------- AÑADIENDO A LA LISTA ---------------------------");


        System.out.println("---------------------- Animal pre guardado con usuario " + userDTO.toString());
        animalRepository.save(animal);
        System.out.println("---------------------- Animal guardado con usuario " + userDTO.toString());

    }
}
