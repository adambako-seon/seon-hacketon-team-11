package io.seon.hackaton.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.seon.hackaton.model.UserProfile;
import io.seon.hackaton.repository.UserRepository;

@Component
public class UserStorageApiImpl implements UserStorageApiDelegate {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public ResponseEntity<UserProfile> getUserProfile(Integer userId) {
        return ResponseEntity.ok(modelMapper.map(userRepository.findById(userId), UserProfile.class));
    }

}
