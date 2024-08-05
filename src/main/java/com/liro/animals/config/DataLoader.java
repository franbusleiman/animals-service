package com.liro.animals.config;

import com.liro.animals.model.dbentities.*;
import com.liro.animals.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final AnimalTypeRepository animalTypeRepository;
    private final BreedRepository breedRepository;
    private final RecordTypeRepository recordTypeRepository;

    @Autowired
    public DataLoader(AnimalTypeRepository animalTypeRepository,
                      BreedRepository breedRepository,
                      RecordTypeRepository recordTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
        this.breedRepository = breedRepository;
        this.recordTypeRepository = recordTypeRepository;
    }

    public void run(ApplicationArguments args) {

        // Return if the db has been already initialized
        if (!recordTypeRepository.findAll().isEmpty()) {
            return;
        }

        // Animal types
        animalTypeRepository.save(AnimalType
            .builder()
            .formalName("canis lupus familiaris")
            .name("dog")
            .build());


        // Record types
        List<String> recordTypesStr = Arrays.asList("old_name", "old_surname", "weight_in_kgs",
            "length_in_cms", "height_in_cms", "childbirth_date", "pregnancy_date", "heat_date", "manto",
            "details");
        List<RecordType> recordTypes = new ArrayList<>();
        recordTypesStr.forEach(s -> recordTypes.add(RecordType.builder()
            .recordType(s)
            .build()));
        recordTypeRepository.saveAll(recordTypes);


        // Breeds
        AnimalType dogType = animalTypeRepository.findByFormalName("canis lupus familiaris").get();


        Breed breed = Breed.builder()
            .name("alaskan malamute")
            .animalType(dogType)
            .build();

        breed = breedRepository.save(breed);

        breedRepository.save(breed);
    }
}
