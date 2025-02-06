package io.seon.hackaton.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private Long id;
    private String phoneNumber;
}