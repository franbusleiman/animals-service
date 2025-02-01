package com.liro.animals.service.impl;


import com.liro.animals.dto.*;
import com.liro.animals.dto.mappers.*;
import com.liro.animals.dto.responses.AnimalCompleteResponse;
import com.liro.animals.dto.responses.AnimalMigrationResponse;
import com.liro.animals.dto.responses.AnimalResponse;
import com.liro.animals.exceptions.BadRequestException;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.Record;
import com.liro.animals.model.dbentities.*;
import com.liro.animals.model.enums.Castrated;
import com.liro.animals.repositories.*;
import com.liro.animals.service.AnimalService;
import com.liro.animals.service.AnimalsSharedUsersService;
import com.liro.animals.service.RecordService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalColorRepository animalColorRepository;
    private final AnimalsSharedUsersRepository animalsSharedUsersRepository;
    private final BreedRepository breedRepository;
    private final BreedMapper breedMapper;
    private final AnimalTypeMapper animalTypeMapper;
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalMapper animalMapper;
    private final UserService userService;
    private final AnimalsSharedUsersService animalsSharedUsersService;
    private final RecordMapper recordMapper;
    private final RecordRepository recordRepository;
    private final RecordService recordService;
    private final RecordTypeRepository recordTypeRepository;
    private final Util util;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository,
                             AnimalColorRepository animalColorRepository,
                             AnimalsSharedUsersRepository animalsSharedUsersRepository,
                             BreedRepository breedRepository,
                             AnimalMapper animalMapper,
                             UserService userService,
                             Util util,
                             AnimalTypeRepository animalTypeRepository,
                             BreedMapper breedMapper,
                             AnimalTypeMapper animalTypeMapper,
                             RecordMapper recordMapper,
                             RecordService recordService,
                             RecordRepository recordRepository,
                             RecordTypeRepository recordTypeRepository,
                             AnimalsSharedUsersService animalsSharedUsersService) {
        this.animalRepository = animalRepository;
        this.animalColorRepository = animalColorRepository;
        this.animalsSharedUsersRepository = animalsSharedUsersRepository;
        this.breedRepository = breedRepository;
        this.animalMapper = animalMapper;
        this.userService = userService;
        this.util = util;
        this.breedMapper = breedMapper;
        this.animalTypeMapper = animalTypeMapper;
        this.animalTypeRepository = animalTypeRepository;
        this.recordMapper = recordMapper;
        this.recordRepository = recordRepository;
        this.recordService = recordService;
        this.recordTypeRepository = recordTypeRepository;
        this.animalsSharedUsersService = animalsSharedUsersService;
    }

    @Override
    public AnimalResponse getAnimalResponse(Long animalId, UserDTO userDTO) {

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + animalId));

        AnimalResponse animalResponse = animalMapper.animalToAnimalResponse(animal);

        animalResponse.setBreed(breedMapper.breedToBreedResponse(animal.getBreed()));
        animalResponse.setAnimalType(animalTypeMapper.animalTypeToAnimalTypeResponse(animal.getBreed().getAnimalType()));

        return animalResponse;
    }

    //TODO: EVERY VET CAN CREATE ANIMALS TO OWNERS.
    @Override
    public AnimalResponse createAnimal(AnimalDTO animalRequest, UserDTO userDTO) {

        Animal animal = animalMapper.animalDtoToAnimal(animalRequest);


        if (animalRequest.getOwnerUserId() != null) {


            if (userDTO.getRoles().contains("ROLE_VET")) {
                animal.setValidCastrated(true);
                animal.setMainClinicId(userDTO.getClinicId());
                animal.setOwnerUserId(animalRequest.getOwnerUserId());
            }
        } else {

            animal.setValidCastrated(false);
            animal.setOwnerUserId(userDTO.getId());
        }

        Breed breed = breedRepository.
                findById(animalRequest.getBreedId()).orElseThrow(
                        () -> new ResourceNotFoundException("Breed not found with id: "
                                + animalRequest.getBreedId()));

        animal.setBreed(breed);
        if (breed.getAnimals() == null) breed.setAnimals(new HashSet<>());
        breed.getAnimals().add(animal);

        AnimalColor animalColor = animalColorRepository
                .findByHex(animalRequest.getMainColorHex()).orElseThrow(
                        () -> new ResourceNotFoundException("Animal Color not found with hex: "
                                + animalRequest.getMainColorHex()));

        animal.setMainColor(animalColor);
        if (animalColor.getMainColorOf() == null) animalColor.setMainColorOf(new HashSet<>());
        animalColor.getMainColorOf().add(animal);
        animal.setIsPublic(true);

        return animalMapper.animalToAnimalResponse(
                animalRepository.save(animal));
    }

    @Override
    public List<AnimalMigrationResponse> migrateAnimals(List<AnimalMigratorDTO> animalMigratorDTOList, Long vetClinicId) {

        List<AnimalMigrationResponse> responses = new ArrayList<>();
        animalMigratorDTOList.forEach(animalRequest -> {


            Animal animal = animalMapper.animalMigratorDtoToAnimal(animalRequest);


            animal.setVetterCode(vetClinicId + "-" + animalRequest.getVetterCode());


            animal.setMainClinicId(vetClinicId);


            AnimalType animalType = animalTypeRepository.findByFormalName(animalRequest.getEspecie())
                    .orElseThrow(() -> new ResourceNotFoundException("animal type not found: " + animalRequest.getEspecie()));


            Breed breed = breedRepository.findByAnimalTypeAndNameOrAlternativeNames(animalType, animalRequest.getBreed()).stream().findFirst().orElseGet(() -> {


                return breedRepository.findByAnimalTypeAndNameOrAlternativeNames(animalType, "mestizo").stream().findFirst()
                                    .orElseThrow(() -> new ResourceNotFoundException("breed not found"));

            });


            animal.setBreed(breed);
            animal.setIsPublic(true);

            if (breed.getAnimals() == null) breed.setAnimals(new HashSet<>());
            breed.getAnimals().

                    add(animal);

            animalRepository.save(animal);

            if(animalRequest.getPeso()!=null){
                RecordType recordType = recordTypeRepository.findById(3L)
                        .orElseThrow(() -> new ResourceNotFoundException("RecordType not found with id: "));


                Record record = new Record();
                record.setRecordType(recordType);
                record.setDataString(animalRequest.getPeso());
                record.setValidData(false);
                record.setDate(LocalDateTime.now());

                if (recordType.getRecords() == null) recordType.setRecords(new HashSet<>());
                recordType.getRecords().add(record);

                record.setAnimal(animal);

               recordRepository.save(record);
            }


            responses.add(animalMapper.animalToAnimalMigrationResponse(animal));
        });

        return responses;
    }

    @Override
    public void updateAnimal(AnimalDTO animalRequest, Long animalId, UserDTO userDTO) {
        boolean validVet = util.validateVet(userDTO);

        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);


        if (animalRequest.getDeathDate() != null) {
            animal.setDeath(true);
            animal.setDeathDate(animalRequest.getDeathDate());
        } else{


        // Only update if the one setting it is a veterinary
        if (animal.getSex() != null) {
            if (validVet) {
                Util.updateIfNotNull(animal::setSex, animalRequest.getSex());
            }
        } else {
            Util.updateIfNotNull(animal::setSex, animalRequest.getSex());
        }

        // If the animal is castrated validly only a vet could change this, if it's not anyone can change
        //  it until a vet set it as true
        if (animalRequest.getCastrated() != null) {
            if (animal.getCastrated() == Castrated.YES && animal.getValidCastrated()) {
                if (validVet) {
                    animal.setCastrated(animalRequest.getCastrated());
                }
            } else {
                animal.setCastrated(animalRequest.getCastrated());
                animal.setValidCastrated(validVet);
            }
        }

        // Only update if the one setting it is a veterinary
        if (animalRequest.getBreedId() != null && validVet) {
            Breed breed = breedRepository.findById(animalRequest.getBreedId())
                    .orElseThrow(() -> new ResourceNotFoundException("Breed not found with id: "
                            + animalRequest.getBreedId()));
            animal.setBreed(breed);
            animal.setValidBreed(true);
        }

        AnimalColor animalColor = animalColorRepository
                .findByHex(animalRequest.getMainColorHex()).orElseThrow(
                        () -> new ResourceNotFoundException("Animal Color not found with hex: "
                                + animalRequest.getMainColorHex()));

        animal.setMainColor(animalColor);
        if (animalColor.getMainColorOf() == null) animalColor.setMainColorOf(new HashSet<>());
        animalColor.getMainColorOf().add(animal);

        // Only update the birthdate if validBirthdate is false or if the one setting it is a veterinary
        // If validBirthdate is false and the one setting it is a veterinary, the boolean will be set to true,
        //  not allowing more modifications to the birthdate except if it's from other veterinaries
        if (animalRequest.getBirthDate() != null) {
            if (animal.getValidBirthDate()) {
                if (validVet) {
                    animal.setBirthDate(animalRequest.getBirthDate());
                }
            } else {
                animal.setBirthDate(animalRequest.getBirthDate());
                animal.setValidBirthDate(validVet);
            }
        }
        Util.updateIfNotNull(animal::setApproxBirthDate, animalRequest.getApproxBirthDate());

        Util.updateIfNotNull(animal::setDeath, animalRequest.getDeath());
        Util.updateIfNotNull(animal::setApproxDeathDate, animalRequest.getApproxDeathDate());

        Util.updateIfNotNull(animal::setBornLocation, animalRequest.getBornLocation());
        Util.updateIfNotNull(animal::setBornLat, animalRequest.getBornLat());
        Util.updateIfNotNull(animal::setBornHeight, animalRequest.getBornHeight());
        Util.updateIfNotNull(animal::setBornLong, animalRequest.getBornLong());
        Util.updateIfNotNull(animal::setSurname, animalRequest.getSurname());
        Util.updateIfNotNull(animal::setName, animalRequest.getName());

        }

        animalRepository.save(animal);
    }

    @Override
    public void deleteAnimal(Long animalId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                false, true, false);

        animalRepository.delete(animal);
    }

    @Override
    public void changeShareStateAnimal(Long animalId, String shareToEmail,
                                       boolean readOnly, UserDTO userDTO) {


        System.out.println("----------------------- LLAMANDO AL METODO NUEVO --------------------------");
        animalsSharedUsersService.createRelation(animalId, readOnly, shareToEmail, userDTO);


//        Optional<AnimalsSharedUsers> animalsSharedClientProfiles = animalsSharedUsersRepository
//            .findByAnimalIdAndUserId(animalId, userToShare.getId());
//        System.out.println("animalsSharedClientProfiles = " + animalsSharedClientProfiles);
//        if (animalsSharedClientProfiles.isPresent()) {
//            if (animalsSharedClientProfiles.get().getReadOnly() != readOnly) {
//                animalsSharedClientProfiles.get().setReadOnly(readOnly);
//
//                animalsSharedUsersRepository.save(animalsSharedClientProfiles.get());
//            }
//        } else {
//            if (animal.getSharedWith() == null) animal.setSharedWith(new HashSet<>());
//            animal.getSharedWith().add(AnimalsSharedUsers.builder()
//                .animal(animal)
//                .userId(userToShare.getId())
//                .readOnly(readOnly)
//                .build());
//
//            animalRepository.save(animal);
//        }
    }

    @Override
    public void removeShareAnimal(Long animalId, String shareToEmail, UserDTO userDTO) {
        UserResponseDTO userToShare = userService.getUserByEmail(shareToEmail);

        Animal animal = util.validatePermissions(animalId, userDTO,
                false, true, false);

        Optional<AnimalsSharedUsers> animalsSharedClientProfiles = animalsSharedUsersRepository
                .findByAnimalIdAndUserId(animalId, userToShare.getId());
        if (animalsSharedClientProfiles.isPresent()) {
            animal.getSharedWith().remove(animalsSharedClientProfiles.get());

            animalRepository.save(animal);
        }
    }

    @Override
    public void increaseNumberOfPhotos(Long animalId, UserDTO userDTO) {

        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);
        animal.setNumberOfPhotos(animal.getNumberOfPhotos() + 1);

        animalRepository.save(animal);
    }

    @Override
    public void decreaseNumberOfPhotos(Long animalId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);
        if (animal.getNumberOfPhotos() > 0) {
            animal.setNumberOfPhotos(animal.getNumberOfPhotos() - 1);
            animalRepository.save(animal);

        } else throw new BadRequestException("The number of photos cannot be less than 0!");


    }

    @Override
    public void addColor(Long animalId, Long animalColorId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);
        AnimalColor animalColor = animalColorRepository.
                findById(animalColorId).orElseThrow(
                        () -> new ResourceNotFoundException("Animal color not found with id: "
                                + animalColorId));

        if (animalColor.getAnimals() == null) animalColor.setAnimals(new HashSet<>());
        animalColor.getAnimals().add(animal);

        if (animal.getColors() == null) animal.setColors(new HashSet<>());
        animal.getColors().add(animalColor);

        animalRepository.save(animal);
    }

    @Override
    public void removeColor(Long animalId, Long animalColorId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);
        AnimalColor animalColor = animalColorRepository.
                findById(animalColorId).orElseThrow(
                        () -> new ResourceNotFoundException("Animal color not found with id: "
                                + animalColorId));

        if (animalColor.getAnimals() != null) {
            animalColor.getAnimals().remove(animal);
        }

        if (animal.getColors() != null) {
            animal.getColors().remove(animalColor);
        }

        animalRepository.save(animal);
    }

    @Override
    public void changeMainColor(Long animalId, Long animalColorId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);
        AnimalColor animalColor = animalColorRepository.
                findById(animalColorId).orElseThrow(
                        () -> new ResourceNotFoundException("Animal color not found with id: "
                                + animalColorId));

        if (animalColor.getMainColorOf() == null) animalColor.setMainColorOf(new HashSet<>());
        animalColor.getMainColorOf().add(animal);
        animal.setMainColor(animalColor);

        animalRepository.save(animal);
    }

    @Override
    public void changeOwner(Long animalId, Long ownerUserId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,
                true, false, false);

        UserResponseDTO userDTO1 = userService.getUserById(ownerUserId);
        animal.setOwnerUserId(userDTO1.getId());

        animalRepository.save(animal);
    }


    @Override
    public void hasPermissions(Long animalId, UserDTO userDTO, boolean needWritePermissions,
                               boolean onlyOwner, boolean onlyVet) {
        util.validatePermissions(animalId, userDTO, needWritePermissions, onlyOwner, onlyVet);
    }

    @Override
    public Page<AnimalsSharedClientProfilesWADTO> getSharedAnimals(Pageable pageable, UserDTO userDTO) {

        return animalsSharedUsersRepository.findAllByUserId(userDTO.getId(), pageable)
                .map(animalMapper::aSCPToASCPWADto);
    }

    @Override
    public Page<AnimalResponse> getOwnAnimals(Pageable pageable, UserDTO userDTO, Liveness liveness) {


        return animalRepository.findAllByOwnerUserIdAndIsDeath(userDTO.getId(), pageable, liveness.liveness())
                .map(animal -> {
                    AnimalResponse animalResponse = animalMapper.animalToAnimalResponse(animal);
                    animalResponse.setAnimalType(animalTypeMapper.animalTypeToAnimalTypeResponse(animal.getBreed().getAnimalType()));
                    return animalResponse;
                });
    }

    @Override
    public List<AnimalCompleteResponse> getAnimalsByUserId(Long userId) {
        return animalRepository.findAllByOwnerUserId(userId)
                .stream()
                .map(animal -> {

                    AnimalCompleteResponse animalResponse = animalMapper.animalToAnimalCompleteResponse(animal);
                    animalResponse.setBreed(breedMapper.breedToBreedResponse(animal.getBreed()));
                    animalResponse.setAnimalType(animalTypeMapper.animalTypeToAnimalTypeResponse(animal.getBreed().getAnimalType()));
                    return animalResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<AnimalCompleteResponse> getAnimalsByNameAndVetId(Pageable pageable, String name, UserDTO userDTO) {

        if (!name.isEmpty()) {
            return animalRepository.findAllByNameContainingAndMainClinicOrExtraClinics(name, userDTO.getClinicId(), pageable)
                    .map(animal -> {

                        System.out.println(animal.getDeath());
                        AnimalCompleteResponse animalResponse = animalMapper.animalToAnimalCompleteResponse(animal);
                        System.out.println(animalResponse.getDeath());

                        if(animal.getOwnerUserId()!=null){
                            UserResponseDTO userDTO1 = userService.getUserById(animal.getOwnerUserId());
                            animalResponse.setOwner(userDTO1);

                        }

                        animalResponse.setBreed(breedMapper.breedToBreedResponse(animal.getBreed()));
                        animalResponse.setAnimalType(animalTypeMapper.animalTypeToAnimalTypeResponse(animal.getBreed().getAnimalType()));
                        animalResponse.setRecord(recordMapper.recordToRecordResponse(recordRepository.findLastByAnimalIdAndRecordTypeId(animal.getId(), 3L).orElseGet(() -> new Record())));

                        System.out.println(animalResponse.getDeath());
                        return animalResponse;
                    });
        }
        return Page.empty();
    }

    @Override
    public Page<AnimalCompleteResponse> getAnimalsByOwnerDni(Pageable pageable, Long dni, UserDTO userDTO) {


        UserResponseDTO userDTO1 = userService.getUserByIdentificationNr(dni);

        return animalRepository.findAllByOwnerUserIdAndIsDeath(userDTO1.getId(), pageable, null)
                .map(animal -> {

                    AnimalCompleteResponse animalResponse = animalMapper.animalToAnimalCompleteResponse(animal);
                    animalResponse.setOwner(userDTO1);
                    animalResponse.setBreed(breedMapper.breedToBreedResponse(animal.getBreed()));
                    animalResponse.setAnimalType(animalTypeMapper.animalTypeToAnimalTypeResponse(animal.getBreed().getAnimalType()));
                    animalResponse.setRecord(recordMapper.recordToRecordResponse(recordRepository.findLastByAnimalIdAndRecordTypeId(animal.getId(), 3L).orElseGet(() -> new Record())));
                    return animalResponse;
                });
    }
}