package io.seon.hackaton.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.seon.hackaton.entity.Address;
import io.seon.hackaton.entity.Shipping;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByShipping(Shipping shipping);
}
