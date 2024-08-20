package com.liro.animals.dto.mappers;

import com.liro.animals.dto.responses.AnimalsSharedUsersResponse;
import com.liro.animals.model.dbentities.AnimalsSharedUsers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AnimalsSharedUsersMapper {

//    @Mapping()
    AnimalsSharedUsersResponse animalsSharedUsersToAnimalsSharedUsersResponse(AnimalsSharedUsers animalsSharedUsers);

}
