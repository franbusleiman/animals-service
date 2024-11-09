package com.liro.animals.util;


import com.liro.animals.config.FeignClinicClientClient;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.requests.ClinicClientDTO;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.exceptions.UnauthorizedException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalsExtraClinics;
import com.liro.animals.repositories.AnimalExtraClinicsRepository;
import com.liro.animals.repositories.AnimalRepository;
import com.liro.animals.repositories.AnimalsSharedUsersRepository;
import feign.Feign;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

@Component
public class Util {

    private final AnimalsSharedUsersRepository animalsSharedUsersRepository;
    private final AnimalExtraClinicsRepository animalExtraClinicsRepository;
    private final AnimalRepository animalRepository;
    private final FeignClinicClientClient feignClinicClientClient;

    @Autowired
    public Util(AnimalsSharedUsersRepository animalsSharedUsersRepository,
                AnimalRepository animalRepository, AnimalExtraClinicsRepository animalExtraClinicsRepository,
                FeignClinicClientClient feignClinicClientClient) {
        this.animalsSharedUsersRepository = animalsSharedUsersRepository;
        this.animalRepository = animalRepository;
        this.animalExtraClinicsRepository = animalExtraClinicsRepository;
        this.feignClinicClientClient = feignClinicClientClient;
    }

    public Animal validatePermissions(Long animalId, String token, Long clinicHeader,
                                      boolean needWritePermissions, boolean onlyOwner, boolean onlyVet) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + animalId));

        UserDTO user = getUser(token,clinicHeader);

        boolean isOwner = animal.getOwnerUserId().equals(user.getId());
        boolean isVet = validateVet(user);
        boolean isSharedOwnerWithWrite = isSharedOwnerWithWritePermissions(animal, user);
        boolean isSharedOwner = isSharedOwner(animal, user);
        boolean isInMainClinic = animal.getMainClinicId() != null && animal.getMainClinicId().equals(user.getClinicId());
        boolean isInExtraClinics = isInExtraClinics(animal, user);
        boolean isPublic = animal.getIsPublic() != null && animal.getIsPublic();

        // Prioridad a onlyOwner
        if (onlyOwner) {
            if (!isOwner) {
                throw new UnauthorizedException("You are not the owner of this animal");
            }
            return animal; // Si es el dueño, no necesita más validación.
        }

        // Validación para onlyVet
        if (onlyVet) {
            if (!isVet) {
                throw new UnauthorizedException("You are not a valid veterinary");
            }
            // Si es público y el vet no está en mainClinic ni extraClinics, agregamos la clínica
            addVetClinicIfPublic(animal, user,token, isInMainClinic, isInExtraClinics);
            return animal;
        }

        // Verificar permisos de escritura
        if (needWritePermissions) {
            if (!isSharedOwnerWithWrite && !isOwner && !isInMainClinic) {
                throw new UnauthorizedException("You do not have write permissions for this animal");
            }
            return animal; // Si tiene permisos de escritura, no necesita más validación.
        }

        // Si no se requiere escritura, verificar acceso general
        if (!isPublic && !isOwner && !isSharedOwner && !isInExtraClinics && !isInMainClinic) {
            throw new UnauthorizedException("You do not have permission to access this animal");
        }

        // Añadir la clínica si es público y es veterinario
        addVetClinicIfPublic(animal, user,token, isInMainClinic, isInExtraClinics);
        return animal;
    }

    private void addVetClinicIfPublic(Animal animal, UserDTO user, String token, boolean isInMainClinic, boolean isInExtraClinics) {

        if (animal.getIsPublic() && validateVet(user) && user.getClinicId() != null) {
            if (!isInMainClinic && !isInExtraClinics) {
                if (animal.getMainClinicId() == null) {
                    // Si no tiene mainClinic, se asigna la clínica del veterinario como main
                    animal.setMainClinicId(user.getClinicId());
                } else {
                    // Si ya tiene una mainClinic, agregar la clínica como extra
                    animal.getExtraClinics().add(new AnimalsExtraClinics(animal, user.getClinicId()));
                }
                addClientToClinic(animal.getOwnerUserId(), user.getClinicId(), token);
            }
        }
    }

    private void addClientToClinic(Long userId, Long clinicId, String token){

        ClinicClientDTO clinicClientDTO = ClinicClientDTO.builder()
                .userId(userId)
                .clinicId(clinicId)
                .accountBalance(0.00)
                .build();

        feignClinicClientClient.createClinicClient(token, clinicClientDTO);
    }

    private boolean isSharedOwnerWithWritePermissions(Animal animal, UserDTO user) {
        return animal.getSharedWith().stream()
                .anyMatch(shared -> shared.getUserId().equals(user.getId()) && !shared.getReadOnly());
    }

    private boolean isSharedOwner(Animal animal, UserDTO user) {
        return animal.getSharedWith().stream()
                .anyMatch(shared -> shared.getUserId().equals(user.getId()));
    }

    private boolean isInExtraClinics(Animal animal, UserDTO user) {
        return user.getClinicId() != null &&
                animal.getExtraClinics().stream()
                        .anyMatch(extraClinic -> extraClinic.getClinicId().equals(user.getClinicId()));
    }

    public boolean validateVet(UserDTO user) {
        return user.getRoles().contains("ROLE_VET");
    }
    public static UserDTO getUser(String token, Long clinicHeader){
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
                    .clinicId(clinicHeader)
                    .build();
        }


    public static <T> void updateIfNotNull(Consumer<T> setterMethod, T value) {
        if (value != null){
            setterMethod.accept(value);
        }
    }
}
