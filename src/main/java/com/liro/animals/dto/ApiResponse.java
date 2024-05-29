package com.liro.animals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
public class ApiResponse {
    private  Boolean success;
    private  String message;
}