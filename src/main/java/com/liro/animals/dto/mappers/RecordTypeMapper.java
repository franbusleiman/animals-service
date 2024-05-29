package com.liro.animals.dto.mappers;


import com.liro.animals.dto.RecordTypeDTO;
import com.liro.animals.dto.responses.RecordTypeResponse;
import com.liro.animals.model.dbentities.RecordType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordTypeMapper {

    RecordTypeResponse recordTypeToRecordTypeResponse(RecordType recordType);

    @Mapping(target = "records", ignore = true)
    RecordType recordTypeDtoToRecordType(RecordTypeDTO recordTypeDTO);
}
