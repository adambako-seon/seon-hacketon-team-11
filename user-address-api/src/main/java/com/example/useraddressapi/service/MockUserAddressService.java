package com.example.useraddressapi.service;

import static com.example.useraddressapi.service.MockData.FICO;
import static com.example.useraddressapi.service.MockData.ORBAN;
import static com.example.useraddressapi.service.MockData.getDefault;

import com.example.useraddressapi.dto.UserAddress;
import java.util.Map;
import org.springframework.stereotype.Service;


@Service
public class MockUserAddressService {

    private static final Map<Integer, UserAddress> USER_ADDRESS_DATA = Map.of(
      1, ORBAN,
      2, FICO);

    public UserAddress getUser(int id) {
        switch (id) {
            case 0:
                return null;
            case 1, 2:
                return USER_ADDRESS_DATA.get(id);
            default:
                return getDefault(id);
        }

    }

}
