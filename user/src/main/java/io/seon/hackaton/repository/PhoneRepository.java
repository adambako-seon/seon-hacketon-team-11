package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Phone;
import io.seon.hackaton.entity.User;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByUser(User user);
}
