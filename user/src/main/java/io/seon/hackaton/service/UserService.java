package io.seon.hackaton.service;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.seon.hackaton.dto.PhoneDTO;
import io.seon.hackaton.dto.ShippingDTO;
import io.seon.hackaton.dto.UserDTO;
import io.seon.hackaton.entity.User;
import io.seon.hackaton.repository.AddressRepository;
import io.seon.hackaton.repository.PhoneRepository;
import io.seon.hackaton.repository.ShippingRepository;
import io.seon.hackaton.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;
    private final ShippingRepository shippingRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository, AddressRepository addressRepository, ShippingRepository shippingRepository ) {
        this.userRepository = userRepository;
        this.modelMapper = getModelMapper();
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
        this.shippingRepository = shippingRepository;

    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id.intValue());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.setShippings(user.getShippings().stream()
          .map(s -> modelMapper.map(s, ShippingDTO.class))
          .collect(Collectors.toList()));

        userDTO.setPhones(user.getPhones().stream()
          .map(p -> modelMapper.map(p, PhoneDTO.class))
          .collect(Collectors.toList()));

        return userDTO;
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        user.getShippings().forEach(shipping -> shipping.getAddresses().forEach(address -> address.setShipping(shipping)));
        user.getShippings().forEach(shipping -> shipping.setUser(user));
        user.getPhones().forEach(phone -> phone.setUser(user));

        User savedUser = userRepository.saveAndFlush(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }



    ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
            mapper.skip(UserDTO::setShippings);
            mapper.skip(UserDTO::setPhones);
        });

        return modelMapper;
    }
}
