package io.seon.hackaton.dto;


import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String placeOfBirth;
    private LocalDate dateOfBirth;
    private List<ShippingDTO> shippings;
    private List<PhoneDTO> phones;
}