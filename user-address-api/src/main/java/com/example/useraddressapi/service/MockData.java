package com.example.useraddressapi.service;

import com.example.useraddressapi.dto.Address;
import com.example.useraddressapi.dto.Shipping;
import com.example.useraddressapi.dto.UserAddress;
import java.util.List;


public class MockData {
    public static final UserAddress ORBAN = UserAddress.builder()
      .id(1)
      .username("Orban Viktor")
      .shippings(
        List.of(
          Shipping.builder()
            .addresses(
              List.of(
                Address.builder()
                  .country("Hungary")
                  .street("Cinege utca")
                  .build(),
                Address.builder()
                  .country("Russia")
                  .street("Arbatskaya")
                  .build()
              )
            )
            .build()
        )
      )
      .build();

    public static final UserAddress FICO = UserAddress.builder()
      .id(2)
      .username("Robert Fico")
      .shippings(
        List.of(
          Shipping.builder()
            .addresses(
              List.of(
                Address.builder()
                  .country("Slovakia")
                  .street("Strapacka")
                  .build(),
                Address.builder()
                  .country("Russia")
                  .street("Staraya Basmannaya")
                  .build()
              )
            )
            .build()
        )
      )
      .build();

    public static final UserAddress getDefault(int id) {
        return UserAddress.builder()
          .id(id)
          .username("John Doe")
          .shippings(
            List.of(
              Shipping.builder()
                .addresses(
                  List.of(
                    Address.builder()
                      .country("USA")
                      .street("Wall Street")
                      .build(),
                    Address.builder()
                      .country("UK")
                      .street("Downing Street")
                      .build()
                  )
                )
                .build()
            )
          )
          .build();
    }

}
