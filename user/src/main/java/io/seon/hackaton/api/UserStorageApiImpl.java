package io.seon.hackaton.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.seon.hackaton.dto.UserDTO;
import io.seon.hackaton.repository.UserRepository;
import io.seon.hackaton.service.UserService;


@Component
public class UserStorageApiImpl implements UserStorageApiDelegate {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public ResponseEntity<io.seon.hackaton.model.User> getUserProfile(Integer userId) {
        return ResponseEntity.ok(modelMapper.map(
          userRepository.findById(userId.longValue()), io.seon.hackaton.model.User.class));
    }

    @Override
    public ResponseEntity<io.seon.hackaton.model.User> createUserProfile(io.seon.hackaton.model.User openApiUser) {

        UserDTO userDTO = modelMapper.map(openApiUser, UserDTO.class);

        // Pass DTO to service
        userService.saveOrUpdateUser(userDTO);
        return ResponseEntity.ok().body(modelMapper.map(new UserDTO(), io.seon.hackaton.model.User.class));
    }

}
