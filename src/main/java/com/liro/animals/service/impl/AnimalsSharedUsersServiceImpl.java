package com.liro.animals.service.impl;

import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.exceptions.NotFoundException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.repositories.AnimalsSharedUsersRepository;
import com.liro.animals.service.AnimalsSharedUsersService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AnimalsSharedUsersServiceImpl implements AnimalsSharedUsersService {

    private final UserService userService;
    private final Util util;
    private final AnimalRepository animalRepository;
    private final AnimalsSharedUsersRepository animalsSharedUsersRepository;



    public AnimalsSharedUsersServiceImpl(UserService userService,
                                         Util util,
                                         AnimalRepository animalRepository,
                                         AnimalsSharedUsersRepository animalsSharedUsersRepository) {
        this.userService = userService;
        this.util = util;
        this.animalRepository = animalRepository;
        this.animalsSharedUsersRepository = animalsSharedUsersRepository;
    }

    @Override
    public void createRelation(Long animalId, Boolean readOnly, String shareToEmail, UserDTO userDTO) {

        UserResponseDTO userToShare = userService.getUserByEmail(shareToEmail);
        System.out.println("---------------------- = " + userToShare.getName() + userToShare.getEmail() + "---------------------");

        Animal animal = util.validatePermissions(animalId, userDTO,
                true, true, false, false);

        System.out.println("----------------------- ANIMAL " + animal.getName() + animal.getSurname() + "--------------------------");

        if (animal.getSharedWith() == null) {
            System.out.println("------------------------- GETSHAREDWITH == NULL --------------------------");
            animal.setSharedWith(new HashSet<>());
        }


        Optional<AnimalsSharedUsers> animalsSharedUsers = animalsSharedUsersRepository.findByAnimalIdAndUserId(animalId, userToShare.getId());

        if (animalsSharedUsers != null){
            if (animalsSharedUsers.get().getReadOnly() != readOnly)
            System.out.println("-------------------------- " + animalsSharedUsers.get().getUserId() + "---------------------------");
            animalsSharedUsers.get().setReadOnly(readOnly);
        }else {

            AnimalsSharedUsers newSharedUser = AnimalsSharedUsers.builder()
                    .animal(animal)
                    .userId(userToShare.getId())
                    .readOnly(readOnly)
                    .build();

            System.out.println("--------------- REALCION CREADA -------------------------");
            animal.getSharedWith().add(newSharedUser);
            System.out.println("------------------- AÑADIENDO A LA LISTA ---------------------------");


            System.out.println("---------------------- Animal pre guardado con usuario " + userDTO.getEmail());
            animalRepository.save(animal);
            System.out.println("---------------------- Animal guardado con usuario " + userDTO.getEmail());
        }
    }
}
