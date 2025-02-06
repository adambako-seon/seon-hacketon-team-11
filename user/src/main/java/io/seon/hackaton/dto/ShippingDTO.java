package io.seon.hackaton.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDTO {
    private Long id;
    private List<AddressDTO> addresses;
}