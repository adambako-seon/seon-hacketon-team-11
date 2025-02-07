package com.example.useraddressapi.controller;

import com.example.useraddressapi.dto.UserAddress;
import com.example.useraddressapi.service.MockUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UserAddressController {

    @Autowired
    public UserAddressController(MockUserAddressService service) {
        this.service = service;
    }

    private final MockUserAddressService service;

    @GetMapping(value = {"/user-address/{id}"}, produces = "application/json")
    public ResponseEntity<UserAddress> receiveRequest(@PathVariable int id) {
        UserAddress address = service.getUser(id);
        if (address != null) {
            return ResponseEntity.ok(service.getUser(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
