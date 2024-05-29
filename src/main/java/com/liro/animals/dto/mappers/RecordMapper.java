package com.liro.animals.dto.mappers;


import com.liro.animals.dto.RecordDTO;
import com.liro.animals.dto.responses.RecordResponse;
import com.liro.animals.model.dbentities.Record;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(target = "animalId", source = "animal.id")
    @Mapping(target = "recordTypeId", source = "recordType.id")
    RecordResponse recordToRecordResponse(Record record);

    @Mapping(target = "vetUserId", ignore = true)
    @Mapping(target = "validData", ignore = true)
    @Mapping(target = "recordType", ignore = true)
    @Mapping(target = "animal", ignore = true)
    Record recordDtoToRecord(RecordDTO recordDTO);
}
