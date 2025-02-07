package io.seon.hackaton.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.seon.hackaton.dto.UserDTO;
import io.seon.hackaton.entity.Shipping;
import io.seon.hackaton.entity.User;
import io.seon.hackaton.model.Address;
import io.seon.hackaton.model.Phone;
import io.seon.hackaton.repository.AddressRepository;
import io.seon.hackaton.repository.PhoneRepository;
import io.seon.hackaton.repository.ShippingRepository;
import io.seon.hackaton.repository.UserRepository;
import io.seon.hackaton.service.UserService;


@Component
public class UserStorageApiImpl implements UserStorageApiDelegate {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private AddressRepository addressRepository;

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public ResponseEntity<io.seon.hackaton.model.User> getUserProfile(Integer userId) {
        return ResponseEntity.ok(modelMapper.map(
          userRepository.findById(userId.longValue()), io.seon.hackaton.model.User.class));
    }

    @Override
    public ResponseEntity<io.seon.hackaton.model.User> createUserProfile(io.seon.hackaton.model.User openApiUser) {
        UserDTO userDTO = modelMapper.map(openApiUser, UserDTO.class);

        User response = userService.saveOrUpdate(userDTO);
        return ResponseEntity.ok().body(modelMapper.map(response, io.seon.hackaton.model.User.class));
    }

    @Override
    public ResponseEntity<String> getAddressCountByUsername(String username) {
        Optional<User> byUsername = userService.findByUsername(username);
        long count = byUsername.get().getShippings().size();

        return ResponseEntity.ok("" + count);
    }

    @Override
    public ResponseEntity<List<Address>> getUserAddressesByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        List<Shipping> shippings = shippingRepository.findByUser(user.get());
        List<io.seon.hackaton.entity.Address> addresses = addressRepository.findByShipping(shippings.get(0));

        List<Address> response = new ArrayList<>();
        addresses.forEach(adress -> response.add(modelMapper.map(adress, Address.class)));

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<Phone>> getUserPhonesByUsername(String username) {
        Optional<User> byUsername = userService.findByUsername(username);
        List<io.seon.hackaton.entity.Phone> phones = phoneRepository.findByUser(byUsername.get());
        List<Phone> response = new ArrayList<>();

        phones.forEach(p -> response.add(modelMapper.map(p, Phone.class)));

        return ResponseEntity.ok().body(response);
    }


    @Override
    public ResponseEntity<String> getPhoneCountByUsername(String username) {
        Optional<User> byUsername = userService.findByUsername(username);
        long count = byUsername.get().getPhones().size();
        return ResponseEntity.ok("" + count);
    }

    public ResponseEntity<Long> getNumberOfAddresses(String username) {
        return ResponseEntity.ok(userService.findByUsername(username).stream().count());
    }

}
