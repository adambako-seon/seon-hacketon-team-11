package io.seon.hackaton.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.seon.hackaton.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findById(Integer id);
}
