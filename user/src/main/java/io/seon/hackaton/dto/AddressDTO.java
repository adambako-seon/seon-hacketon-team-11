package io.seon.hackaton.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String country;
    private String region;
    private String zip;
    private String street;
}