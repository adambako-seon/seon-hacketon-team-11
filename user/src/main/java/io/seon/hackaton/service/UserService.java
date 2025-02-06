package io.seon.hackaton.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.seon.hackaton.dto.UserDTO;
import io.seon.hackaton.entity.Address;
import io.seon.hackaton.entity.Phone;
import io.seon.hackaton.entity.Shipping;
import io.seon.hackaton.entity.User;
import io.seon.hackaton.repository.AddressRepository;
import io.seon.hackaton.repository.PhoneRepository;
import io.seon.hackaton.repository.ShippingRepository;
import io.seon.hackaton.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final ShippingRepository shippingRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository, ShippingRepository shippingRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.phoneRepository = phoneRepository;
        this.shippingRepository = shippingRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public void saveOrUpdateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            userRepository.update(user);
            user.getPhones().forEach(phoneRepository::saveOrUpdate);
            user.getShippings().forEach(shippingRepository::saveOrUpdate);
            user.getShippings().forEach( shipping -> shipping.getAddresses().forEach(addressRepository::saveOrUpdate));
        } else {
            userRepository.save(user);
        }

        for (Phone phone : user.getPhones()) {
            phone.setUserId(user.getId());
            phoneRepository.saveOrUpdate(phone);
        }

        for (Shipping shipping : user.getShippings()) {
            shipping.setUserId(user.getId());
            shippingRepository.saveOrUpdate(shipping);

            for (Address address : shipping.getAddresses()) {
                address.setShippingId(shipping.getId());
                addressRepository.saveOrUpdate(address);
            }
        }
    }
}
