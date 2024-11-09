package com.liro.animals.service;

import com.liro.animals.dto.UserDTO;
import com.liro.animals.dto.responses.AnimalsSharedUsersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalsSharedUsersService {

//    Page<AnimalsSharedUsersResponse> findAll(Pageable pageable);

    void createRelation(Long animalId, Boolean readOnly, String shareToEmail, String token, Long clinicId);

//    void deleteRelation(Long animalId, Long userId);

}
