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
    private final AnimalDataIndexRepository animalDataIndexRepository;
    private final AnimalDataTypeRepository animalDataTypeRepository;

    @Autowired
    public DataLoader(AnimalTypeRepository animalTypeRepository,
                      BreedRepository breedRepository,
                      RecordTypeRepository recordTypeRepository,
                      AnimalDataIndexRepository animalDataIndexRepository,
                      AnimalDataTypeRepository animalDataTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
        this.breedRepository = breedRepository;
        this.recordTypeRepository = recordTypeRepository;
        this.animalDataIndexRepository = animalDataIndexRepository;
        this.animalDataTypeRepository = animalDataTypeRepository;
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


        // Data indexes
        List<String> animalDataIndexesStr = Arrays.asList("general", "by_Age");
        List<AnimalDataIndex> animalDataIndexes = new ArrayList<>();
        animalDataIndexesStr.forEach(s -> animalDataIndexes.add(AnimalDataIndex.builder()
            .animalDataIndexType(s)
            .build()));
        animalDataIndexRepository.saveAll(animalDataIndexes);


        // Data types
        List<String> animalDataTypesStr = Arrays.asList("average_weight_in_kgs", "average_length_in_cms",
            "average_height_in_cms", "average_life_span_in_months", "other_names", "max_weight_in_kgs",
            "min_weight_in_kgs", "min_height_in_cms", "max_height_in_cms", "min_life_span_in_months",
            "max_life_span_in_months", "breed_size", "recommended_exercise", "manto");
        List<AnimalDataType> animalDataTypes = new ArrayList<>();
        animalDataTypesStr.forEach(s -> animalDataTypes.add(AnimalDataType.builder()
            .animalDataType(s)
            .build()));
        animalDataTypeRepository.saveAll(animalDataTypes);


        // Breeds
        AnimalDataIndex generalType = animalDataIndexRepository.findByAnimalDataIndexType("general").get();
        AnimalDataType otherNames = animalDataTypeRepository.findByAnimalDataType("other_names").get();
        AnimalDataType maxWeight = animalDataTypeRepository.findByAnimalDataType("max_weight_in_kgs").get();
        AnimalDataType minWeight = animalDataTypeRepository.findByAnimalDataType("min_weight_in_kgs").get();
        AnimalDataType minHeight = animalDataTypeRepository.findByAnimalDataType("min_height_in_cms").get();
        AnimalDataType maxHeight = animalDataTypeRepository.findByAnimalDataType("max_height_in_cms").get();
        AnimalDataType minLifeSpan = animalDataTypeRepository.findByAnimalDataType("min_life_span_in_months").get();
        AnimalDataType maxLifeSpan = animalDataTypeRepository.findByAnimalDataType("max_life_span_in_months").get();
        AnimalDataType breedSize = animalDataTypeRepository.findByAnimalDataType("breed_size").get();
        AnimalDataType recommendedExercise = animalDataTypeRepository.findByAnimalDataType("recommended_exercise").get();

        AnimalType dogType = animalTypeRepository.findByFormalName("canis lupus familiaris").get();


        Breed breed = Breed.builder()
            .name("alaskan malamute")
            .animalType(dogType)
            .build();

        breed = breedRepository.save(breed);
        breed.setAnimalData(new HashSet<>());

        AnimalData otherName1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(otherNames)
            .animalDataValue("malamute de alaska")
            .build();
        breed.getAnimalData().add(otherName1);

        AnimalData maxWeight1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(maxWeight)
            .animalDataValue("38")
            .build();
        breed.getAnimalData().add(maxWeight1);

        AnimalData minWeight1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(minWeight)
            .animalDataValue("32")
            .build();
        breed.getAnimalData().add(minWeight1);

        AnimalData maxHeight1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(maxHeight)
            .animalDataValue("61")
            .build();
        breed.getAnimalData().add(maxHeight1);

        AnimalData minHeight1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(minHeight)
            .animalDataValue("56")
            .build();
        breed.getAnimalData().add(minHeight1);

        AnimalData maxLifeSpan1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(maxLifeSpan)
            .animalDataValue("12")
            .build();
        breed.getAnimalData().add(maxLifeSpan1);

        AnimalData minLifeSpan1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(minLifeSpan)
            .animalDataValue("10")
            .build();
        breed.getAnimalData().add(minLifeSpan1);

        AnimalData breedSize1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(breedSize)
            .animalDataValue("4")
            .build();
        breed.getAnimalData().add(breedSize1);

        AnimalData recommendedExercise1 = AnimalData.builder()
            .breed(breed)
            .animalDataIndex(generalType)
            .animalDataType(recommendedExercise)
            .animalDataValue("medium")
            .build();
        breed.getAnimalData().add(recommendedExercise1);

        breedRepository.save(breed);
    }
}
