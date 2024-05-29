package com.liro.animals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AddressDTO {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String countryCode;
    private String postalCode;
}
