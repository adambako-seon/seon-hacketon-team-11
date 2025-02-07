package io.seon.hackaton.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.seon.hackaton.dto.UserDTO;
import io.seon.hackaton.entity.Address;
import io.seon.hackaton.entity.Phone;
import io.seon.hackaton.entity.Shipping;
import io.seon.hackaton.entity.User;
import io.seon.hackaton.repository.PhoneRepository;
import io.seon.hackaton.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public User saveOrUpdate(UserDTO userDto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            User existing = existingUser.get();
            user.setId(existing.getId());

            List<String> existingPhones = existing.getPhones().stream()
              .map(Phone::getPhoneNumber)
              .collect(Collectors.toList());

            List<String> existingShippingAddresses = existing.getShippings().stream()
              .flatMap(shipping -> shipping.getAddresses().stream().map(Address::getStreet))
              .collect(Collectors.toList());

            user.getPhones().removeIf(phone -> existingPhones.contains(phone.getPhoneNumber()));
            user.getShippings().forEach(shipping ->
              shipping.getAddresses().removeIf(address -> existingShippingAddresses.contains(address.getStreet())));

            existing.getPhones().addAll(user.getPhones());
            existing.getShippings().addAll(user.getShippings());

            for (Shipping shipping : user.getShippings()) {
                shipping.setUser(existing);
                shipping.getAddresses().forEach(address -> address.setShipping(shipping));
            }
            for (Phone phone : user.getPhones()) {
                phone.setUser(existing);
            }
            return userRepository.save(existing);
        }

        user.getPhones().forEach(phone -> phone.setUser(user));
        user.getShippings().forEach(shipping -> {
            shipping.setUser(user);
            shipping.getAddresses().forEach(address -> address.setShipping(shipping));
        });

        return userRepository.save(user);
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
