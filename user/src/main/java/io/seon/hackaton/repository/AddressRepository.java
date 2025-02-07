package io.seon.hackaton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
