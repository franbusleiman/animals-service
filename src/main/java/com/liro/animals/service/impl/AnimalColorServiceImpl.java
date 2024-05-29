package com.liro.animals.service.impl;


import com.liro.animals.dto.AnimalColorDTO;
import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.mappers.AnimalColorMapper;
import com.liro.animals.dto.responses.AnimalColorResponse;
import com.liro.animals.exceptions.ConflictException;
import com.liro.animals.exceptions.ResourceNotFoundException;
import com.liro.animals.model.dbentities.Animal;
import com.liro.animals.model.dbentities.AnimalColor;
import com.liro.animals.repositories.AnimalColorRepository;
import com.liro.animals.service.AnimalColorService;
import com.liro.animals.service.UserService;
import com.liro.animals.util.Util;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalColorServiceImpl implements AnimalColorService {

    private final AnimalColorRepository animalColorRepository;
    private final AnimalColorMapper animalColorMapper;
    private final UserService userService;
    private final Util util;

    @Autowired
    public AnimalColorServiceImpl(AnimalColorRepository animalColorRepository,
                                  AnimalColorMapper animalColorMapper,
                                  UserService userService,
                                  Util util) {
        this.animalColorRepository = animalColorRepository;
        this.animalColorMapper = animalColorMapper;
        this.userService = userService;
        this.util = util;
    }

    @Override
    public Page<AnimalColorResponse> findAll(Pageable pageable) {
        return animalColorRepository.findAll(pageable)
            .map(animalColorMapper::animalColorToAnimalColorResponse);
    }

    @Override
    public Page<AnimalColorResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();
        return animalColorRepository.findAllByNameContaining(nameContaining, pageable)
            .map(animalColorMapper::animalColorToAnimalColorResponse);
    }

    @Override
    public List<AnimalColorResponse> findAllByAnimalId(Long animalId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,false, false, true, false);

        return animal.getColors().stream().map(animalColorMapper::animalColorToAnimalColorResponse)
            .collect(Collectors.toList());
    }

    @Override
    public AnimalColorResponse findMainColorByAnimalId(Long animalId, UserDTO userDTO) {
        Animal animal = util.validatePermissions(animalId, userDTO,false, false, true, false);

        return animalColorMapper.animalColorToAnimalColorResponse(animal.getMainColor());
    }

    @Override
    public AnimalColorResponse getAnimalColorResponse(Long animalColorId) {
        AnimalColor animalColor = animalColorRepository.findById(animalColorId)
            .orElseThrow(() -> new ResourceNotFoundException("AnimalColor not found with id: " + animalColorId));
        return animalColorMapper.animalColorToAnimalColorResponse(animalColor);
    }

    @Override
    public AnimalColorResponse createAnimalColor(AnimalColorDTO animalColorDto) {
        if (animalColorDto.getName() != null) {
            animalColorDto.setName(animalColorDto.getName().toLowerCase());
        }
        AnimalColor animalColor = animalColorMapper.animalColorDtoToAnimalColor(animalColorDto);
        if (animalColorRepository.existsByName(animalColorDto.getName())) {
            throw new ConflictException("Animal color with name: "
                + animalColorDto.getName() + ", already exists");
        }
        if (animalColorRepository.existsByHex(animalColorDto.getHex())) {
            throw new ConflictException("Animal color with hex: "
                + animalColorDto.getHex() + ", already exists");
        }

        return animalColorMapper.animalColorToAnimalColorResponse(
            animalColorRepository.save(animalColor)
        );
    }
}