package com.liro.animals.util;


import com.liro.animals.dto.UserDTO;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.exceptions.UnauthorizedException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.repositories.AnimalsSharedUsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class Util {

    private final AnimalsSharedUsersRepository animalsSharedUsersRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public Util(AnimalsSharedUsersRepository animalsSharedUsersRepository,
                AnimalRepository animalRepository) {
        this.animalsSharedUsersRepository = animalsSharedUsersRepository;
        this.animalRepository = animalRepository;
    }

    public Animal validatePermissions(Long animalId, UserDTO user,
                                      boolean needWritePermissions, boolean onlyOwner,
                                      boolean vetEnabled, boolean onlyVet) {
        Animal animal = animalRepository.
            findById(animalId).orElseThrow(
            () -> new ResourceNotFoundException("Animal not found with id: "
                + animalId));

        // if onlyVets, return the animal if is the user is an enabledVet and error if it isn't
        boolean validVet = validateVet(user);
        if (onlyVet) {
            if (!validVet) {
                throw new UnauthorizedException("You are not a valid veterinary");
            }

            return animal;
        }

        // if vetEnabled, return the animal if the user is an enabledVet
        if (vetEnabled) {
            if (validVet) {
                return animal;
            }
        }

        // if onlyOwner, return the animal if it's the owner and error if it isn't
        boolean owner = animal.getOwnerUserId().equals(user.getId());
        if (onlyOwner) {
            if (!owner) {
                throw new UnauthorizedException("You are not the owner of this animal");
            }

            return animal;
        }

        // return the animal if it's the owner
        if (owner) {
            return animal;
        }

        // return error if the animal isn't shared with the user
        Optional<AnimalsSharedUsers> animalsSharedClientProfiles = animalsSharedUsersRepository
                .findByAnimalIdAndUserId(animalId, user.getId());
        if (!animalsSharedClientProfiles.isPresent()) {
            throw new UnauthorizedException("You are not allowed to this animal");
        }

        // if needWritePermissions, return error if the animal shared with the user is with readOnly as true
        if (needWritePermissions) {
            if (animalsSharedClientProfiles.get().getReadOnly()) {
                throw new UnauthorizedException("You are not allowed to make changes into this animal");
            }
        }

        // Only return if shares can access the call
        return animal;
    }

    public boolean validateVet(UserDTO user) {
        // TODO: Change to check the validity of a veterinary using his plate
        //  If invalid, change the enabled boolean to false and return false
        //  If valid, change the enabled boolean to true and return true
        //  Create an endpoint to do this validation in the userService
        return user.getRoles().contains("ROLE_VET");
    }

    public static <T> void updateIfNotNull(Consumer<T> setterMethod, T value) {
        if (value != null){
            setterMethod.accept(value);
        }
    }

    public static UserDTO getUser(String token){
            Claims claims;

            claims = Jwts.parser()
                    //  .setSigningKey("codigo_secreto".getBytes())
                    .setSigningKey(Base64.getEncoder().encodeToString("asdfAEGVDSAkdnASBOIAW912927171Q23Q".getBytes()))
                    .parseClaimsJws(token.substring(7))
                    .getBody();

            return  UserDTO.builder()
                    .email((String) claims.get("email"))
                    .id(Long.valueOf((Integer) claims.get("id")))
                    .roles((List<String>) claims.get("authorities"))
                    .build();
        }
}
