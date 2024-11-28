package com.liro.animals.dto.mappers;


import com.liro.animals.dto.GroupDTO;
import com.liro.animals.dto.responses.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponse groupToGroupResponse(Group group);

    @Mapping(target = "breeds", ignore = true)
    @Mapping(target = "animalTypes", ignore = true)
    Group groupDtoToGroup(GroupDTO groupDTO);
}
