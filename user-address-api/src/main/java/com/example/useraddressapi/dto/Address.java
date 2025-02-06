package com.example.useraddressapi.dto;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Address {

    private String country;

    private String street;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
