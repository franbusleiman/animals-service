package com.liro.animals.service.impl;

import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.UserResponseDTO;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.repositories.AnimalsSharedUsersRepository;
import com.liro.animals.service.AnimalsSharedUsersService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
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
    public void createRelation(Long animalId, Boolean readOnly, String shareToEmail, String token, Long clinicId) {

        UserResponseDTO userToShare = userService.getUserByEmail(shareToEmail);

        Animal animal = util.validatePermissions(animalId, token, clinicId,
                false, true,  false);

        Optional<AnimalsSharedUsers> animalsSharedUsersOptional = animalsSharedUsersRepository.findByAnimalIdAndUserId(animalId, userToShare.getId());


        if (animalsSharedUsersOptional.isPresent()) {
            AnimalsSharedUsers existingSharedUser = animalsSharedUsersOptional.get();
            if (!existingSharedUser.getReadOnly().equals(readOnly)) {
                existingSharedUser.setReadOnly(readOnly);
                animalsSharedUsersRepository.save(existingSharedUser);
            }
        } else {
            AnimalsSharedUsers newSharedUser = AnimalsSharedUsers.builder()
                    .animal(animal)
                    .userId(userToShare.getId())
                    .readOnly(readOnly)
                    .build();

            if (animal.getSharedWith() == null) {
                animal.setSharedWith(new HashSet<>());
            }

            animal.getSharedWith().add(newSharedUser);

            animalsSharedUsersRepository.save(newSharedUser);
        }
    }
}
