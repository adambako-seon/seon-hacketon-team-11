package com.example.useraddressapi.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class UserAddress {

    private Integer id;

    private String username;

    private List<Shipping> shippings;

}
