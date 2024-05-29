package com.liro.animals.dto.responses;

import com.liro.animals.dto.RecordDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class RecordResponse extends RecordDTO {

    private Long id;

    private boolean validData;

    private Long vetUserId;
}