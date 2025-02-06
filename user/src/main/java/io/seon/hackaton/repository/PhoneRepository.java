package io.seon.hackaton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Phone;

    @Repository
    public interface PhoneRepository extends JpaRepository<Phone, Long> {}

